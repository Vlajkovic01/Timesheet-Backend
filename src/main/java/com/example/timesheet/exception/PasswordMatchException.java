package com.example.timesheet.exception;

public class PasswordMatchException extends IllegalArgumentException {

    public PasswordMatchException(String message) {
        super(message);
    }
}
