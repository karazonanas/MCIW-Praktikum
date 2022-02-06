package whs.mciv.aufgabe04.windowController.uebersichten;

import javafx.scene.control.ListView;
import whs.mciv.aufgabe04.BaseController;
import whs.mciv.aufgabe04.daten.buchung.Buchung;
import whs.mciv.aufgabe04.daten.buchung.BuchungDaten;

import java.net.URL;
import java.util.ResourceBundle;

public class BuchungUebersichtController extends UebersichtController {



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        listView.getItems().addAll(BuchungDaten.getAllBuchungen());
//        listView.getItems().addAll();
    }
}
