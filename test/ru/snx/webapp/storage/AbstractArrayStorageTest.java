package ru.snx.webapp.storage;

import org.junit.*;
import ru.snx.webapp.exceptions.*;
import ru.snx.webapp.model.Resume;


public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveStorageOverflow() {
        storage.clear();
        try {
            for (int i = 0; i < 10000; i++) {
                storage.save(new Resume(Integer.toString(i)));
            }
        } catch (Exception e) {
            Assert.fail("Filling storage is fail !!!");
        }
        storage.save(new Resume());
    }
}