package ru.homyakin.zakupki.exceptions;

public class FileIsEmptyException extends RuntimeException {
    private String message;

    public FileIsEmptyException(String filePath) {
        this.message = "File " + filePath + " is empty";
    }
}
