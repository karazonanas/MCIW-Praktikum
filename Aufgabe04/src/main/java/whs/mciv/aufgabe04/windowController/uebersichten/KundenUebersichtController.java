package whs.mciv.aufgabe04.windowController.uebersichten;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.MultipleSelectionModel;
import whs.mciv.aufgabe04.BaseController;
import whs.mciv.aufgabe04.daten.N;
import whs.mciv.aufgabe04.daten.kunde.Kunde;
import whs.mciv.aufgabe04.daten.kunde.KundenDaten;
import whs.mciv.aufgabe04.windowController.formulare.FormularController;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

public class KundenUebersichtController extends UebersichtController {
    private static final String KUNDE_ANLEGEN_VIEW = "formulare/KundeAnlegen.fxml";
    private static final String KUNDE_ANLEGEN_TITLE = "Kunde bearbeiten";

    private String key;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listView.getItems().addAll(KundenDaten.getAllKunden());
        initButtons();
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<N>() {
            @Override
            public void changed(ObservableValue<? extends N> observableValue, N n, N t1) {
                StringTokenizer st = new StringTokenizer(String.valueOf(t1), " ");
                key = st.nextToken();
            }
        });
    }

    @Override
    protected void onFirstButton() {
        Kunde kunde = KundenDaten.getKunde(key);
        BaseController controller = zeigeDialog(KUNDE_ANLEGEN_VIEW,KUNDE_ANLEGEN_TITLE);
        if (controller instanceof FormularController)
            ((FormularController) controller).fillForm(kunde);
        listView.getItems().addAll(KundenDaten.getAllKunden());

    }
}
