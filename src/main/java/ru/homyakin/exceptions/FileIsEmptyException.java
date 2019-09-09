package ru.homyakin.exceptions;

public class FileIsEmptyException extends RuntimeException {
    private String message;

    public FileIsEmptyException(String message) {
        this.message = message;
    }
}
