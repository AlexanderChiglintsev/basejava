package ru.snx.webapp.storage;

import ru.snx.webapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage {
    private HashMap<String, Resume> storage = new HashMap<>();

    @Override
    protected void insertResume(Object uuid, Resume r) {
        storage.put((String) uuid, r);
    }

    @Override
    protected void updateResume(Object uuid, Resume r) {
        storage.put((String) uuid, r);
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
    protected List<Resume> getAllSortedResume() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected boolean checkExist(Object uuid) {
        String id = (String) uuid;
        return storage.containsKey(id);
    }

    @Override
    protected Object findKey(String uuid) {
        return uuid;
    }

}
