package ru.snx.webapp.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.snx.webapp.exceptions.ExistStorageException;
import ru.snx.webapp.exceptions.NoExistStorageException;
import ru.snx.webapp.model.Resume;
import ru.snx.webapp.utils.ResumeTestData;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorageTest {
    Storage storage;
    private Resume res1 = ResumeTestData.getFilledResume("1", "John");
    private Resume res2 = ResumeTestData.getFilledResume("2", "Mike");
    private Resume res3 = ResumeTestData.getFilledResume("3", "Alex");
    private Resume res4 = ResumeTestData.getFilledResume("5", "Mike");
    private Resume res5 = ResumeTestData.getFilledResume("4", "Mike");
    private static final Comparator<Resume> RESUME_COMPARATOR = AbstractStorage.getResumeComparator();

    AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void init() {
        storage.clear();
        storage.save(res1);
        storage.save(res2);
        storage.save(res3);
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void save() {
        Resume resume = ResumeTestData.getFilledResume("4", "Test");
        storage.save(resume);
        Assert.assertEquals(resume, storage.get("4"));
        Assert.assertEquals(4, storage.size());
    }

    @Test(expected = NoExistStorageException.class)
    public void delete() {
        storage.delete("1");
        storage.get("1");
    }

    @Test(expected = NoExistStorageException.class)
    public void deleteNoExist() {
        storage.delete("TestNoExist");
    }

    @Test
    public void update() {
        Resume resume = ResumeTestData.getFilledResume("1", "test_name");
        storage.update(resume);
        Assert.assertEquals(resume, storage.get("1"));
    }

    @Test(expected = NoExistStorageException.class)
    public void updateNoExist() {
        storage.update(ResumeTestData.getFilledResume("0", "testNoExistStorageException"));
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
    public void getAllSorted() {
        storage.save(res4);
        storage.save(res5);
        List<Resume> expectedResumes = Arrays.asList(res1, res2, res3, res4, res5);
        expectedResumes.sort(RESUME_COMPARATOR);
        Assert.assertEquals(expectedResumes, storage.getAllSorted());
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
