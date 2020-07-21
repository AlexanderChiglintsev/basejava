package ru.snx.webapp.storage;

import ru.snx.webapp.exceptions.StorageException;
import ru.snx.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage {
    private static final int STORAGE_CAPACITY = 10_000;
    Resume[] storage = new Resume[STORAGE_CAPACITY];
    int size = 0;

    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public int size() {
        return size;
    }

    @Override
    protected void updateResume(Object index, Resume r) {
        storage[(int) index] = r;
    }

    @Override
    protected void insertResume(Object index, Resume r) {
        if (size == STORAGE_CAPACITY) {
            throw new StorageException("База резюме заполнена !!!", r.getUuid());
        } else {
            insertArrayResume((int) index, r);
            size++;
        }
    }

    @Override
    protected void deleteResume(Object index) {
        deleteArrayResume((int) index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected Resume getResume(Object index) {
        return storage[(int) index];
    }

    @Override
    protected List<Resume> getAllSortedResume() {
        return Arrays.asList(storage).subList(0, size);
    }

    @Override
    protected boolean checkExist(Object index) {
        return ((int) index >= 0);
    }

    protected abstract void insertArrayResume(int index, Resume r);

    protected abstract void deleteArrayResume(int index);
}
