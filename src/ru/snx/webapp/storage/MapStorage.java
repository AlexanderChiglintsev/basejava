package ru.snx.webapp.storage;

import ru.snx.webapp.model.Resume;

import java.util.HashMap;

public class MapStorage extends AbstractStorage {
    private HashMap<String, Resume> storage = new HashMap<>();

    @Override
    protected boolean checkExist(String uuid) {
        return false;
    }

    @Override
    protected int findIndex(String uuid) {
        return 0;
    }

    @Override
    protected void insertResume(Resume r) {

    }

    @Override
    protected void updateResume(Resume r) {

    }

    @Override
    protected Resume getResume(String uuid) {
        return null;
    }

    @Override
    protected void deleteResume(String uuid) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Resume[] getAll() {
        return new Resume[0];
    }

    @Override
    public int size() {
        return 0;
    }
}
