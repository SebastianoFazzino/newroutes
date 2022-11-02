package com.newroutes.services.user;

import com.newroutes.entities.user.UserEntity;
import com.newroutes.enums.LogOperationType;
import com.newroutes.enums.LoginSource;
import com.newroutes.exceptions.user.EmailNotValidException;
import com.newroutes.exceptions.user.UserAlreadyExistsException;
import com.newroutes.exceptions.user.UserNotFoundException;
import com.newroutes.models.countries.CountryCode;
import com.newroutes.models.mappers.user.UserMapper;
import com.newroutes.models.user.User;
import com.newroutes.models.user.UserSignupData;
import com.newroutes.repositories.user.UserRepository;
import com.newroutes.services.integrations.CloudmersiveService;
import com.newroutes.services.integrations.SendinblueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sibModel.CreateUpdateContactModel;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserRoleService userRoleService;
    private final LogService logService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final CloudmersiveService cloudmersiveService;
    private final SendinblueService sendinblueService;


    //*********************************************
    // CRUD region

    public boolean existsByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public User getById(UUID userId) {

        log.info("Getting User by id {}", userId);

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User not found by id '%s'", userId)));

        return userMapper.convertToDto(user);
    }

    public User getByUsername(String username) {

        log.info("Getting User by username {}", username);
        Optional<UserEntity> user = userRepository.findByEmail(username);

        if ( user.isEmpty() ) {
            throw new UserNotFoundException(String.format("No User found for username %s", username));
        }

        return userMapper.convertToDto(user.get());
    }

    public List<User> getAll() {

        log.info("Getting all Users");
        return userRepository.findAll()
                .stream().map(userMapper::convertToDto)
                .collect(Collectors.toList());
    }

    public User save(User user) {

        boolean newSignup = user.getId() == null || user.getSendinBlueId() == null;

        User savedUser = userMapper.convertToDto(userRepository.save(
                userMapper.convertToEntity(user)));

        //**************************************************
        // Send contact to SendinBlue

        if ( !newSignup ) {

            sendinblueService.updateContact(savedUser);
            return savedUser;

        } else {

            CreateUpdateContactModel contactModel = sendinblueService.createContact(savedUser);
            savedUser.setSendinBlueId(contactModel.getId() + "");

            //*********************************************

            logService.addLog(
                    savedUser.getId(),
                    LogOperationType.SENDINBLUE_USER_CREATED,
                    String.format("SendinBlueUser contact created with id %s", contactModel.getId())
            );

            //*********************************************
        }

        return userMapper.convertToDto(userRepository.save(
                userMapper.convertToEntity(savedUser)));
    }

    public User signup(UserSignupData signupData) {

        log.info("Validating UserSignupData {}", signupData);

        if ( this.existsByEmail(signupData.getEmail()) || existsByUsername(signupData.getUsername()) ) {
            throw new UserAlreadyExistsException("Either email or username are already in use");
        }

        //**************************************************
        // Validating email

        boolean isValid = EmailValidator.getInstance().isValid(signupData.getEmail());
        boolean isDisposable = cloudmersiveService.validateEmail(signupData.getEmail()).isDisposable();

        if ( !isValid || isDisposable ) {
            throw new EmailNotValidException("Either email syntax is not valid or is disposable email");
        }

        //**************************************************

        log.info("Signing up new User {}", signupData);

        User user = new User();
        userMapper.mergeSignupData(signupData, user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = this.save(user);

        //**************************************************

        userRoleService.addRole(savedUser);
        savedUser = this.save(savedUser);

        //**************************************************
        // Creating signup log

        logService.addLog(
                savedUser.getId(),
                LogOperationType.USER_SIGNUP,
                String.format("User '%s' successfully signed up", user.getEmail())
        );

        //**************************************************

        return savedUser;
    }

    public void updateLastLogin(String username, LoginSource loginSource) {

        User user = this.getByUsername(username);
        user.setLastLogin(new Date());
        this.save(user);

        logService.addLog(
                user.getId(),
                LogOperationType.USER_LOGIN,
                "New login from application " + loginSource
        );
    }

    public User setCountry(UUID userId, CountryCode countryCode) {

        log.info("Setting User '{}' Country as {}", userId, countryCode);

        User user = this.getById(userId);
        user.setCountry(countryCode);
        return this.save(user);
    }

    //***********************************************************
    // Security

    @Override
    public UserDetails loadUserByUsername(String username) throws UserNotFoundException {

        log.info("Loading UserDetails from username {}", username);
        User user = this.getByUsername(username);

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRole().name())));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), authorities
        );
    }
}
