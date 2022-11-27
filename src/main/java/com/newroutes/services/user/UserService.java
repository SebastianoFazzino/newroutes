package com.newroutes.services.user;

import com.newroutes.config.rabbitmq.Message;
import com.newroutes.config.rabbitmq.Producer;
import com.newroutes.entities.user.ArchivedUser;
import com.newroutes.entities.user.UserEntity;
import com.newroutes.enums.user.LogOperationType;
import com.newroutes.enums.user.LoginSource;
import com.newroutes.exceptions.user.EmailNotValidException;
import com.newroutes.exceptions.user.UserAlreadyExistsException;
import com.newroutes.exceptions.user.UserNotFoundException;
import com.newroutes.models.mappers.user.ArchivedUserMapper;
import com.newroutes.models.mappers.user.UserMapper;
import com.newroutes.models.responses.utility.Deliverability;
import com.newroutes.models.responses.utility.EmailValidationResponse;
import com.newroutes.models.user.User;
import com.newroutes.models.user.UserSignupData;
import com.newroutes.repositories.user.ArchivedUserRepository;
import com.newroutes.repositories.user.UserRepository;
import com.newroutes.services.integrations.AbstractApiService;
import com.newroutes.services.integrations.sendinblue.EmailService;
import com.newroutes.services.integrations.sendinblue.SendinblueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sibModel.CreateUpdateContactModel;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static com.newroutes.config.security.SecurityConfig.extractIp;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ArchivedUserRepository archivedUserRepository;
    private final ArchivedUserMapper archivedUserMapper;
    private final UserRoleService userRoleService;
    private final LogService logService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final SendinblueService sendinblueService;
    private final AbstractApiService abstractApiService;
    private final EmailService emailService;
    private final Producer producer;

    //*********************************************
    // CRUD region

    public boolean existsByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    private boolean existsArchived(String email) {
        return archivedUserRepository.findByEmail(email).isPresent();
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

            long delay = 15;
            emailService.sendWelcomeEmail(savedUser.getId(), delay);

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

    /**
     * Subscribe new User
     * @param signupData
     * @return
     */
    public User signup(UserSignupData signupData) {

        log.info("Validating UserSignupData {}", signupData);

        String email = signupData.getEmail();

        if ( this.existsByEmail(email) || existsByUsername(signupData.getUsername()) ) {
            throw new UserAlreadyExistsException("Either email or username are already in use");
        }

        if ( this.existsArchived(email) ) {
            throw new UserAlreadyExistsException("Archived user already exists with email " + email);
        }

        //**************************************************
        // Validating email

        EmailValidationResponse validation = abstractApiService.validateEmail(email);

        if ( !validation.validFormat.getValue() || validation.disposableEmail.getValue() ) {
            throw new EmailNotValidException("Either email syntax is not valid or is disposable email");
        }

        if ( validation.getDeliverability().equals(Deliverability.UNDELIVERABLE) ) {
            throw new EmailNotValidException("Email validation failed, address is " + validation.getDeliverability());
        }

        //**************************************************

        log.info("Signing up new User {}", signupData);

        User user = new User();
        userMapper.mergeSignupData(signupData, user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = this.save(user);

        //**************************************************

        userRoleService.addRole(savedUser);

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

    /**
     * Update last login
     * @param username
     * @param loginSource
     */
    public void updateLastLogin(String username, LoginSource loginSource, HttpServletRequest request) {

        User user = this.getByUsername(username);
        user.setLastLogin(new Date());
        this.save(user);

        String ip = extractIp(request);

        logService.addLog(
                user.getId(),
                LogOperationType.USER_LOGIN,
                String.format("New login from application %s and ip %s", loginSource, ip)
        );

        Message<String> message = new Message<>();
        message.setMessageType("login");
        message.setPayload("Login");

        producer.sendMessage(message);
    }

    //***********************************************************
    // Delete User

    /**
     * Delete User
     * @param userId
     */
    public void deleteUser(UUID userId) {

        log.info("Deleting User {}", userId);
        User user = this.getById(userId);

        //****************************************
        // unsubscribe User
        sendinblueService.deleteContact(user.getEmail());

        ArchivedUser archivedUser = new ArchivedUser();
        archivedUserMapper.createFromUser(user, archivedUser);
        archivedUser.addDeletedLog();

        log.info("Saving archived User {}", archivedUser);
        archivedUserRepository.save(archivedUser);
        userRepository.delete(userMapper.convertToEntity(user));
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
