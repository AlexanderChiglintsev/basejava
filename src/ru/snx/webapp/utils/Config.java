package ru.snx.webapp.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final File CONFIG = new File("config\\resumes.properties");
    private static final Config INSTANCE = new Config();
    private File storageDir;

    public static Config getInstance() {
        return INSTANCE;
    }

    private Config() {
        try(InputStream is = new FileInputStream(CONFIG)) {
            Properties props = new Properties();
            props.load(is);
            storageDir = new File(props.getProperty("storage.dir"));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file "+ CONFIG.getAbsolutePath());
        }
    }

    public File getStorageDir() {
        return storageDir;
    }
}
