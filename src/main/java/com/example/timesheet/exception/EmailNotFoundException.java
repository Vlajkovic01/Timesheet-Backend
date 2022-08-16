package com.example.timesheet.exception;

import javax.naming.AuthenticationException;

public class EmailNotFoundException extends AuthenticationException {
    public EmailNotFoundException(String message) {
        super(message);
    }
}
