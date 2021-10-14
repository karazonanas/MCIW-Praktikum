module com.example.aufgabe1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires javafx.graphics;

    opens com.example.aufgabe1 to javafx.fxml;
    exports com.example.aufgabe1;
}