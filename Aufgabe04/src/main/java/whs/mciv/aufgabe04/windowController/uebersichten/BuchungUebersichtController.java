package whs.mciv.aufgabe04.windowController.uebersichten;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import whs.mciv.aufgabe04.BaseController;
import whs.mciv.aufgabe04.daten.N;
import whs.mciv.aufgabe04.daten.buchung.Buchung;
import whs.mciv.aufgabe04.daten.buchung.BuchungDaten;
import whs.mciv.aufgabe04.windowController.formulare.FormularController;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

public class BuchungUebersichtController extends UebersichtController {
    private static final String BUCHUNG_ANLEGEN_VIEW = "formulare/BuchungAnlegen.fxml";
    private static final String BUCHUNG_ANLEGEN_TITLE = "Buchung bearbeiten";
    private static final String BUCHUNG_AUSWAEHLEN_HINWEIS = "Bitte wählen Sie eine Buchung aus";
    private String key;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        listView.getItems().addAll(BuchungDaten.getAllBuchungen());
        initButtons();

        loeschen.setOnAction((ActionEvent event) -> {
            if (BuchungDaten.getBuchung(key) != null) {
                BuchungDaten.loescheBuchung(BuchungDaten.getBuchung(key));
                updateListView(BuchungDaten.getAllBuchungen());
            } else {
                BaseController.setMessage(Alert.AlertType.WARNING, BUCHUNG_AUSWAEHLEN_HINWEIS);
            }
        });

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
        Buchung buchung = BuchungDaten.getBuchung(key);
        if (buchung != null) {
            BaseController controller = zeigeDialog(BUCHUNG_ANLEGEN_VIEW, BUCHUNG_ANLEGEN_TITLE);
            if (controller instanceof FormularController formularController) {
                formularController.fillForm(buchung);
                formularController.getStage().setOnHidden(h -> updateListView(BuchungDaten.getAllBuchungen()));
            }
        } else {
            BaseController.setMessage(Alert.AlertType.WARNING, BUCHUNG_AUSWAEHLEN_HINWEIS);
        }
    }
}
