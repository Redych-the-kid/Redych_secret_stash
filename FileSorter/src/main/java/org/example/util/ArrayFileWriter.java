package org.example.util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

public class ArrayFileWriter<T> {
    public void write(String fileName, String filePrefix, String filePath, boolean appendMode, CopyOnWriteArrayList<T> list) throws IOException {
        FileWriter writer = new FileWriter(filePath + filePrefix + fileName, appendMode);
        for(T el: list){
            writer.write(el + System.lineSeparator());
        }
        writer.close();
    }
}
