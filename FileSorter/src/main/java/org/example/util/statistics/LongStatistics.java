package org.example.util.statistics;

import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

public class LongStatistics {
    public static long min(CopyOnWriteArrayList<Long> list){
        return Collections.min(list);
    }
    public static long max(CopyOnWriteArrayList<Long> list){
        return Collections.max(list);
    }

    public static long sum(CopyOnWriteArrayList<Long> list){
        return list.stream().mapToLong(Long::longValue).sum();
    }

    public static long avg(CopyOnWriteArrayList<Long> list){
        return LongStatistics.sum(list) / list.size();
    }
}
