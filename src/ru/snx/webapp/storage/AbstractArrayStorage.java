package ru.snx.webapp.storage;

import ru.snx.webapp.exceptions.StorageException;
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

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    @Override
    protected void updateResume(Resume r) {
        storage[findIndex(r.getUuid())] = r;
    }

    @Override
    protected void insertResume(Resume r) {
        if (size == STORAGE_CAPACITY) {
            throw new StorageException("База резюме заполнена !!!", r.getUuid());
        } else {
            insertArrayResume(findIndex(r.getUuid()), r);
            size++;
        }
    }

    @Override
    protected void deleteResume(String uuid) {
        deleteArrayResume(findIndex(uuid));
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected Resume getResume(String uuid) {
        return storage[findIndex(uuid)];
    }

    @Override
    protected boolean checkExist(String uuid) {
        return findIndex(uuid) >= 0;
    }

    protected abstract int findIndex(String uuid);

    protected abstract void insertArrayResume(int index, Resume r);

    protected abstract void deleteArrayResume(int index);
}
