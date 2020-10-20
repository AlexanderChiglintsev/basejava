package ru.snx.webapp.storage;

import ru.snx.webapp.exceptions.StorageException;
import ru.snx.webapp.model.Resume;
import ru.snx.webapp.storage.strategy.Serializer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        return directory.resolve(uuid);
    }

    @Override
    protected void insertResume(Path path, Resume r) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("File creating error !!! (NIO)", path.getFileName().toString(), e);
        }
        updateResume(path, r);
    }

    @Override
    protected void updateResume(Path path, Resume r) {
        try {
            serializer.doWrite(new BufferedOutputStream(Files.newOutputStream(path)), r);
        } catch (IOException e) {
            throw new StorageException("File writing error !!! (NIO)", path.getFileName().toString(), e);
        }
    }

    @Override
    protected Resume getResume(Path path) {
        try {
            return serializer.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("IO error !!! (NIO)", path.getFileName().toString(), e);
        }
    }

    @Override
    protected void deleteResume(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("File deleting error !!! (NIO)", path.getFileName().toString());
        }
    }

    @Override
    protected List<Resume> getAllResume() {
        return getStreamFiles().map(this::getResume).collect(Collectors.toList());
    }

    @Override
    public void clear() {
        getStreamFiles().forEach(this::deleteResume);
    }

    @Override
    public int size() {
        return (int) getStreamFiles().count();
    }

    private Stream<Path> getStreamFiles() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Getting stream of files error !!! (NIO)", null);
        }
    }

}
