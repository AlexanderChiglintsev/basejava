package ru.snx.webapp.storage;

import ru.snx.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insertResume(int index, Resume r) {
        System.arraycopy(storage, (Math.abs(index) - 1), storage, Math.abs(index), (size - (Math.abs(index) - 1)));
        storage[Math.abs(index) - 1] = r;
    }

    @Override
    protected void deleteResume(int index) {
        System.arraycopy(storage, (Math.abs(index) + 1), storage, (Math.abs(index)), (size - Math.abs(index) - 1));
    }

    @Override
    protected int findIndex(String uuid) {
        Resume indexSearch = new Resume();
        indexSearch.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, indexSearch);
    }
}
