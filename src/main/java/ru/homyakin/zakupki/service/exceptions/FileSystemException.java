package ru.homyakin.zakupki.service.exceptions;

public class FileSystemException extends RuntimeException{
    private String message;

    public FileSystemException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
