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
    protected int findIndex(String uuid) {
        int i = 0;
        for (Resume r : storage) {
            if (r.getUuid().equals(uuid)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    @Override
    protected void insertResume(Resume r) {
        storage.add(r);
    }

    @Override
    protected void updateResume(Resume r) {
        storage.set(findIndex(r.getUuid()), r);
    }

    @Override
    protected Resume getResume(String uuid) {
        return storage.get(findIndex(uuid));
    }

    @Override
    protected void deleteResume(String uuid) {
        storage.remove(findIndex(uuid));
    }

    @Override
    public Resume[] getAll() {
        int capacity = storage.size();
        return storage.toArray(new Resume[capacity]);
    }

    @Override
    protected boolean checkExist(String uuid) {
        return findIndex(uuid) >= 0;
    }
}
