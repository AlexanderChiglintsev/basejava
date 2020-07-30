package ru.snx.webapp.storage;

import ru.snx.webapp.exceptions.ExistStorageException;
import ru.snx.webapp.exceptions.NoExistStorageException;
import ru.snx.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage<SK> implements Storage {

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
        List<Resume> sortedResumes = getAllSortedResume();
        sortedResumes.sort(RESUME_COMPARATOR);
        return sortedResumes;
    }

    private SK checkUuidExist(String uuid) {
        SK key = findKey(uuid);
        if (checkExist(key)) {
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    private SK checkUuidNoExist(String uuid) {
        SK key = findKey(uuid);
        if (!checkExist(key)) {
            throw new NoExistStorageException(uuid);
        }
        return key;
    }

    protected abstract boolean checkExist(SK key);

    protected abstract SK findKey(String uuid);

    protected abstract void insertResume(SK key, Resume r);

    protected abstract void updateResume(SK key, Resume r);

    protected abstract Resume getResume(SK key);

    protected abstract void deleteResume(SK key);

    protected abstract List<Resume> getAllSortedResume();

}
