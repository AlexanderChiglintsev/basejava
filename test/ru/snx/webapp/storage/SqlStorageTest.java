package ru.snx.webapp.storage;

import ru.snx.webapp.utils.Config;

public class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(Config.getInstance().getSqlStorage());
    }
}