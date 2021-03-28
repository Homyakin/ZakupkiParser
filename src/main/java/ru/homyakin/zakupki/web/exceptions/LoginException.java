package ru.homyakin.zakupki.web.exceptions;

public class LoginException extends NetworkException {
    private final String message;

    public LoginException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
