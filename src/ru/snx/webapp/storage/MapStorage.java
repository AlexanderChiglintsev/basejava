package ru.snx.webapp.storage;

import ru.snx.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MapStorage extends AbstractStorage {
    private HashMap<String, Resume> storage = new HashMap<>();

    @Override
    protected void insertResume(int index, Resume r) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void updateResume(int index, Resume r) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected Resume getResume(int index, String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void deleteResume(int index, String uuid) {
        storage.remove(uuid);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        Set<Map.Entry<String, Resume>> set = storage.entrySet();
        Resume[] resumes = new Resume[storage.size()];
        int i = 0;
        for (Map.Entry<String, Resume> me : set) {
            resumes[i] = me.getValue();
            i++;
        }
        return resumes;
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected int findIndex(String uuid) {
        if (storage.containsKey(uuid)) {
            return 1;
        }
        return -1;
    }
}
