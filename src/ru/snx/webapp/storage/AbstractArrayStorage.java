package ru.snx.webapp.storage;

import ru.snx.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    private static final int STORAGE_CAPACITY = 10_000;
    Resume[] storage = new Resume[STORAGE_CAPACITY];
    int size = 0;

    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    /*public void save(Resume r) {
        int index = findIndex(r.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(r.getUuid());
        } else if (size < STORAGE_CAPACITY) {
            insertResume(index, r);
            size++;
        } else throw new StorageException("База резюме заполнена !!!", r.getUuid());
    }*/

    /*public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index < 0) {
            throw new NoExistStorageException(uuid);
        } else {
            deleteResume(index);
            storage[size - 1] = null;
            size--;
        }
    }*/

    /*public void update(Resume r) {
        int index = findIndex(r.getUuid());
        if (index >= 0) {
            storage[index] = r;
        } else {
            throw new NoExistStorageException(r.getUuid());
        }
    }*/

    /*public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            return storage[index];
        }
        throw new NoExistStorageException(uuid);
    }*/

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    @Override
    protected boolean checkSize() {
        return (size == STORAGE_CAPACITY);
    }

    @Override
    protected void updateResume(int index, Resume r) {
        storage[index] = r;
    }

    @Override
    protected Resume getResume(int index) {
        return storage[index];
    }

    //protected abstract void insertResume(int index, Resume r);

    //protected abstract void deleteResume(int index);

    protected abstract int findIndex(String uuid);
}
