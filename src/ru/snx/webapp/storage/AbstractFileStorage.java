package ru.snx.webapp.storage;

import ru.snx.webapp.exceptions.StorageException;
import ru.snx.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private File directory;

    public AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null !!!");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory !!!");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable !!!");
        }
        this.directory = directory;
    }

    @Override
    protected boolean checkExist(File file) {
        return file.exists();
    }

    @Override
    protected File findKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void insertResume(File file, Resume r) {
        try {
            file.createNewFile();
            doWrite(file, r);
        } catch (IOException e) {
            throw new StorageException("IO error !!!", file.getName(), e);
        }
    }

    @Override
    protected void updateResume(File file, Resume r) {
        try {
            doWrite(file, r);
        } catch (IOException e) {
            throw new StorageException("IO error !!!", file.getName(), e);
        }
    }

    @Override
    protected Resume getResume(File file) {
        try {
            return doRead(file);
        } catch (IOException e) {
            throw new StorageException("IO error !!!", file.getName(), e);
        }
    }

    @Override
    protected void deleteResume(File file) {
        file.delete();
    }

    @Override
    protected List<Resume> getAllSortedResume() {
        File[] files = directory.listFiles();
        List<Resume> allres = new ArrayList<>();
        for (File file : files) {
            try {
                allres.add(doRead(file));
            } catch (IOException e) {
                throw new StorageException("IO error !!!", file.getName(), e);
            }
        }
        return allres;
    }

    @Override
    public void clear() {
        File[] files = directory.listFiles();
        for (File f : files) {
            f.delete();
        }
    }

    @Override
    public int size() {
        File[] files = directory.listFiles();
        return files != null ? files.length : 0;
    }

    abstract void doWrite(File file, Resume r) throws IOException;

    abstract Resume doRead(File file) throws IOException;
}
