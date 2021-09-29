package com.tracker.utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ReadProperties {
    private static final Logger log = LogManager.getLogger(ReadProperties.class);

    private ReadProperties() {
    }
    public static String readProps(String pathToProps, String propertyToRead) {
        String propertyData;
        try (InputStream inputStream = new FileInputStream(pathToProps)) {
            Properties prop = new Properties();
            prop.load(inputStream);
            propertyData = prop.getProperty(propertyToRead);
        } catch (FileNotFoundException e) {
            log.log(Level.ERROR, String.format("Fail to find %s file", pathToProps), e);
            propertyData = null;
        } catch (IOException e) {
            log.log(Level.ERROR, String.format("Fail to read %s property", propertyToRead), e);
            propertyData = null;
        }
        return propertyData;
    }
}
