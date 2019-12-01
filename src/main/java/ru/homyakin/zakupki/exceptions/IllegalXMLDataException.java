package ru.homyakin.zakupki.exceptions;

public class IllegalXMLDataException extends RuntimeException {
    private String message;

    public IllegalXMLDataException(String className) {
        this.message = "Unexpected XML format in " + className;
    }
}
