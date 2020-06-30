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

    protected abstract int findIndex(String uuid);
}
