package ru.snx.webapp.storage;

import ru.snx.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {

    private List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected void insertResume(Integer index, Resume r) {
        storage.add(r);
    }

    @Override
    protected void updateResume(Integer index, Resume r) {
        storage.set(index, r);
    }

    @Override
    protected Resume getResume(Integer index) {
        return storage.get(index);
    }

    @Override
    protected void deleteResume(Integer index) {
        storage.remove(index.intValue());
    }

    @Override
    protected List<Resume> getAllSortedResume() {
        return new ArrayList<>(storage);
    }

    @Override
    protected boolean checkExist(Integer index) {
        return index >= 0;
    }

    protected Integer findKey(String uuid) {
        int i = 0;
        for (Resume r : storage) {
            if (r.getUuid().equals(uuid)) {
                return i;
            }
            i++;
        }
        return -1;
    }
}
