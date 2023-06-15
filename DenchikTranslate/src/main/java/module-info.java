module com.example.denchiktranslate {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.denchiktranslate to javafx.fxml;
    exports com.example.denchiktranslate;
}