package com.example.ov_app.data;

public class InvalidCredentialsException extends Exception {

    public InvalidCredentialsException() {
        super("Invalid username or password.");
    }

    public InvalidCredentialsException(String message) {
        super(message);
    }
}
