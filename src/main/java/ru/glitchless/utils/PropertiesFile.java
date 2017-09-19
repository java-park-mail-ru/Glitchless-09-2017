package ru.glitchless.utils;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.*;
import java.util.Properties;

public class PropertiesFile {
    private static final File CONFIGFILE = new File("config.properties");
    private static final Logger LOG = LoggerFactory.getLogger("props");

    private Properties properties = new Properties();

    public PropertiesFile() {
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

    public String getSalt() {
        String salt = properties.getProperty("salt", null);
        if (salt == null) {
            salt = BCrypt.gensalt();
            properties.setProperty("salt", salt);
        }
        return salt;
    }

    @PreDestroy
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
