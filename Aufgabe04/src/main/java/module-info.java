module whs.mciv.aufgabe02 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires javafx.graphics;
    requires java.desktop;

    opens whs.mciv.aufgabe04 to javafx.fxml;
    exports whs.mciv.aufgabe04;
//    exports whs.mciv.aufgabe04.windowController.formulare;
//    opens whs.mciv.aufgabe04.windowController to javafx.fxml;
    exports whs.mciv.aufgabe04.windowController.formulare;
    opens whs.mciv.aufgabe04.windowController.formulare to javafx.fxml;
    exports whs.mciv.aufgabe04.windowController.uebersichten;
    opens whs.mciv.aufgabe04.windowController.uebersichten to javafx.fxml;
}