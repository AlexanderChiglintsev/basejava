package ru.snx.webapp.storage;

import ru.snx.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insertArrayResume(int index, Resume r) {
        storage[size] = r;
    }

    @Override
    protected void deleteArrayResume(int index) {
        storage[index] = storage[size - 1];
    }

    /*Поиск резюме по uuid
    Возвращает индекс элемента в storage, если найдено,
    в противном случае -1*/
    @Override
    protected Object findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}