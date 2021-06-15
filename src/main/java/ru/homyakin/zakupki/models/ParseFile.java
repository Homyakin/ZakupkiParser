package ru.homyakin.zakupki.models;

public class ParseFile {
    private final String filepath;
    private final Folder folder;

    public ParseFile(String filepath, Folder folder) {
        this.filepath = filepath;
        this.folder = folder;
    }

    public Folder getFolder() {
        return folder;
    }

    public String getFilepath() {
        return filepath;
    }
}
