module whs.mciv.aufgabe02 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires javafx.graphics;

    opens whs.mciv.aufgabe02 to javafx.fxml;
    exports whs.mciv.aufgabe02;
}