package com.example.denchiktranslate;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CurseReader {

    private static Stage stage;

    public static ArrayList<String> load() throws IOException {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select the curses list");
        File file = chooser.showOpenDialog(stage);
        FileReader reader = new FileReader(file.getAbsolutePath());
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line = bufferedReader.readLine();
        ArrayList<String> result = new ArrayList<>();
        while (line != null){
            result.add(line.toLowerCase());
            line = bufferedReader.readLine();
        }
        return result;
    }
}
