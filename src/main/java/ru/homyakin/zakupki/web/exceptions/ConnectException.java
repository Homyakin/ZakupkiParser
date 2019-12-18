package ru.homyakin.zakupki.web.exceptions;

public class ConnectException extends NetworkException {
    private String message;

    public ConnectException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
