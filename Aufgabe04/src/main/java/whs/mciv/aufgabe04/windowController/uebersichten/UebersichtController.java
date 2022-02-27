package whs.mciv.aufgabe04.windowController.uebersichten;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import whs.mciv.aufgabe04.BaseController;
import whs.mciv.aufgabe04.BuchungsTool;
import whs.mciv.aufgabe04.daten.Datensatz;

import java.util.Collection;

public abstract class UebersichtController extends BaseController {

    @FXML
    protected ListView<Datensatz> listView = new ListView<>();

    @FXML
    protected Button loeschen;

    @Override
    protected void onSecondButton() {
        abbrechen();
    }

    protected BaseController zeigeDialog (String view, String titel) {
        BuchungsTool buchungsTool = new BuchungsTool();
        return buchungsTool.zeigeDialog(view, titel, stage);
    }

    public void updateListView(Collection collection) {
        listView.getItems().clear();
        listView.getItems().addAll(collection);
        loeschen.setDisable(true);
        button1.setDisable(true);
    }
}
