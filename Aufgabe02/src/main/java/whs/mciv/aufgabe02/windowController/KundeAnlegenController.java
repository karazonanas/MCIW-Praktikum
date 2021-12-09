package whs.mciv.aufgabe02.windowController;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import whs.mciv.aufgabe02.BaseController;
import whs.mciv.aufgabe02.daten.kunde.Kunde;

import java.net.URL;
import java.util.ResourceBundle;

public class KundeAnlegenController extends BaseController {

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
    private ComboBox land;     //eventuell ComboBox verwenden?

    @FXML
    private ComboBox bundesland; //eventuell ComboBox verwenden?

    @FXML
    private TextField telefonnummer;

    @FXML
    private TextField email;

    @FXML
    private TextField kontoinhaber;

    @FXML
    private CheckBox sameAsCustomer;

    @FXML
    private TextField iban;

    @FXML
    private TextField bic;

    @FXML
    private TextField bank;

    private Kunde kunde;

    private final String DE_LAENDER[] = {"Baden-Württemberg", "Bayern", "Berlin", "Brandenburg", "Bremen", "Hamburg", "Hessen", "Mecklenburg-Vorpommern", "Niedersachsen", "Nordrhein-Westfalen", "Rheinland-Pfalz", "Saarland", "Sachsen", "Sachsen-Anhalt", "Schleswig-Holstein" ,"Thüringen"};
    private final String OS_LAENDER[] = {"Burgenland", "Kärnten", "Niederösterreich", "Oberösterreich", "Salzburg", "Steiermark", "Tirol", "Vorarlberg", "Wien"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.kunde = new Kunde();
        id.setText(kunde.getId());
        conditionalComboBox();
        conditionalCheckbox();

        updateKontoinhaber();
    }

    private void conditionalCheckbox() {
        sameAsCustomer.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (t1) {
                    kontoinhaber.setText(vorname.getText() + " " + nachname.getText());
                }
                kontoinhaber.setEditable(!t1);
                kontoinhaber.setDisable(t1);
            }
        });
    }

    private void updateKontoinhaber() {
        ChangeListener<Boolean> namensAenderungChangeListener = new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldProperty, Boolean newProperty) {
                // Wenn kein Fokus auf Feld
                if (!newProperty && sameAsCustomer.isSelected()) {
                    kontoinhaber.setText(vorname.getText() + " " + nachname.getText());
                }
            }
        };

        this.vorname.focusedProperty().addListener(namensAenderungChangeListener);
        this.nachname.focusedProperty().addListener(namensAenderungChangeListener);
    }

    private void conditionalComboBox() {
        bundesland.getItems().setAll(DE_LAENDER);
        land.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                if (t1 != null && t1 instanceof String) {
                    String ausgewaehlteLand = (String) t1;
                    switch (ausgewaehlteLand) {
                        case "Deutschland": bundesland.getItems().setAll(DE_LAENDER);break;
                        case "Österreich": bundesland.getItems().setAll(OS_LAENDER);break;
                    }

                }
            }
        });
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
