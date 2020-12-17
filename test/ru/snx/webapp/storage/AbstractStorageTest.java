package ru.snx.webapp.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.snx.webapp.exceptions.ExistStorageException;
import ru.snx.webapp.exceptions.NoExistStorageException;
import ru.snx.webapp.model.ContactType;
import ru.snx.webapp.model.Resume;
import ru.snx.webapp.utils.Config;
import ru.snx.webapp.utils.ResumeTestData;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static ru.snx.webapp.storage.AbstractStorage.RESUME_COMPARATOR;

public abstract class AbstractStorageTest {

    static final File STORAGE_DIR = Config.getInstance().getStorageDir();
    Storage storage;
    private final String uuid1 = UUID.randomUUID().toString();
    private final String uuid2 = UUID.randomUUID().toString();
    private final String uuid3 = UUID.randomUUID().toString();
    private final String uuid4 = UUID.randomUUID().toString();
    private final String uuid5 = UUID.randomUUID().toString();
    private final Resume RES1 = new Resume(uuid1, "John");
    private final Resume RES2 = ResumeTestData.getFilledResume(uuid2, "Mike");
    private final Resume RES3 = ResumeTestData.getFilledResume(uuid3, "Alex");
    private final Resume RES4 = ResumeTestData.getFilledResume(uuid4, "Mike");
    private final Resume RES5 = ResumeTestData.getFilledResume(uuid5, "Mike");

    AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void init() {
        storage.clear();
        storage.save(RES1);
        storage.save(RES2);
        storage.save(RES3);
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void save() {
        Resume resume = ResumeTestData.getFilledResume(uuid4, "Test");
        storage.save(resume);
        Assert.assertEquals(resume, storage.get(uuid4));
        Assert.assertEquals(4, storage.size());
    }

    @Test
    public void delete() {
        storage.delete(uuid2);
        Assert.assertEquals(2, storage.size());
    }

    @Test(expected = NoExistStorageException.class)
    public void deleteNoExist() {
        storage.delete("TestNoExist");
    }

    @Test
    public void update() {
        Resume resume = ResumeTestData.getFilledResume(uuid1, "test_name");
        resume.addContact(ContactType.PHONE, "324125");
        storage.update(resume);
        Assert.assertEquals(resume, storage.get(uuid1));
    }

    @Test(expected = NoExistStorageException.class)
    public void updateNoExist() {
        storage.update(ResumeTestData.getFilledResume("0", "testNoExistStorageException"));
    }

    @Test
    public void get() {
        Assert.assertEquals(RES1, storage.get(uuid1));
    }

    @Test(expected = NoExistStorageException.class)
    public void getNoExist() {
        storage.get("123");
    }

    @Test
    public void getAllSorted() {
        storage.save(RES4);
        storage.save(RES5);
        List<Resume> expectedResumes = Arrays.asList(RES1, RES2, RES3, RES4, RES5);
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
    public void saveExist() {
        storage.save(RES1);
    }

}
