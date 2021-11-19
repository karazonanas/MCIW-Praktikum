package whs.mciv.aufgabe02;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import whs.mciv.aufgabe02.daten.kunde.Kunde;

import java.net.URL;
import java.util.ResourceBundle;

public class KundeAnlegenController implements Initializable {

    @FXML
    private TextField id;

    @FXML
    private ComboBox anrede;

    @FXML
    private TextField vorname;

    @FXML
    private TextField nachname;

    @FXML
    private TextField adresse;

    @FXML
    private TextField plz;

    @FXML
    private TextField ort;

    @FXML
    private TextField land;     //eventuell ComboBox verwenden?

    @FXML
    private TextField bundesland; //eventuell ComboBox verwenden?

    @FXML
    private TextField telefonnummer;

    @FXML
    private TextField email;

    @FXML
    private TextField bankverbindung;


    private Kunde kunde;
    private boolean wurdeGespeichert;
    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.kunde = new Kunde();
        id.setText(kunde.getId());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public boolean wurdeGespeichert() {
        return wurdeGespeichert;
    }

    @FXML
    private void speichern() {
        wurdeGespeichert = true;
        stage.close();
    }

    @FXML
    private void abbrechen() {
        wurdeGespeichert = false;
        stage.close();
    }
}
