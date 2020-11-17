package ru.snx.webapp.storage.strategy;

import java.io.IOException;

@FunctionalInterface
public interface DataReader {
    void readIt() throws IOException;
}
