package ru.snx.webapp.storage;

import ru.snx.webapp.exceptions.StorageException;
import ru.snx.webapp.model.Resume;
import ru.snx.webapp.storage.strategy.Serializer;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private File directory;
    private Serializer serializer;

    FileStorage(File directory, Serializer serializer) {
        Objects.requireNonNull(directory, "directory must not be null !!!");
        Objects.requireNonNull(serializer, "serializer must not be null !!!");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory !!!");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable !!!");
        }
        this.directory = directory;
        this.serializer = serializer;
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
            if (file.createNewFile()) {
                updateResume(file, r);
            }
        } catch (IOException e) {
            throw new StorageException("File creating error !!!", file.getName(), e);
        }
    }

    @Override
    protected void updateResume(File file, Resume r) {
        try {
            serializer.doWrite(new BufferedOutputStream(new FileOutputStream(file)), r);
        } catch (IOException e) {
            throw new StorageException("File writing error !!!", file.getName(), e);
        }
    }

    @Override
    protected Resume getResume(File file) {
        try {
            return serializer.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO error !!!", file.getName(), e);
        }
    }

    @Override
    protected void deleteResume(File file) {
        if (!file.delete()) {
            throw new StorageException("File deleting error !!!", file.getName());
        }

    }

    @Override
    protected List<Resume> getAllResume() {
        List<Resume> allResumes = new ArrayList<>();
        Arrays.stream(getListFiles()).forEach(file -> allResumes.add(getResume(file)));
        return allResumes;
    }

    @Override
    public void clear() {
        Arrays.stream(getListFiles()).forEach(this::deleteResume);
    }

    @Override
    public int size() {
        return getListFiles().length;
    }

    private File[] getListFiles() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Getting list files in storage error !!!", null);
        }
        return files;
    }

}
