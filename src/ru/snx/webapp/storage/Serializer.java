package ru.snx.webapp.storage;

import ru.snx.webapp.model.Resume;

import java.io.InputStream;
import java.io.OutputStream;

public interface Serializer {

    void doWrite(OutputStream os, Resume resume);

    Resume doRead(InputStream is);
}
