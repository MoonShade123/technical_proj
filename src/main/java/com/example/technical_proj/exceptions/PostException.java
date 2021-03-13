package com.example.technical_proj.exceptions;

public class PostException extends RuntimeException {
    public PostException(final String postNotFound) {
        super(postNotFound);
    }
}
