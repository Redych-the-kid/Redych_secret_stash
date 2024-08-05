package ru.redych.data;

import ru.redych.data.interfaces.IURLRepository;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Set;

/**
 * URLRepository implementation
 */
public class URLRepository implements IURLRepository {
    private final HashMap<String, Integer> hashMap;

    /**
     * Constructor for URLRepository
     * @param fileName File path to URL table file. Can be either in resources, relative or absolute.
     */
    public URLRepository(String fileName) {
        hashMap = new HashMap<>();
        // Trying to read from relative or absolute path first
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String key = parts[0];
                    Integer value = Integer.valueOf(parts[1]);
                    hashMap.put(key, value);
                }
            }
        } catch (IOException e) {
            // If not succeeded - trying to read default table from resources
            try (InputStream inputStream = URLRepository.class.getClassLoader().getResourceAsStream(fileName);
                 InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                 BufferedReader reader = new BufferedReader(streamReader)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 2) {
                        String key = parts[0];
                        Integer value = Integer.valueOf(parts[1]);
                        hashMap.put(key, value);
                    }
                }
            } catch (IOException exception){
                exception.printStackTrace();
            }
        }
    }

    @Override
    public Integer getCode(String url) {
        return hashMap.get(url);
    }

    @Override
    public Set<String> getSet() {
        return hashMap.keySet();
    }
}
