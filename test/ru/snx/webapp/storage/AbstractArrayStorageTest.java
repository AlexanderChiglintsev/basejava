package ru.snx.webapp.storage;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.snx.webapp.exceptions.ExistStorageException;
import ru.snx.webapp.exceptions.NoExistStorageException;
import ru.snx.webapp.exceptions.StorageException;
import ru.snx.webapp.model.Resume;


public abstract class AbstractArrayStorageTest {
    private Storage storage;
    private Resume res1 = new Resume("1");
    private Resume res2 = new Resume("2");
    private Resume res3 = new Resume("3");

    AbstractArrayStorageTest(Storage storage) {
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
        Assert.assertEquals(0, storage.getAll().length);
    }

    @Test
    public void save() {
        Resume resume = new Resume("4");
        storage.save(resume);
        Assert.assertEquals(resume, storage.get("4"));
    }

    @Test
    public void delete() {
        storage.delete("1");
        Assert.assertEquals(2, storage.getAll().length);
    }

    @Test
    public void update() {
        storage.update(res1);
        Assert.assertEquals(res1, storage.get("1"));
    }

    @Test
    public void get() {
        Assert.assertEquals(res1, storage.get("1"));
    }

    @Test
    public void getAll() {
        Resume[] resumes;
        resumes = new Resume[]{res1, res2, res3};
        Assert.assertArrayEquals(resumes, storage.getAll());
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test(expected = NoExistStorageException.class)
    public void resumeNotFound() {
        storage.get("123");
    }

    @Test(expected = ExistStorageException.class)
    public void resumeExist() {
        storage.save(res1);
    }

    @Test(expected = StorageException.class)
    public void storageOverflow() {
        storage.clear();
        try {
            for (int i = 0; i < 100000; i++) {
                storage.save(new Resume(Integer.toString(i)));
            }
        } catch (Exception e) {
            Assert.fail("Filling storage is fail !!!");
        }
        storage.save(new Resume());
    }
}