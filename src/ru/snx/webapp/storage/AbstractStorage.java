package ru.snx.webapp.storage;

import ru.snx.webapp.exceptions.ExistStorageException;
import ru.snx.webapp.exceptions.NoExistStorageException;
import ru.snx.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage implements Storage {

    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    @Override
    public void update(Resume r) {
        updateResume(checkUuidNoExist(r.getUuid()), r);
    }

    @Override
    public void save(Resume r) {
        insertResume(checkUuidExist(r.getUuid()), r);
    }

    @Override
    public Resume get(String uuid) {
        return getResume(checkUuidNoExist(uuid));
    }

    @Override
    public void delete(String uuid) {
        deleteResume(checkUuidNoExist(uuid));
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> allSorted = getAllSortedResume();
        allSorted.sort(RESUME_COMPARATOR);
        return allSorted;
    }

    private Object checkUuidExist(String uuid) {
        Object key = findKey(uuid);
        if (checkExist(key)) {
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    private Object checkUuidNoExist(String uuid) {
        Object key = findKey(uuid);
        if (!checkExist(key)) {
            throw new NoExistStorageException(uuid);
        }
        return key;
    }

    protected abstract boolean checkExist(Object key);

    protected abstract Object findKey(String uuid);

    protected abstract void insertResume(Object key, Resume r);

    protected abstract void updateResume(Object key, Resume r);

    protected abstract Resume getResume(Object key);

    protected abstract void deleteResume(Object key);

    protected abstract List<Resume> getAllSortedResume();

}
