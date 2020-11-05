package ru.snx.webapp.utils;

import java.io.IOException;

@FunctionalInterface
public interface Writer<T> {
    void writeIt(T t) throws IOException;
}
