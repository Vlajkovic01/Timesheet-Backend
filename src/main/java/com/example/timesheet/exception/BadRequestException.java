package com.example.timesheet.exception;

public class BadRequestException extends IllegalArgumentException{
    public BadRequestException(String message) {
        super(message);
    }
}
