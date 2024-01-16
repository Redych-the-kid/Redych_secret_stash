package org.example.util;

import org.apache.commons.lang3.math.NumberUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
public class LinesReader implements Callable<Integer> {
    private final String fileName;
    private final CopyOnWriteArrayList<Long> intList;
    private final CopyOnWriteArrayList<Double> doubleList;
    private final CopyOnWriteArrayList<String> stringList;

    public LinesReader(String fileName, CopyOnWriteArrayList<Long> intList, CopyOnWriteArrayList<Double> doubleList, CopyOnWriteArrayList<String> stringList){
        this.fileName = fileName;
        this.intList = intList;
        this.doubleList = doubleList;
        this.stringList = stringList;
    }

    @Override
    public Integer call() throws Exception {
        List<String> lines = Files.readAllLines(Path.of(fileName));
        for(String line: lines){
            if (NumberUtils.isCreatable(line)) {
                try{
                    Long integer = Long.parseLong(line);
                    intList.add(integer);
                } catch (NumberFormatException e){
                    Double dbl = Double.parseDouble(line);
                    doubleList.add(dbl);
                }
            } else{
                stringList.add(line);
            }
        }
        return lines.size();
    }
}
