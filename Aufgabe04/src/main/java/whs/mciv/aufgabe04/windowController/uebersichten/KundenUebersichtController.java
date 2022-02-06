package whs.mciv.aufgabe04.windowController.uebersichten;

import whs.mciv.aufgabe04.BaseController;
import whs.mciv.aufgabe04.daten.kunde.KundenDaten;

import java.net.URL;
import java.util.ResourceBundle;

public class KundenUebersichtController extends UebersichtController {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listView.getItems().addAll(KundenDaten.getAllKunden());
    }
}
