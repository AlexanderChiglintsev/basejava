package ru.snx.webapp.storage;

import ru.snx.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapResumeStorage extends AbstractStorage<Resume> {
    private HashMap<String, Resume> storage = new HashMap<>();

    @Override
    protected void insertResume(Resume resume, Resume r) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void updateResume(Resume resume, Resume r) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected Resume getResume(Resume resume) {
        return resume;
    }

    @Override
    protected void deleteResume(Resume resume) {
        String id = resume.getUuid();
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
    protected boolean checkExist(Resume resume) {
        return resume != null;
    }

    @Override
    protected Resume findKey(String uuid) {
        return storage.get(uuid);
    }

}
