package ru.snx.webapp.storage;

import ru.snx.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insertResume(int index, Resume r) {
        index = Math.abs(index);
        System.arraycopy(storage, (index - 1), storage, index, (size - (index - 1)));
        storage[index - 1] = r;
        size++;
    }

    @Override
    protected void deleteResume(int index) {
        System.arraycopy(storage, (index + 1), storage, index, (size - index - 1));
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected int findIndex(String uuid) {
        Resume indexSearch = new Resume();
        indexSearch.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, indexSearch);
    }

}
