package ru.snx.webapp.storage;

import static org.junit.Assert.*;

public class PathStorageTest extends AbstractStorageTest {
    public PathStorageTest() {
        super(new PathStorage(PATH, new ObjectStreamSerializer()));
    }
}