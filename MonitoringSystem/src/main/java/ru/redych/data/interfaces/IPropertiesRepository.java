package ru.redych.data.interfaces;

/**
 * Interface for our properties storage
 */
public interface IPropertiesRepository {
    /**
     * Gets property from properties repository
     * @param name Name of the property to get
     * @return Property string
     */
    String getPropertyByName(String name);
}
