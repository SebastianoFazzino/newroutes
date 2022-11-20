package com.newroutes.enums.user;

public enum LogOperationType {

    USER_SIGNUP,
    USER_LOGIN,
    USER_UPDATE,
    DELETE_USER,
    REACTIVATE_USER,

    TWOFA_ENABLED,
    TWOFA_DISABLED,

    EMAIL_CONFIRMED,
    EMAIL_CHANGE_REQUEST,
    EMAIL_CHANGED,

    PASSWORD_EXPIRED,
    PASSWORD_CHANGE_REQUEST,
    PASSWORD_CHANGED,

    SENDINBLUE_USER_CREATED,
    UNSUBSCRIBE_USER

}
