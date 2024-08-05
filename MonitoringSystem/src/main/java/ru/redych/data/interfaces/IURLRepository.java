package ru.redych.data.interfaces;

import java.util.Set;

/**
 * Interface for URL storage
 */
public interface IURLRepository {
    /**
     * Gets status code by URL
     * @param url Target URL
     * @return HTTP status code for given URL
     */
    Integer getCode(String url);

    /**
     * Gets Set of String type of URL's
     * @return Set of String type of URL's
     */
    Set<String> getSet();
}
