package ru.snx.webapp.storage;

import ru.snx.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume r) {
        int index = findIndex(r.getUuid());
        if (index >= 0) {
            System.out.println("Резюме " + r.getUuid() + " уже существует !!!");
        } else {
            System.out.println(size-(Math.abs(index)-1));
            System.arraycopy(storage,(Math.abs(index)-1),storage,Math.abs(index),(size-(Math.abs(index)-1)));
            storage[Math.abs(index)-1] = r;
            size++;
        }
    }

    @Override
    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index < 0) {
            System.out.println("Резюме " + uuid + " отсутствует !!!");
        } else {
            System.out.println(size-Math.abs(index)-1);
            System.arraycopy(storage,(Math.abs(index)+1),storage,(Math.abs(index)),(size-Math.abs(index)-1));
            storage[size-1] = null;
            size--;
        }
    }

    @Override
    protected int findIndex(String uuid) {
        Resume indexSearch = new Resume();
        indexSearch.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, indexSearch);
    }
}
