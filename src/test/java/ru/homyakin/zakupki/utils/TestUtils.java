package ru.homyakin.zakupki.utils;

public class TestUtils {
    public static String getFilePath(String fileName) {
        return TestUtils.class.getClassLoader().getResource(fileName).getFile();
    }
}
