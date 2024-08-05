package ru.redych.usecases;

import ru.redych.data.interfaces.IURLRepository;

import java.util.Objects;

/**
 * Use case for getting a string of changed URLs
 */
public class GetChangedUsecase {
    private final IURLRepository yesterday;
    private final IURLRepository today;

    /**
     * Constructor for GetChangedUsecase
     * @param yesterday Repository for yesterday's URLs
     * @param today Repository for today's URLs
     */
    public GetChangedUsecase(IURLRepository yesterday, IURLRepository today) {
        this.yesterday = yesterday;
        this.today = today;
    }

    /**
     * Gets all changed URLs
     * @return String of all changed URLs
     */
    public String getChanged() {
        StringBuilder builder = new StringBuilder();
        for(String key: today.getSet()){
            if(null != yesterday.getCode(key)) {
                if(!Objects.equals(yesterday.getCode(key), today.getCode(key))){
                    builder.append(key).append(", ");
                }
            }
        }
        // We don't need the last comma
        int latsComma = builder.lastIndexOf(", ");
        if(latsComma != -1) {
            builder.delete(latsComma, latsComma + 2);
        }
        return builder.toString();
    }
}
