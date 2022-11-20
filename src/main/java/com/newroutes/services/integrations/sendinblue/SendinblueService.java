package com.newroutes.services.integrations.sendinblue;

import com.newroutes.entities.sendinblue.SendinBlueUserEntity;
import com.newroutes.enums.sendinblue.Template;
import com.newroutes.exceptions.user.UserNotFoundException;
import com.newroutes.models.mappers.SendinBlueUserMapper;
import com.newroutes.models.sendinblue.SendinBlueUser;
import com.newroutes.models.user.User;
import com.newroutes.repositories.SendinblueUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sendinblue.ApiClient;
import sendinblue.ApiException;
import sendinblue.Configuration;
import sibApi.AccountApi;
import sibApi.ContactsApi;
import sibApi.TransactionalEmailsApi;
import sibModel.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SendinblueService {

    private final SendinblueUserRepository repository;
    private final SendinBlueUserMapper mapper;

    //*************************************************************************
    // User CRUD

    private SendinBlueUser save(SendinBlueUser user) {
        return mapper.convertToDto(repository.save(mapper.convertToEntity(user)));
    }

    public SendinBlueUser getById(UUID userId) {

        log.info("Getting User by id {}", userId);

        SendinBlueUserEntity user = repository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User not found by id '%s'", userId)));

        return mapper.convertToDto(user);
    }

    private SendinBlueUser getOrCreate(UUID userId) {

        log.info("Getting or creating SendinBlueUser by User id {}", userId);
        Optional<SendinBlueUserEntity> optUser = repository.findByUserId(userId);

        if ( optUser.isPresent() ) {
            return mapper.convertToDto(optUser.get());
        }
        return new SendinBlueUser();
    }

    //*************************************************************************
    // Account API

    /**
     * Get account information, plan and credits details
     */
    public GetAccount getAccount() {

        try {
            return this.buildAccountApi().getAccount();

        } catch (ApiException e) {
            log.error("Exception when calling AccountApi#getAccount => {}", e.getMessage());
            return null;
        }
    }

    //*************************************************************************
    // Contacts API

    /**
     * Create contact from User
     * @param user
     * @return
     */
    public CreateUpdateContactModel createContact(User user) {

        log.info("Creating SendinBlueUser Contact for User {}", user.getEmail());

        SendinBlueUser sendinBlueUser = this.getOrCreate(user.getId());
        mapper.mergeUserData(user, sendinBlueUser);

        CreateContact createContact = new CreateContact();
        createContact.setEmail(user.getEmail());
        createContact.setUpdateEnabled(true);
        createContact.setAttributes(sendinBlueUser);

        CreateUpdateContactModel contact = this.createContact(createContact);

        sendinBlueUser.setSendinBlueId(contact.getId().toString());
        this.save(sendinBlueUser);

        return contact;
    }

    /**
     * Update contact
     * @param user
     * @return
     */
    public void updateContact(User user) {

        log.info("Updating SendinBlueUser Contact for User {}", user.getEmail());

        SendinBlueUser sendinBlueUser = this.getOrCreate(user.getId());
        mapper.mergeUserData(user, sendinBlueUser);
        this.save(sendinBlueUser);

        UpdateContact updateContact = new UpdateContact();
        updateContact.setAttributes(sendinBlueUser);

        this.updateContact(user.getEmail(), updateContact);
    }

    /**
     * Create a contact
     * @return
     */
    public CreateUpdateContactModel createContact(CreateContact createContact) {

        log.info("[SIB] - Creating new Contact {}", createContact);

        try {
            return this.buildContactsApi().createContact(createContact);

        } catch (ApiException e) {
            log.error("Exception when calling ContactsApi#createContact => {}", e.getMessage());
            return null;
        }
    }

    /**
     * Update contact
     * @param identifier
     * @param updateContact
     */
    public void updateContact(String identifier, UpdateContact updateContact) {

        log.info("[SIB] - Updating Contact {}", identifier);

        try {
            this.buildContactsApi().updateContact(identifier, updateContact);
        } catch (ApiException e) {
            log.error("Exception when calling ContactsApi#updateContact => {}", e.getMessage());
        }
    }

    /**
     * Delete contact
     * @param identifier
     */
    public void deleteContact(String identifier) {

        log.info("[SIB] - Deleting Contact {}", identifier);

        try {
            this.buildContactsApi().deleteContact(identifier);
        } catch (ApiException e) {
            log.error("Exception when calling ContactsApi#deleteContact => {}", e.getMessage());
        }
    }

    //*************************************************************************

    @Value("${integrations.sendinblue.apikey}")
    public String key;

    private AccountApi buildAccountApi() {
        AccountApi accountApi = new AccountApi();
        accountApi.setApiClient(this.getApiClient());
        return accountApi;
    }

    private ContactsApi buildContactsApi() {
        ContactsApi contactsApi = new ContactsApi();
        contactsApi.setApiClient(this.getApiClient());
        return contactsApi;
    }

    private ApiClient getApiClient() {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setApiKey(key);
        return defaultClient;
    }

    /**
     * Send transactional email
     * @param email
     * @param template
     */
    public void sendTransactionalEmail(String email, Template template) {

        log.info("[SIB] - Sending transactional email with template {} to {}", template, email);

        this.getApiClient();
        TransactionalEmailsApi api = new TransactionalEmailsApi();

        try {

            SendSmtpEmailTo to = new SendSmtpEmailTo();
            to.setEmail(email);

            SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();
            sendSmtpEmail.to(List.of(to));
            sendSmtpEmail.setTemplateId(template.getId());

            CreateSmtpEmail response = api.sendTransacEmail(sendSmtpEmail);
            log.info("[SIB] - Response: '{}'", response);

        } catch (Exception ex) {
            log.error(ex.toString());
        }
    }

    /**
     * Get all Templates
     * @return
     */
    public GetSmtpTemplates getTemplates() {

        log.info("[SIB] - Requested get all Templates");

        this.getApiClient();
        TransactionalEmailsApi api = new TransactionalEmailsApi();
        Boolean templateStatus = true;
        Long limit = 50L;
        Long offset = 0L;

        try {
            return api.getSmtpTemplates(templateStatus, limit, offset, null);

        } catch (Exception ex) {
            log.error("Error thrown on get templates: {}", ex.toString());
            return null;
        }
    }

    /**
     * Get Template by id
     * @param template
     * @return
     */
    public GetSmtpTemplateOverview getTemplate(Template template) {

        log.info("[SIB] - Requested get Template {}", template);

        this.getApiClient();
        TransactionalEmailsApi api = new TransactionalEmailsApi();

        try {
            return api.getSmtpTemplate(template.getId());

        } catch (Exception ex) {
            log.error("Error thrown on get template {}: {}", template, ex.toString());
            return null;
        }
    }
}
