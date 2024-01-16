package org.example.util.statistics;

import java.util.concurrent.CopyOnWriteArrayList;

public class StringStatistics {
    public static int minLen(CopyOnWriteArrayList<String> list){
        int min = Integer.MAX_VALUE;
        for(String str : list){
            if(str.length() < min){
                min = str.length();
            }
        }
        return min;
    }

    public static int maxLen(CopyOnWriteArrayList<String> list){
        int max = -1;
        for(String str : list){
            if(str.length() > max){
                max = str.length();
            }
        }
        return max;
    }
}
