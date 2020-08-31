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
            if (!file.createNewFile()){
                throw new StorageException("Creating file error !!!", file.getName());
            }
        } catch (IOException e) {
            throw new StorageException("IO error !!!", file.getName(), e);
        }
        updateResume(file, r);
    }

    @Override
    protected void updateResume(File file, Resume r) {
        try {
            doWrite(file, r);
        } catch (IOException e) {
            throw new StorageException("File write error !!!", file.getName(), e);
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
        if (!file.delete()) {
            throw new StorageException("Delete error !!!", file.getName());
        }

    }

    @Override
    protected List<Resume> getAllSortedResume() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Delete error !!!", null);
        }
        List<Resume> allres = new ArrayList<>();
        for (File file : files) {
            allres.add(getResume(file));
        }
        return allres;
    }

    @Override
    public void clear() {
        File[] files = directory.listFiles();
        if (files != null)
            for (File f : files) {
                deleteResume(f);
            }
    }

    @Override
    public int size() {
        String[] list = directory.list();
        if (list == null) {
            throw new StorageException("IO error !!!", null);
        }
        return list.length;
    }

    abstract void doWrite(File file, Resume r) throws IOException;

    abstract Resume doRead(File file) throws IOException;
}
