package ru.snx.webapp.storage;

import ru.snx.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private static final int STORAGE_CAPACITY = 10000;
    private Resume[] storage = new Resume[STORAGE_CAPACITY];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume r) {
        int index = findIndex(r.getUuid());
        if (index != -1) {
            storage[index] = r;
        } else {
            System.out.println("Резюме " + r.getUuid() + " отсутствует !!!");
        }
    }

    public void save(Resume r) {
        if (size < STORAGE_CAPACITY) {
            int index = findIndex(r.getUuid());
            if (index == -1) {
                storage[size] = r;
                size++;
            } else {
                System.out.println("Резюме " + r.getUuid() + " уже существует !!!");
            }
        } else {
            System.out.println("База резюме переполнена !!!");
        }
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index != -1) {
            return storage[index];
        }
        System.out.println("Резюме " + uuid + " отсутствует !!!");
        return null;
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index != -1) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("Резюме " + uuid + " отсутствует !!!");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    /*Поиск резюме по uuid
    Возвращает индекс элемента в storage, если найдено,
    в противном случае -1*/
    private int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
