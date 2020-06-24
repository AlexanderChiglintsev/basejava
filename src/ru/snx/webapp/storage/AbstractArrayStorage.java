package ru.snx.webapp.storage;

import ru.snx.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_CAPACITY = 100000;
    protected Resume[] storage = new Resume[STORAGE_CAPACITY];
    protected int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        int index = findIndex(r.getUuid());
        if (index >= 0) {
            System.out.println("Резюме " + r.getUuid() + " уже существует !!!");
        } else if (size < STORAGE_CAPACITY) {
            insertResume(index, r);
            size++;
        } else System.out.println("База резюме заполнена !!!");
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index < 0) {
            System.out.println("Резюме " + uuid + " отсутствует !!!");
        } else {
            deleteResume(index);
            storage[size - 1] = null;
            size--;
        }
    }

    public void update(Resume r) {
        int index = findIndex(r.getUuid());
        if (index >= 0) {
            storage[index] = r;
        } else {
            System.out.println("Резюме " + r.getUuid() + " отсутствует !!!");
        }
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            return storage[index];
        }
        System.out.println("Резюме " + uuid + " отсутствует !!!");
        return null;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    protected abstract void insertResume(int index, Resume r);

    protected abstract void deleteResume(int index);

    protected abstract int findIndex(String uuid);
}
