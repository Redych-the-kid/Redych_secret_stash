package com.example.denchiktranslate;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private TextArea inputTextArea;

    @FXML
    private TextArea outputTextArea;

    @FXML
    private Button translateButton;

    @FXML
    private Button loadButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        translateButton.setOnAction(actionEvent -> {
            outputTextArea.clear();
            outputTextArea.setText(TranslationUtilities.translateString(inputTextArea.getText()));
        });
        loadButton.setOnAction(actionEvent ->{
            try {
                TranslationUtilities.setCurses(CurseReader.load());
            } catch (IOException e) {
                TranslationUtilities.setCurses(new ArrayList<>(Arrays.asList("блять", "сука", "нахуй", "ну типа", "пенис", "похуй")));
            }
        });
    }
}