package ru.redych.data;

import ru.redych.data.interfaces.IPropertiesRepository;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * PropertiesRepository implementation
 */
public class PropertiesRepository implements IPropertiesRepository {
    private final Properties properties = new Properties();

    /**
     * Constructor for PropertiesRepository
     */
    public PropertiesRepository() {
        // Trying to read properties file from resources
        try (InputStream inputStream = PropertiesRepository.class.getClassLoader().getResourceAsStream("application.properties");
             InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            properties.load(bufferedReader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public String getPropertyByName(String name) {
        return properties.getProperty(name);
    }
}
