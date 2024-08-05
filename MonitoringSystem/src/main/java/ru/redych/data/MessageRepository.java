package ru.redych.data;

import ru.redych.data.interfaces.IMessageRepository;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Message repository implementation
 */
public class MessageRepository implements IMessageRepository {

    private final String messageFile;

    /**
     * Constructor for MessageRepository
     * @param messageFile File path to messageFile. Can be either in resources, relative or absolute.
     */
    public MessageRepository(String messageFile) {
        this.messageFile = messageFile;
    }
    @Override
    public String getMessage() {
        // Trying to read from relative or absolute path first
        try (BufferedReader reader = new BufferedReader(new FileReader(messageFile))){
            StringBuilder messageBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                messageBuilder.append(line).append("\n");
            }
            return messageBuilder.toString();
        } catch (Exception e) {
            // If not succeeded - trying to read default message from resources
            try (InputStream inputStream = MessageRepository.class.getClassLoader().getResourceAsStream(messageFile);
                 InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                 BufferedReader reader = new BufferedReader(streamReader)) {
                StringBuilder messageBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    messageBuilder.append(line).append("\n");
                }
                return messageBuilder.toString();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        // Worst case scenario
        return "No message!";
    }
}
