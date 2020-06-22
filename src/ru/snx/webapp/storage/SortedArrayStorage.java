package ru.snx.webapp.storage;

import ru.snx.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume r) {

    }

    @Override
    public void delete(String uuid) {

    }

    @Override
    protected int findIndex(String uuid) {
        Resume indexSearch = new Resume();
        indexSearch.setUuid(uuid);
        return Arrays.binarySearch(storage,0,size,indexSearch);
    }
}
