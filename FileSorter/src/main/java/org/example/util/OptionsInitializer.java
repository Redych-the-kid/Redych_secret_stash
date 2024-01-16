package org.example.util;

import org.apache.commons.cli.Options;

public class OptionsInitializer {
    private final Options options;

    public OptionsInitializer(Options options) {
        this.options = options;
    }

    public void init(){
        options.addOption("o", true, "Specify the path for result files");
        options.addOption("p", true, "Adds the prefix to output filenames");
        options.addOption("a", false, "Allows to add parsed results to existing result files");
        options.addOption("s", false, "Prints the brief statistics about the sorted output");
        options.addOption("f", false, "Prints the full statistics about the sorted output");
    }
}
