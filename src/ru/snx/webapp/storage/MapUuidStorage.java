package ru.snx.webapp.storage;

import ru.snx.webapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage<String> {
    private HashMap<String, Resume> storage = new HashMap<>();

    @Override
    protected void insertResume(String uuid, Resume r) {
        storage.put(uuid, r);
    }

    @Override
    protected void updateResume(String uuid, Resume r) {
        storage.put(uuid, r);
    }

    @Override
    protected Resume getResume(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void deleteResume(String uuid) {
        storage.remove(uuid);
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
    protected boolean checkExist(String uuid) {
        return storage.containsKey(uuid);
    }

    @Override
    protected String findKey(String uuid) {
        return uuid;
    }

}
