package ru.snx.webapp.storage;

import ru.snx.webapp.model.Resume;

import java.util.HashMap;

public class MapStorage extends AbstractStorage {
    private HashMap<String, Resume> storage = new HashMap<>();

    @Override
    protected void insertResume(Resume r) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void updateResume(Resume r) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected Resume getResume(Object uuid) {
        String id = (String) uuid;
        return storage.get(id);
    }

    @Override
    protected void deleteResume(Object uuid) {
        String id = (String) uuid;
        storage.remove(id);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        int capacity = storage.size();
        return storage.values().toArray(new Resume[capacity]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected boolean checkExist(String uuid) {
        return storage.containsKey(uuid);
    }

    @Override
    protected Object findIndex(String uuid) {
        return uuid;
    }
}
