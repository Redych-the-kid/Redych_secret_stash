package ru.redych.util;

import org.apache.commons.cli.Options;

/**
 * Utility class to init the Apache Commons Cli options parser
 */
public class OptionsInitializer {
    private final Options options;

    /**
        Constructor for options initializer
        @param options Options object from Commons Cli
     */
    public OptionsInitializer(Options options) {
        this.options = options;
    }

    /**
     * Initializes the option object
     */
    public void init(){
        options.addOption("t1", true, "Path to table of yesterday sites");
        options.addOption("t2", true, "Path to table of today sites");
        options.addOption("m", true, "Path to message file");
    }
}
