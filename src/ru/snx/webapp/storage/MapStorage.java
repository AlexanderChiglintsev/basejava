package ru.snx.webapp.storage;

import ru.snx.webapp.model.Resume;

import java.util.HashMap;

public class MapStorage extends AbstractStorage {
    private HashMap<String, Resume> storage = new HashMap<>();

    @Override
    protected int findIndex(String uuid) {
        return 0;
    }

    @Override
    protected void insertResume(int index, Resume r) {

    }

    @Override
    protected void updateResume(int index, Resume r) {

    }

    @Override
    protected Resume getResume(int index) {
        return null;
    }

    @Override
    protected void deleteResume(int index) {

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
