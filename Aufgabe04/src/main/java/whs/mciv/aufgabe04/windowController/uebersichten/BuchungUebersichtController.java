package whs.mciv.aufgabe04.windowController.uebersichten;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import whs.mciv.aufgabe04.BaseController;
import whs.mciv.aufgabe04.daten.Datensatz;
import whs.mciv.aufgabe04.daten.buchung.Buchung;
import whs.mciv.aufgabe04.daten.buchung.BuchungDaten;
import whs.mciv.aufgabe04.windowController.formulare.FormularController;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

public class BuchungUebersichtController extends UebersichtController {
    private static final String BUCHUNG_ANLEGEN_VIEW = "formulare/BuchungAnlegen.fxml";
    private static final String BUCHUNG_ANLEGEN_TITLE = "Buchung bearbeiten";
    private static final String BUCHUNG_AUSWAEHLEN_HINWEIS = "Bitte wählen Sie eine Buchung aus";
    private static final String BUCHUNG_LOESCHEN_HINWEIS = "Sind Sie sicher die ausgewählte Buchung zu löschen";
    private static final String BUCHUNG_LOESCHEN_TITEL = "Buchung Löschen";
    private String key;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        listView.getItems().addAll(BuchungDaten.getAllBuchungen());
        initButtons();

        loeschen.setOnAction((ActionEvent event) -> {
            if (BuchungDaten.getBuchung(key) != null) {
                Optional<ButtonType> beenden = BaseController.beende(BUCHUNG_LOESCHEN_HINWEIS, BUCHUNG_LOESCHEN_TITEL);
                if (beenden.isPresent()) {
                    if (Objects.equals(beenden.get().getButtonData().toString(), "YES")) {
                        BuchungDaten.loescheBuchung(BuchungDaten.getBuchung(key));
                        updateListView(BuchungDaten.getAllBuchungen());
                        loeschen.setDisable(true);
                        button1.setDisable(true);
                    }
                }
            } else {
                BaseController.setMessage(Alert.AlertType.WARNING, BUCHUNG_AUSWAEHLEN_HINWEIS);
            }
        });

        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Datensatz>() {
            @Override
            public void changed(ObservableValue<? extends Datensatz> observableValue, Datensatz datensatz, Datensatz t1) {
                StringTokenizer st = new StringTokenizer(String.valueOf(t1), " ");
                key = st.nextToken();
                loeschen.setDisable(false);
                button1.setDisable(false);
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
                formularController.runOnClose();
            }
        } else {
            BaseController.setMessage(Alert.AlertType.WARNING, BUCHUNG_AUSWAEHLEN_HINWEIS);
        }
    }
}
