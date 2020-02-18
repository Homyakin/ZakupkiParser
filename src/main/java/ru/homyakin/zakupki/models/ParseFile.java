package ru.homyakin.zakupki.models;

public class ParseFile {
    private final String filepath;
    private final FileType type;

    public ParseFile(String filepath, FileType type) {
        this.filepath = filepath;
        this.type = type;
    }

    public FileType getType() {
        return type;
    }

    public String getFilepath() {
        return filepath;
    }
}
