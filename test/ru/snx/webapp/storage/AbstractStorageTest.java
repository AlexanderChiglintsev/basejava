package ru.snx.webapp.storage;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.snx.webapp.exceptions.ExistStorageException;
import ru.snx.webapp.exceptions.NoExistStorageException;
import ru.snx.webapp.model.Resume;

import static org.junit.Assert.*;

public abstract class AbstractStorageTest {
    Storage storage;
    private Resume res1 = new Resume("1");
    private Resume res2 = new Resume("2");
    private Resume res3 = new Resume("3");

    AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void beforeTest() {
        storage.save(res1);
        storage.save(res2);
        storage.save(res3);
    }

    @After
    public void afterTest() {
        storage.clear();
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void save() {
        Resume resume = new Resume("4");
        storage.save(resume);
        Assert.assertEquals(resume, storage.get("4"));
        Assert.assertEquals(4, storage.size());
    }

    @Test(expected = NoExistStorageException.class)
    public void delete() {
        storage.delete("1");
        Assert.assertEquals(2, storage.size());
        storage.delete("1");
    }

    @Test
    public void update() {
        storage.update(res1);
        Assert.assertEquals(res1, storage.get("1"));
    }

    @Test(expected = NoExistStorageException.class)
    public void updateNoExist() {
        storage.update(new Resume());
    }

    @Test
    public void get() {
        Assert.assertEquals(res1, storage.get("1"));
    }

    @Test(expected = NoExistStorageException.class)
    public void getNoExist() {
        storage.get("123");
    }

    @Test
    public void getAll() {
        Resume[] expectedResumes = new Resume[]{res1, res2, res3};
        Assert.assertArrayEquals(expectedResumes, storage.getAll());
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExistException() {
        storage.save(res1);
    }

}