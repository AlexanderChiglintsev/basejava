package ru.snx.webapp.storage;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.snx.webapp.exceptions.ExistStorageException;
import ru.snx.webapp.exceptions.NoExistStorageException;
import ru.snx.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractStorageTest {
    Storage storage;
    private Resume res1 = new Resume("1", "John");
    private Resume res2 = new Resume("2", "Mike");
    private Resume res3 = new Resume("3", "Alex");

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
        Resume resume = new Resume("4", "Test");
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
        storage.update(new Resume("testNoExistStorageException"));
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
    public void getAllSortedByFullName() {
        List<Resume> expectedResumes = new ArrayList<>();
        expectedResumes.add(res3);
        expectedResumes.add(res1);
        expectedResumes.add(res2);
        Assert.assertArrayEquals(expectedResumes.toArray(), storage.getAllSorted().toArray());
    }

    @Test
    public void getAllSortedByFullNameAndUuid() {
        Resume res4 = new Resume("5", "Mike");
        Resume res5 = new Resume("4", "Mike");
        storage.save(res4);
        storage.save(res5);
        List<Resume> expectedResumes = new ArrayList<>();
        expectedResumes.add(res3);
        expectedResumes.add(res1);
        expectedResumes.add(res2);
        expectedResumes.add(res5);
        expectedResumes.add(res4);
        Assert.assertArrayEquals(expectedResumes.toArray(), storage.getAllSorted().toArray());
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