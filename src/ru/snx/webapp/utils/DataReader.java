package ru.snx.webapp.utils;

import java.io.IOException;

@FunctionalInterface
public interface DataReader {
    void readIt() throws IOException;
}
