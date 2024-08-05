package ru.redych;

import org.apache.commons.cli.*;
import ru.redych.data.MessageRepository;
import ru.redych.data.PropertiesRepository;
import ru.redych.data.interfaces.IURLRepository;
import ru.redych.data.URLRepository;
import ru.redych.usecases.GetChangedUsecase;
import ru.redych.usecases.GetDisappearedUsecase;
import ru.redych.usecases.GetNewUsecase;
import ru.redych.util.OptionsInitializer;

public class Main {

    /**
     * Prints the help window
     * @param options Options object from Apache Commons Cli to print the options available
     */
    private static void printHelp(Options options){
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java -jar MonitoringSystem.jar [OPTIONS] [FILENAMES]", options);
    }

    public static void main(String[] args) {
        // Setup the commons cli parser
        CommandLineParser parser = new DefaultParser();
        Options options = new Options();
        OptionsInitializer initializer = new OptionsInitializer(options);
        initializer.init();
        // Prints the help message if asked
        if(args.length == 1 && args[0].equals("-help")){
            printHelp(options);
            return;
        }
        // Parse the command line arguments if there's any
        CommandLine line;
        try{
            line = parser.parse(options, args);
        } catch (ParseException e) {
            printHelp(options);
            return;
        }

        // Initialize the file paths with either default values or with parsed from command line ones
        String yesterdayPath = "yesterday.txt";
        String todayPath = "today.txt";
        String messagePath = "message.txt";
        if (line.hasOption("t1")) {
            yesterdayPath = line.getOptionValue("t1");
        }
        if (line.hasOption("t2")) {
            todayPath = line.getOptionValue("t1");
        }
        if (line.hasOption("m")) {
            messagePath = line.getOptionValue("m");
        }

        // Initialize the repositories
        IURLRepository yesterday = new URLRepository(yesterdayPath);
        IURLRepository today = new URLRepository(todayPath);
        PropertiesRepository propertiesRepository = new PropertiesRepository();
        MessageRepository messageRepository = new MessageRepository(messagePath);

        // Setting up the usecases and getting the message string
        String message = messageRepository.getMessage();
        GetChangedUsecase changedUsecase = new GetChangedUsecase(yesterday, today);
        GetDisappearedUsecase getDisappearedUsecase = new GetDisappearedUsecase(yesterday, today);
        GetNewUsecase getNewUsecase = new GetNewUsecase(yesterday, today);

        // Assembling the result
        String result = message.replace("$changed", changedUsecase.getChanged())
                .replace("$name", propertiesRepository.getPropertyByName("name"))
                .replace("$new", getNewUsecase.getNew())
                .replace("$disappeared", getDisappearedUsecase.getDisappeared());

        // Printing the result
        System.out.println(result);
    }
}