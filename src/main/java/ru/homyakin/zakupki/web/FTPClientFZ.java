package ru.homyakin.zakupki.web;

import java.io.IOException;

public interface FTPClientFZ {
    // connect to server
    void connect() throws IOException;

    // login to server
    void login() throws IOException;

    // parse server
    void parseFTPServer() throws IOException;
}
