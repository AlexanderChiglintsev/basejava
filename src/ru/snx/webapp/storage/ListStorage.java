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
    protected void insertResume(Resume r) {
        storage.add(r);
    }

    @Override
    protected void updateResume(Resume r) {
        storage.set((int) findIndex(r.getUuid()), r);
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
    protected boolean checkExist(String uuid) {
        for (Resume r : storage) {
            if (r.getUuid().equals(uuid)) {
                return true;
            }
        }
        return false;
    }

    protected Object findIndex(String uuid) {
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
