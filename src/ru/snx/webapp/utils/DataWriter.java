package ru.snx.webapp.utils;

import java.io.IOException;

@FunctionalInterface
public interface DataWriter<T> {
    void writeIt(T t) throws IOException;
}
