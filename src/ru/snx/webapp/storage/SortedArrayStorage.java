package ru.snx.webapp.storage;

import ru.snx.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    private static final Comparator<Resume> RESUME_COMPARATOR_UUID = Comparator.comparing(Resume::getUuid);

    @Override
    protected void insertArrayResume(int index, Resume r) {
        index = Math.abs(index);
        System.arraycopy(storage, (index - 1), storage, index, (size - index + 1));
        storage[index - 1] = r;
    }

    @Override
    protected void deleteArrayResume(int index) {
        System.arraycopy(storage, (index + 1), storage, index, (size - index - 1));
    }

    @Override
    protected Integer findKey(String uuid) {
        Resume indexSearch = new Resume(uuid, "");
        return Arrays.binarySearch(storage, 0, size, indexSearch, RESUME_COMPARATOR_UUID);
    }

}
