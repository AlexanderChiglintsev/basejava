package ru.snx.webapp.exceptions;

public class ExistStorageException extends StorageException {
    public ExistStorageException(String uuid) {
        super("Резюме " + uuid + " уже существует !!!", uuid);
    }

    public ExistStorageException(String message, Exception e) {
        super(message, e);
    }

}
