package ru.snx.webapp.storage;

import ru.snx.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapResumeStorage extends AbstractStorage {
    private HashMap<String, Resume> storage = new HashMap<>();

    @Override
    protected void insertResume(Object resume, Resume r) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void updateResume(Object resume, Resume r) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected Resume getResume(Object resume) {
        return (Resume) resume;
    }

    @Override
    protected void deleteResume(Object resume) {
        String id = ((Resume) resume).getUuid();
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
    protected boolean checkExist(Object resume) {
        return resume != null;
    }

    @Override
    protected Object findKey(String uuid) {
        return storage.get(uuid);
    }

}
