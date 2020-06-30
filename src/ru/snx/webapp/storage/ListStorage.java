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
        int i =0;
        for (Resume r : storage) {
            if (r.getUuid().equals(uuid)) {
                System.out.println("i="+i);
                return i;
            }
            i++;
        }
        return -1;
    }

    @Override
    protected void insertResume(int index, Resume r) {
        storage.add(r);
    }

    @Override
    protected void updateResume(int index, Resume r) {
        storage.set(index, r);
        //storage.add(index, r);
    }

    @Override
    protected Resume getResume(int index) {
        return storage.get(index);
    }

    @Override
    protected void deleteResume(int index) {
        storage.remove(index);
    }

    @Override
    protected boolean checkSize() {
        return false;
    }

    @Override
    public Resume[] getAll() {
        int capacity = storage.size();
        return storage.toArray(new Resume[capacity]);
    }
}