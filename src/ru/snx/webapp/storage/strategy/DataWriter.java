package ru.snx.webapp.storage.strategy;

import java.io.IOException;

@FunctionalInterface
public interface DataWriter<T> {
    void writeIt(T t) throws IOException;
}
