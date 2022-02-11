package whs.mciv.aufgabe04.windowController.uebersichten;

import whs.mciv.aufgabe04.daten.buchung.BuchungDaten;
import java.net.URL;
import java.util.ResourceBundle;

public class BuchungUebersichtController extends UebersichtController {
    private static final String BUCHUNG_ANLEGEN_VIEW = "formulare/BuchungAnlegen.fxml";
    private static final String BUCHUNG_ANLEGEN_TITLE = "Buchung bearbeiten";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        listView.getItems().addAll(BuchungDaten.getAllBuchungen());
        initButtons();
    }

    @Override
    protected void onFirstButton() {
        zeigeDialog(BUCHUNG_ANLEGEN_VIEW, BUCHUNG_ANLEGEN_TITLE);
    }
}
