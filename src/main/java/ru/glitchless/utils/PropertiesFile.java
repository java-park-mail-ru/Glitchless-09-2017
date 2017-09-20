package ru.glitchless.utils;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;

import javax.annotation.PreDestroy;
import java.io.*;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PropertiesFile implements IPropertiesFile {
    private static final File CONFIGFILE = new File("config.properties");
    private static final Logger LOG = LoggerFactory.getLogger("props");
    private final ExecutorService service;

    private final Properties properties = new Properties();

    public PropertiesFile(@Nullable ExecutorService service) {
        if (service == null) {
            this.service = Executors.newCachedThreadPool();
        } else {
            this.service = service;
        }
        try {
            if (!CONFIGFILE.exists()) {
                if (CONFIGFILE.createNewFile()) {
                    LOG.debug("A config file was successfully created");

                    try (FileInputStream fis = new FileInputStream(CONFIGFILE)) {
                        properties.load(fis);
                    }

                } else {
                    LOG.debug("Error while creating config file");
                }
            } else {

                try (FileInputStream fis = new FileInputStream(CONFIGFILE)) {
                    properties.load(fis);
                }

            }
        } catch (FileNotFoundException e) {
            LOG.error("File not found", e);
        } catch (IOException e) {
            LOG.error("Error while creating file", e);
        }
    }

    @Override
    public String getSalt() {
        String salt = properties.getProperty("salt", null);
        if (salt == null) {
            salt = BCrypt.gensalt();
            properties.setProperty("salt", salt);
            asyncSave();
        }
        return salt;
    }

    public void asyncSave() {
        service.execute(this::save);
    }

    @PreDestroy
    @Override
    public void save() {
        try {
            try (FileOutputStream fos = new FileOutputStream(CONFIGFILE)) {
                properties.store(fos, null);
            }
        } catch (FileNotFoundException e) {
            LOG.error("File not found", e);
        } catch (IOException e) {
            LOG.error("Error while creating file", e);
        }
    }
}
