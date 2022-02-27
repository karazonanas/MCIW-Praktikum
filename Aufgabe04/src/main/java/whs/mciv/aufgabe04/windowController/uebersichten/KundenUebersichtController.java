package whs.mciv.aufgabe04.windowController.uebersichten;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import whs.mciv.aufgabe04.BaseController;
import whs.mciv.aufgabe04.daten.N;
import whs.mciv.aufgabe04.daten.buchung.BuchungDaten;
import whs.mciv.aufgabe04.daten.kunde.Kunde;
import whs.mciv.aufgabe04.daten.kunde.KundenDaten;
import whs.mciv.aufgabe04.windowController.formulare.FormularController;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

public class KundenUebersichtController extends UebersichtController {
    private static final String KUNDE_ANLEGEN_VIEW = "formulare/KundeAnlegen.fxml";
    private static final String KUNDE_ANLEGEN_TITLE = "Kunde bearbeiten";
    private static final String KUNDE_AUSWAELEN_HINWEIS = "Bitte wählen Sie einen Kunden aus der Liste aus";
    private static final String KUNDE_LOESCHEN_HINWEIS = "Sind Sie sicher den ausgewählten Kunden zu löschen";
    private static final String KUNDE_LOESCHEN_TITEL = "Kunde Löschen";
    private static final String KUNDE_LOESCHEN_FEHLER_BUCHUNG_VORHANDEN = "Der Kunde kann nicht gelöscht werden, da er mindestens einer Buchung zugewiesen ist.\nLöschen Sie zuerst alle Buchungen, um den Kunden zu entfernen.";

    private String key;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loeschen.setOnAction((ActionEvent event) -> {
            Kunde kunde = KundenDaten.getKunde(key);

            if (kunde != null) {
                if (BuchungDaten.kundeHatBuchung(kunde)) {
                    BaseController.setMessage(Alert.AlertType.WARNING, KUNDE_LOESCHEN_FEHLER_BUCHUNG_VORHANDEN);
                } else {
                    Optional<ButtonType> beenden = BaseController.beende(KUNDE_LOESCHEN_HINWEIS, KUNDE_LOESCHEN_TITEL);
                    if (beenden.isPresent()) {
                        if (Objects.equals(beenden.get().getButtonData().toString(), "YES")) {
                            KundenDaten.loescheKunde(kunde);
                            updateListView(KundenDaten.getAllKunden());
                            loeschen.setDisable(true);
                            button1.setDisable(true);
                        }
                    }
                }
            } else {
                BaseController.setMessage(Alert.AlertType.WARNING, KUNDE_AUSWAELEN_HINWEIS );
            }
        });

        listView.getItems().addAll(KundenDaten.getAllKunden());
        initButtons();
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<N>() {
            @Override
            public void changed(ObservableValue<? extends N> observableValue, N n, N t1) {
                StringTokenizer st = new StringTokenizer(String.valueOf(t1), " ");
                key = st.nextToken();
                loeschen.setDisable(false);
                button1.setDisable(false);
            }
        });
    }

    @Override
    protected void onFirstButton() {
        Kunde kunde = KundenDaten.getKunde(key);
        if (kunde != null) {
            BaseController controller = zeigeDialog(KUNDE_ANLEGEN_VIEW,KUNDE_ANLEGEN_TITLE);
            if (controller instanceof FormularController formularController) {
                formularController.fillForm(kunde);
                formularController.getStage().setOnHidden(h -> updateListView(KundenDaten.getAllKunden()));
            }
        } else {
            BaseController.setMessage(Alert.AlertType.WARNING, KUNDE_AUSWAELEN_HINWEIS );
        }
    }

}
