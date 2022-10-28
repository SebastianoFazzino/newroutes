package com.newroutes.services.integrations;

import com.newroutes.models.mappers.SendinBlueUserMapper;
import com.newroutes.models.sendinblue.SendinBlueUser;
import com.newroutes.models.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sendinblue.ApiClient;
import sendinblue.ApiException;
import sendinblue.Configuration;
import sibApi.AccountApi;
import sibApi.ContactsApi;
import sibModel.CreateContact;
import sibModel.CreateUpdateContactModel;
import sibModel.GetAccount;
import sibModel.UpdateContact;

@Slf4j
@Service
@RequiredArgsConstructor
public class SendinblueService {

    private final SendinBlueUserMapper sendinBlueUserMapper;


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

        SendinBlueUser sendinBlueUser = new SendinBlueUser();
        sendinBlueUserMapper.mergeUserData(user, sendinBlueUser);

        CreateContact createContact = new CreateContact();
        createContact.setEmail(user.getEmail());
        createContact.setUpdateEnabled(true);
        createContact.setAttributes(sendinBlueUser);

        return this.createContact(createContact);
    }

    /**
     * Update contact
     * @param user
     * @return
     */
    public void updateContact(User user) {

        SendinBlueUser sendinBlueUser = new SendinBlueUser();
        sendinBlueUserMapper.mergeUserData(user, sendinBlueUser);

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
}
