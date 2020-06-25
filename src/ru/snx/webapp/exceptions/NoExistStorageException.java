package ru.snx.webapp.exceptions;

public class NoExistStorageException extends StorageException {
    public NoExistStorageException(String uuid) {
        super("Резюме " + uuid + " отсутствует !!!", uuid);
    }
}
