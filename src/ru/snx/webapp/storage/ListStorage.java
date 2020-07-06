package ru.snx.webapp.storage;

import ru.snx.webapp.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {

    private ArrayList<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected void insertResume(Object index, Resume r) {
        storage.add(r);
    }

    @Override
    protected void updateResume(Object index, Resume r) {
        storage.set((int) index, r);
    }

    @Override
    protected Resume getResume(Object index) {
        return storage.get((int) index);
    }

    @Override
    protected void deleteResume(Object index) {
        storage.remove((int) index);
    }

    @Override
    public Resume[] getAll() {
        int capacity = storage.size();
        return storage.toArray(new Resume[capacity]);
    }

    @Override
    protected boolean checkExist(Object index) {
        return (int) index >= 0;
    }

    protected Object findKey(String uuid) {
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
