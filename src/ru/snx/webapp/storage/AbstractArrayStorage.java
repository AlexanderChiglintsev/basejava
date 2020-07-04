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
    protected void updateResume(int index, Resume r) {
        storage[index] = r;
    }

    @Override
    protected void insertResume(int index, Resume r) {
        if (size == STORAGE_CAPACITY) {
            throw new StorageException("База резюме заполнена !!!", r.getUuid());
        } else {
            insertArrayResume(index, r);
            size++;
        }
    }

    @Override
    protected void deleteResume(int index, String uuid) {
        deleteArrayResume(index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected Resume getResume(int index, String uuid) {
        return storage[index];
    }

    protected abstract void insertArrayResume(int index, Resume r);

    protected abstract void deleteArrayResume(int index);
}
