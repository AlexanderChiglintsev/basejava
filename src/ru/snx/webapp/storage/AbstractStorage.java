package ru.snx.webapp.storage;

import ru.snx.webapp.exceptions.ExistStorageException;
import ru.snx.webapp.exceptions.NoExistStorageException;
import ru.snx.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {

    static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);
    private final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    @Override
    public void update(Resume r) {
        LOG.info("Update: " + r.getUuid() + " | " + r.getFullName());
        updateResume(checkUuidNoExist(r.getUuid()), r);
    }

    @Override
    public void save(Resume r) {
        //LOG.info("Save: " + r.getUuid() + " | " + r.getFullName());
        insertResume(checkUuidExist(r.getUuid()), r);
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get: " + uuid);
        return getResume(checkUuidNoExist(uuid));
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete: " + uuid);
        deleteResume(checkUuidNoExist(uuid));
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> sortedResumes = getAllSortedResume();
        sortedResumes.sort(RESUME_COMPARATOR);
        return sortedResumes;
    }

    private SK checkUuidExist(String uuid) {
        SK key = findKey(uuid);
        if (checkExist(key)) {
            LOG.warning("Резюме " + uuid + " уже существует !!!");
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    private SK checkUuidNoExist(String uuid) {
        SK key = findKey(uuid);
        if (!checkExist(key)) {
            LOG.warning("Резюме " + uuid + " отсутствует !!!");
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
