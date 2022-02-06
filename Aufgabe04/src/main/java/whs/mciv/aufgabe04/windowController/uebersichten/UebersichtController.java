package whs.mciv.aufgabe04.windowController.uebersichten;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import whs.mciv.aufgabe04.BaseController;
import whs.mciv.aufgabe04.daten.N;
import whs.mciv.aufgabe04.daten.kunde.Kunde;

public abstract class UebersichtController extends BaseController {

    @FXML
    protected ListView<N> listView = new ListView<>();

    @Override
    protected void onActionSpeichern() {

    }

    @Override
    protected void onActionAbbrechen() {

    }

    @Override
    protected boolean showController() {
        return false;
    }
}
