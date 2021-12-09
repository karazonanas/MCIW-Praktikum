package whs.mciv.aufgabe02.windowController;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import whs.mciv.aufgabe02.BaseController;
import whs.mciv.aufgabe02.daten.buchung.Buchung;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import whs.mciv.aufgabe02.daten.kunde.Kunde;
import whs.mciv.aufgabe02.daten.kunde.KundenDaten;
import whs.mciv.aufgabe02.daten.reiseziele.Reiseziel;
import whs.mciv.aufgabe02.daten.reiseziele.ReisezielDaten;

public class BuchungAnlegenController extends BaseController {

    @FXML
    private TextField id;

    @FXML
    private ComboBox kunde;

    @FXML
    private TextField buchungsStatus;

    @FXML
    private TextField personenanzahl;

    @FXML
    private DatePicker anreisedatum;

    @FXML
    private TextField anzahlDerNaechte;

    @FXML
    private ComboBox reiseziel;

    @FXML
    private ComboBox verpflegung;

    @FXML
    private TextField gesamtpreis;

    private Buchung buchung;

    private ArrayList<String> reisezielName = new ArrayList<>();

    private ArrayList<String> kundenNamen = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Erzeige neues Buchung-Objekt um die Daten des Formulars später
        // speichern zu können. Der Buchung-Konstruktor erzeugt auch die ID.
        buchung = new Buchung();
        HashMap<String, Reiseziel> reisezieleData = ReisezielDaten.getReiseziele();
        HashMap<String, Kunde> kundenData = KundenDaten.getKunden();

        for (Reiseziel ziel: reisezieleData.values()) {
            if (ziel != null && ziel.getName() != null) {
                this.reisezielName.add(ziel.getName());
            }
        }
        Collections.sort(reisezielName, String.CASE_INSENSITIVE_ORDER);
        this.reiseziel.getItems().setAll(reisezielName);


        for (Kunde kundeDatensatz: kundenData.values()) {
            if (kundeDatensatz != null && kundeDatensatz.getNachname() != null && kundeDatensatz.getVorname() != null) {
                this.kundenNamen.add(kundeDatensatz.getNachname() + ", " + kundeDatensatz.getVorname() + " (" + kundeDatensatz.getId() + ")");
            }
        }
        Collections.sort(kundenNamen, String.CASE_INSENSITIVE_ORDER);
        this.kunde.getItems().setAll(kundenNamen);

        this.id.setText(buchung.getId());

        // Setze Verpflegung
//        List<String> verpflegungAuswahl = new ArrayList<String>();
//        verpflegungAuswahl.add("Halbpension");
//        verpflegungAuswahl.add("Vollpension");
//        ObservableList obList = FXCollections.observableList(verpflegungAuswahl);
//        this.verpflegung.setItems(obList);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void speichern() {
        // Das speichern ist für diese Praktikumsaufgabe noch nicht notwendig.
        wurdeGespeichert = true;
        stage.close();
    }

    @FXML
    private void abbrechen() {
        wurdeGespeichert = false;
        stage.close();
    }
}
