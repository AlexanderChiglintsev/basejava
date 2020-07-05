package ru.snx.webapp.storage;

import ru.snx.webapp.exceptions.ExistStorageException;
import ru.snx.webapp.exceptions.NoExistStorageException;
import ru.snx.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume r) {
        if (checkUuid(true, r.getUuid())) {
            updateResume(r);
        }
    }

    @Override
    public void save(Resume r) {
        if (checkUuid(false, r.getUuid())) {
            insertResume(r);
        }
    }

    @Override
    public Resume get(String uuid) {
        if (checkUuid(true, uuid)) {
            return getResume(findIndex(uuid));
        }
        return null;
    }

    @Override
    public void delete(String uuid) {
        if (checkUuid(true, uuid)) {
            deleteResume(findIndex(uuid));
        }
    }

    private boolean checkUuid(Boolean expect, String uuid) {
        boolean state = checkExist(uuid);
        if ((!state) && (expect)) {
            throw new NoExistStorageException(uuid);
        } else if ((state) && (!expect)) {
            throw new ExistStorageException(uuid);
        }
        return true;
    }

    protected abstract boolean checkExist(String uuid);

    protected abstract Object findIndex(String uuid);

    protected abstract void insertResume(Resume r);

    protected abstract void updateResume(Resume r);

    protected abstract Resume getResume(Object key);

    protected abstract void deleteResume(Object key);

}
