package ru.snx.webapp.storage;

import ru.snx.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insertResume(int index, Resume r) {
        storage[size] = r;
        size++;
    }

    @Override
    protected void deleteResume(int index) {
        storage[index] = storage[size - 1];
        size--;
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