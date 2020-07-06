package ru.snx.webapp.storage;

import ru.snx.webapp.exceptions.ExistStorageException;
import ru.snx.webapp.exceptions.NoExistStorageException;
import ru.snx.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume r) {
        if (checkUuidNoExist(r.getUuid())) updateResume(findKey(r.getUuid()), r);
    }

    @Override
    public void save(Resume r) {
        if (checkUuidExist(r.getUuid())) insertResume(findKey(r.getUuid()), r);
    }

    @Override
    public Resume get(String uuid) {
        if (checkUuidNoExist(uuid)) return getResume(findKey(uuid));
        return null;
    }

    @Override
    public void delete(String uuid) {
        if (checkUuidNoExist(uuid)) deleteResume(findKey(uuid));
    }

    private boolean checkUuidExist(String uuid) {
        if (checkExist(findKey(uuid))) throw new ExistStorageException(uuid);
        return true;
    }

    private boolean checkUuidNoExist(String uuid) {
        if (!checkExist(findKey(uuid))) throw new NoExistStorageException(uuid);
        return true;
    }

    protected abstract boolean checkExist(Object key);

    protected abstract Object findKey(String uuid);

    protected abstract void insertResume(Object key, Resume r);

    protected abstract void updateResume(Object key, Resume r);

    protected abstract Resume getResume(Object key);

    protected abstract void deleteResume(Object key);

}
