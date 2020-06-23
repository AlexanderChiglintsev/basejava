package ru.snx.webapp.storage;

import ru.snx.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {


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
            System.out.println("База резюме заполнена !!!");
        }
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

    /*Поиск резюме по uuid
    Возвращает индекс элемента в storage, если найдено,
    в противном случае -1*/
    @Override
    protected int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}