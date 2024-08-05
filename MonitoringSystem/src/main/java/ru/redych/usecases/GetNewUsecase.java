package ru.redych.usecases;

import ru.redych.data.interfaces.IURLRepository;

/**
 * Use case for getting a string of new URLs
 */
public class GetNewUsecase{
    private final IURLRepository yesterday;
    private final IURLRepository today;

    /**
     * Constructor for GetNewUsecase
     * @param yesterday Repository for yesterday's URLs
     * @param today Repository for today's URLs
     */
    public GetNewUsecase(IURLRepository yesterday, IURLRepository today) {
        this.yesterday = yesterday;
        this.today = today;
    }
    public String getNew() {
        StringBuilder builder = new StringBuilder();
        for(String key: today.getSet()){
            if(yesterday.getCode(key) == null) {
                builder.append(key).append(", ");
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
