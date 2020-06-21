package ru.snx.webapp.storage;

import ru.snx.webapp.model.Resume;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, null);
        size = 0;
    }

    public void update(Resume r) throws IOException {
        int index = checkExists(r.getUuid());
        if (index != -1) {
            System.out.println("Укажите новое значение uuid: ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            storage[index].setUuid(reader.readLine().trim().toLowerCase());
        } else {
            System.out.println("Нет такого резюме !!!");
        }
    }

    public void save(Resume r) {
        if (size < 10000) {
            int index = checkExists(r.getUuid());
            if (index == -1) {
                storage[size] = r;
                size++;
            } else {
                System.out.println("Резюме с таким uuid уже существует !!!");
            }
        } else {
            System.out.println("База резюме переполнена !!!");
        }
    }

    public Resume get(String uuid) {
        int index = checkExists(uuid);
        if (index != -1) {
            return storage[index];
        } else {
            System.out.println("Нет такого резюме !!!");
        }
        return null;
    }

    public void delete(String uuid) {
        int index = checkExists(uuid);
        if (index != -1) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("Нет такого резюме !!!");
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
    private int checkExists(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
