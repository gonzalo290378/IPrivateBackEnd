package com.ms_users.exceptions;

public class UserDisabledNotFoundException extends RuntimeException {
    public UserDisabledNotFoundException(String message) {
        super(message);
    }
}

