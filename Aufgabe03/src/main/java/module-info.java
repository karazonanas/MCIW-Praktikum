module whs.mciv.aufgabe02 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires javafx.graphics;
    requires java.desktop;

    opens whs.mciv.aufgabe02 to javafx.fxml;
    exports whs.mciv.aufgabe02;
    exports whs.mciv.aufgabe02.windowController;
    opens whs.mciv.aufgabe02.windowController to javafx.fxml;
}