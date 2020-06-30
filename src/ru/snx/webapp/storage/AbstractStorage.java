package ru.snx.webapp.storage;

import ru.snx.webapp.exceptions.ExistStorageException;
import ru.snx.webapp.exceptions.NoExistStorageException;
import ru.snx.webapp.exceptions.StorageException;
import ru.snx.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume r) {
        int index = findIndex(r.getUuid());
        if (index < 0) {
            throw new NoExistStorageException(r.getUuid());
        } else updateResume(index, r);
    }

    @Override
    public void save(Resume r) {
        if (checkSize()) {
            throw new StorageException("База резюме заполнена !!!", r.getUuid());
        } else {
            int index = findIndex(r.getUuid());
            if (index >= 0){
                throw new ExistStorageException(r.getUuid());
            } else {
                insertResume(index, r);
            }
        }
    }

    @Override
    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index < 0) {
            throw new NoExistStorageException(uuid);
        } else {
            return getResume(index);
        }
    }

    @Override
    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index < 0) {
            throw new NoExistStorageException(uuid);
        } else {
            deleteResume(index);
        }
    }

    /*@Override
    public Resume[] getAll() {
        return new Resume[0];
    }*/

    protected abstract int findIndex(String uuid);

    protected abstract void insertResume(int index, Resume r);

    protected abstract void updateResume(int index, Resume r);

    protected abstract Resume getResume(int index);

    protected abstract void deleteResume(int index);

    protected abstract boolean checkSize();
}
