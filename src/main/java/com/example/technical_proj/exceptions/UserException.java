package com.example.technical_proj.exceptions;

public class UserException extends RuntimeException{
    public UserException(final String userNotFound) {
        super(userNotFound);
    }
}
