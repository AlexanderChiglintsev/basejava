package ru.snx.webapp.storage;

import org.junit.Test;
import ru.snx.webapp.exceptions.StorageException;
import ru.snx.webapp.utils.Config;
import ru.snx.webapp.utils.ResumeTestData;

public class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(new SqlStorage(Config.getInstance().getDbUrl(),
                Config.getInstance().getDbUser(),
                Config.getInstance().getDbPassword()));
    }

    @Test(expected = StorageException.class)
    @Override
    public void saveExist() {
        storage.save(ResumeTestData.getFilledResume("1", "John"));
    }

}