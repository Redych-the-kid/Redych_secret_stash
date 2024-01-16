package org.example;

import org.apache.commons.cli.*;
import org.example.util.ArrayFileWriter;
import org.example.util.LinesReader;
import org.example.util.OptionsInitializer;
import org.example.util.statistics.DoubleStatistics;
import org.example.util.statistics.LongStatistics;
import org.example.util.statistics.StringStatistics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class Main {
    private static final CopyOnWriteArrayList<Long> intList = new CopyOnWriteArrayList<>();
    private static final CopyOnWriteArrayList<Double> doubleList = new CopyOnWriteArrayList<>();
    private static final CopyOnWriteArrayList<String> stringList = new CopyOnWriteArrayList<>();
    private static final List<FutureTask<Integer>> futureTasks = new ArrayList<>();
    private static String filePath = "";
    private static String filePrefix = "";
    private static boolean appendMode = false;
    private static void printStats(){
        System.out.println("Short stats:");
        System.out.println("Number of ints: " + intList.size());
        System.out.println("Number of floats: " + doubleList.size());
        System.out.println("Number of strings: " + stringList.size());
    }

    private static void printFullStats() {
        System.out.println("Full stats:");
        System.out.println("Number of ints: " + intList.size());
        if(!intList.isEmpty()){
            System.out.println("Min: " + LongStatistics.min(intList));
            System.out.println("Max: " + LongStatistics.max(intList));
            System.out.println("Sum: " + LongStatistics.sum(intList));
            System.out.println("Avg: " + LongStatistics.avg(intList));
            System.out.println();
        }
        if(!doubleList.isEmpty()){
            System.out.println("Number of floats: " + doubleList.size());
            System.out.println("Min: " + DoubleStatistics.min(doubleList));
            System.out.println("Max: " + DoubleStatistics.max(doubleList));
            System.out.println("Sum: " + DoubleStatistics.sum(doubleList));
            System.out.println("Avg: " + DoubleStatistics.avg(doubleList));
            System.out.println();
        }
        if(!stringList.isEmpty()){
            System.out.println("Number of strings: " + stringList.size());
            System.out.println("Min len: " + StringStatistics.minLen(stringList));
            System.out.println("Max len: " + StringStatistics.maxLen(stringList));

        }
    }

    private static void printHelp(Options options){
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java -jar fileSort.jar [OPTIONS] [FILENAMES]", options);
    }

    public static void main(String[] args) {
        CommandLineParser parser = new DefaultParser();
        Options options = new Options();
        OptionsInitializer initializer = new OptionsInitializer(options);
        initializer.init();
        if(args.length < 1){
            printHelp(options);
            return;
        }

        CommandLine line;
        try{
            line = parser.parse(options, args);
        } catch (ParseException e) {
            printHelp(options);
            return;
        }

        ExecutorService service = Executors.newFixedThreadPool(20);
        for(String name: line.getArgs()){
            FutureTask<Integer> futureTask = new FutureTask<>(new LinesReader(name, intList, doubleList, stringList));
            futureTasks.add(futureTask);
            service.execute(futureTask);
        }
        for(FutureTask<Integer> task : futureTasks){
            try{
                // Debug
                System.out.println("File lines count: " + task.get());
            } catch (Exception e){
                System.out.println("Ошибка чтения файла!");
                e.printStackTrace();
            }
        }

        if(line.hasOption("-o")){
            filePath = line.getOptionValue("-o") + "/";
        }
        if(line.hasOption("-p")){
            filePrefix = line.getOptionValue("-p");
        }
        if(line.hasOption("-a")){
            appendMode = true;
        }

        try{
            if(!intList.isEmpty()){
                ArrayFileWriter<Long> intWriter = new ArrayFileWriter<>();
                intWriter.write("integers.txt", filePrefix, filePath, appendMode, intList);
            }
            if(!stringList.isEmpty()){
                ArrayFileWriter<String> stringWriter = new ArrayFileWriter<>();
                stringWriter.write("strings.txt", filePrefix, filePath, appendMode, stringList);

            }
            if(!doubleList.isEmpty()){
                ArrayFileWriter<Double> doubleWriter = new ArrayFileWriter<>();
                doubleWriter.write("floats.txt", filePrefix, filePath, appendMode, doubleList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println();
        if(line.hasOption("-s")){
            printStats();
        }
        if(line.hasOption("-f")){
            printFullStats();
        }
        service.close();
    }
}