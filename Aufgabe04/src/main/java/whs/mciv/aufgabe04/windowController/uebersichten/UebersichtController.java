package whs.mciv.aufgabe04.windowController.uebersichten;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import whs.mciv.aufgabe04.BaseController;
import whs.mciv.aufgabe04.BuchungsTool;
import whs.mciv.aufgabe04.daten.N;

import java.util.StringTokenizer;

public abstract class UebersichtController extends BaseController {

    @FXML
    protected ListView<N> listView = new ListView<>();

    @Override
    protected void onSecondButton() {
        abbrechen();
    }

    protected BaseController zeigeDialog (String view, String titel) {
        BuchungsTool buchungsTool = new BuchungsTool();
        return buchungsTool.zeigeDialog(view, titel, stage);
    }
}
