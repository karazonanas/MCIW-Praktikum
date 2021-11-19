package whs.mciv.aufgabe02;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import whs.mciv.aufgabe02.daten.buchung.Buchung;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class BuchungAnlegenController implements Initializable {

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
    private boolean wurdeGespeichert = false;
    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Erzeige neues Buchung-Objekt um die Daten des Formulars später
        // speichern zu können. Der Buchung-Konstruktor erzeugt auch die ID.
        buchung = new Buchung();

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

    public boolean wurdeGespeichert() {
        return wurdeGespeichert;
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
