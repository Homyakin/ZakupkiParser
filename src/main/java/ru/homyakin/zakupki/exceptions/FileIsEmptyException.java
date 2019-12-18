package ru.homyakin.zakupki.exceptions;

public class FileIsEmptyException extends RuntimeException {
    private String message;

    public FileIsEmptyException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
