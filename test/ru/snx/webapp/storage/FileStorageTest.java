package ru.snx.webapp.storage;

import static org.junit.Assert.*;

public class FileStorageTest extends AbstractStorageTest {
    public FileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamSerializer()));
    }
}