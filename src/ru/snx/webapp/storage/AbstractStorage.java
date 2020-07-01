package ru.snx.webapp.storage;

import ru.snx.webapp.exceptions.ExistStorageException;
import ru.snx.webapp.exceptions.NoExistStorageException;
import ru.snx.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume r) {
        if (!checkExist(r.getUuid())) {
            throw new NoExistStorageException(r.getUuid());
        } else updateResume(r);
    }

    @Override
    public void save(Resume r) {
        if (checkExist(r.getUuid())) {
            throw new ExistStorageException(r.getUuid());
        } else {
            insertResume(r);
        }
    }

    @Override
    public Resume get(String uuid) {

        if (!checkExist(uuid)) {
            throw new NoExistStorageException(uuid);
        } else {
            return getResume(uuid);
        }
    }

    @Override
    public void delete(String uuid) {
        if (!checkExist(uuid)) {
            throw new NoExistStorageException(uuid);
        } else {
            deleteResume(uuid);
        }
    }

    protected abstract boolean checkExist(String uuid);

    protected abstract void insertResume(Resume r);

    protected abstract void updateResume(Resume r);

    protected abstract Resume getResume(String uuid);

    protected abstract void deleteResume(String uuid);

}
