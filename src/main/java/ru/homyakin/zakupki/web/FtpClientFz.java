package ru.homyakin.zakupki.web;

import java.io.IOException;

public interface FtpClientFz {
    void connect() throws IOException;

    void login() throws IOException;
}
