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
        String id = ((Resume) resume).getUuid();
        return storage.get(id);
    }

    @Override
    protected void deleteResume(Object resume) {
        String id = ((Resume) resume).getUuid();
        storage.remove(id);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> allSorted = new ArrayList<>(storage.values());
        allSorted.sort(RESUME_COMPARATOR);
        return allSorted;
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected boolean checkExist(Object resume) {
        if (resume == null) return false;
        Resume r = (Resume) resume;
        return storage.values().contains(r);
    }

    @Override
    protected Object findKey(String uuid) {
        return storage.get(uuid);
    }

}
