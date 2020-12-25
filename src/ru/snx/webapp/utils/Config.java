package ru.snx.webapp.utils;

import ru.snx.webapp.storage.SqlStorage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final File CONFIG = new File(getHomeDir(), "config\\resumes.properties");
    private static final Config INSTANCE = new Config();
    private File storageDir;
    private SqlStorage sqlStorage;

    public static Config getInstance() {
        return INSTANCE;
    }

    private Config() {
        try (InputStream is = new FileInputStream(CONFIG)) {
            Properties props = new Properties();
            props.load(is);
            storageDir = new File(props.getProperty("storage.dir"));
            sqlStorage = new SqlStorage(props.getProperty("db.url"),
                    props.getProperty("db.user"),
                    props.getProperty("db.password"));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + CONFIG.getAbsolutePath());
        }
    }

    public File getStorageDir() {
        return storageDir;
    }

    public SqlStorage getSqlStorage() {
        return sqlStorage;
    }

    private static File getHomeDir() {
        String property = System.getProperty("homeDir");
        File homeDir = new File(property == null ? "." : property);
        if (!homeDir.isDirectory()) {
            throw new IllegalStateException(homeDir + " not is directory!");
        }
        return homeDir;
    }
}
