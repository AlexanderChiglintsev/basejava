package ru.snx.webapp.storage;

import ru.snx.webapp.exceptions.StorageException;
import ru.snx.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PathStorage extends AbstractStorage<Path> {
    private Path directory;
    private Serializer serializer;

    PathStorage(String dir, Serializer serializer) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null !!!");
        Objects.requireNonNull(serializer, "serializer must not be null !!!");
        if (!Files.isDirectory(directory) | !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable !!!");
        }
        this.serializer = serializer;
    }

    @Override
    protected boolean checkExist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected Path findKey(String uuid) {
        return Paths.get(directory.toString(), "\\" + uuid);
    }

    @Override
    protected void insertResume(Path path, Resume r) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("IO error !!!", path.getFileName().toString(), e);
        }
        updateResume(path, r);
    }

    @Override
    protected void updateResume(Path path, Resume r) {
        try {
            serializer.doWrite(new BufferedOutputStream(Files.newOutputStream(path)), r);
        } catch (IOException e) {
            throw new StorageException("Path write error !!!", path.getFileName().toString(), e);
        }
    }

    @Override
    protected Resume getResume(Path path) {
        try {
            return serializer.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("IO error !!!", path.getFileName().toString(), e);
        }
    }

    @Override
    protected void deleteResume(Path path) {
        if (!Files.isDirectory(path)) {
            try {
                Files.delete(path);
            } catch (IOException e) {
                throw new StorageException("Delete error !!!", path.getFileName().toString());
            }
        }
    }

    @Override
    protected List<Resume> getAllSortedResume() {
        List<Resume> allres = new ArrayList<>();
        try {
            Files.list(directory).forEach(path -> allres.add(getResume(path)));
        } catch (IOException e) {
            throw new StorageException("IO error !!!", null);
        }
        return allres;
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::deleteResume);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

    @Override
    public int size() {
        int count;
        try {
            count = (int) Files.list(directory).count();
        } catch (IOException e) {
            throw new StorageException("Getting storage size error !!!", null);
        }
        return count;
    }

    public void setSerializer(Serializer serializer) {
        this.serializer = serializer;
    }
}
