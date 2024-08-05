package ru.redych.usecases;

import ru.redych.data.interfaces.IURLRepository;

/**
 * Use case for getting a string of disappeared URLs
 */
public class GetDisappearedUsecase {
    private final IURLRepository yesterday;
    private final IURLRepository today;

    /**
     * Constructor for GetDisappearedUsecase
     * @param yesterday Repository for yesterday's URLs
     * @param today Repository for today's URLs
     */
    public GetDisappearedUsecase(IURLRepository yesterday, IURLRepository today) {
        this.yesterday = yesterday;
        this.today = today;
    }
    public String getDisappeared(){
        StringBuilder builder = new StringBuilder();
        for(String key: yesterday.getSet()){
            if(today.getCode(key) == null) {
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
