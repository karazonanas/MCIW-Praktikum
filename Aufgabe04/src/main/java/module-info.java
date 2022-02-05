module whs.mciv.aufgabe02 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires javafx.graphics;
    requires java.desktop;

    opens whs.mciv.aufgabe04 to javafx.fxml;
    exports whs.mciv.aufgabe04;
    exports whs.mciv.aufgabe04.windowController;
    opens whs.mciv.aufgabe04.windowController to javafx.fxml;
}