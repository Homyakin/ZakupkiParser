package ru.homyakin.zakupki.exceptions;

public class IllegalXMLDataException extends RuntimeException {
    private String message;

    public IllegalXMLDataException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
