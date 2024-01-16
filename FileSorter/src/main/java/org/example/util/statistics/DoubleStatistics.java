package org.example.util.statistics;

import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

public class DoubleStatistics {
    public static double min(CopyOnWriteArrayList<Double> list){
        return Collections.min(list);
    }

    public static double max(CopyOnWriteArrayList<Double> list){
        return Collections.max(list);
    }

    public static double sum(CopyOnWriteArrayList<Double> list){
        return list.stream().mapToDouble(Double::doubleValue).sum();
    }

    public static double avg(CopyOnWriteArrayList<Double> list){
        return DoubleStatistics.sum(list) / list.size();
    }
}
