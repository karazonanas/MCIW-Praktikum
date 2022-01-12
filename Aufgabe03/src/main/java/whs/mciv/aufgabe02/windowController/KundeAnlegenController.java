package whs.mciv.aufgabe02.windowController;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import whs.mciv.aufgabe02.BaseController;
import whs.mciv.aufgabe02.daten.kunde.Kunde;
import whs.mciv.aufgabe02.filter.FilterEmail;
import whs.mciv.aufgabe02.filter.FilterIban;
import whs.mciv.aufgabe02.filter.FilterPhoneNumber;
import whs.mciv.aufgabe02.filter.FilterPlz;

import java.awt.*;
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

    @FXML
    private Button speichern, abbrechen;

    @FXML
    private Label fehler, plzFehler;

    private Kunde kunde;

    private final String DE_LAENDER[] = {"Baden-Württemberg", "Bayern", "Berlin", "Brandenburg", "Bremen", "Hamburg", "Hessen", "Mecklenburg-Vorpommern", "Niedersachsen", "Nordrhein-Westfalen", "Rheinland-Pfalz", "Saarland", "Sachsen", "Sachsen-Anhalt", "Schleswig-Holstein" ,"Thüringen"};
    private final String OS_LAENDER[] = {"Burgenland", "Kärnten", "Niederösterreich", "Oberösterreich", "Salzburg", "Steiermark", "Tirol", "Vorarlberg", "Wien"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.kunde = new Kunde();
        id.setText(kunde.getId());
        conditionalComboBox();
        conditionalCheckbox();
        plz.setTextFormatter(new TextFormatter<>(new FilterPlz('d')));
        email.setTextFormatter(new TextFormatter<>(new FilterEmail()));
        telefonnummer.setTextFormatter(new TextFormatter<>(new FilterPhoneNumber()));
        iban.setTextFormatter(new TextFormatter<>(new FilterIban()));
        speichern.setOnAction((ActionEvent event) -> {
            boolean formIsValid = validateForm();
            if (formIsValid) {
                speichern();
            }
        });
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
                        case "Deutschland": bundesland.getItems().setAll(DE_LAENDER);
                            plz.setTextFormatter(new TextFormatter<>(new FilterPlz('d')));break;
                        case "Österreich": bundesland.getItems().setAll(OS_LAENDER);
                            plz.setTextFormatter(new TextFormatter<>(new FilterPlz('o')));break;
                    }

                }
            }
        });
    }

    private boolean validateForm() {
        boolean formValid = true;
        if (vorname.getText().isEmpty()) {
            Toolkit.getDefaultToolkit().beep();
            vorname.requestFocus();
            fehler.setText("Bitte Vorname eingeben");
            return ! formValid;
        }
        if (nachname.getText().isEmpty()) {
            Toolkit.getDefaultToolkit().beep();
            nachname.requestFocus();
            fehler.setText("Bitte Nachname eingeben");
            return ! formValid;
        }
        if (telefonnummer.getText().isEmpty()) {
            Toolkit.getDefaultToolkit().beep();
            telefonnummer.requestFocus();
            fehler.setText("Bitte Telefonnummer eingeben");
            return ! formValid;
        }
        if (adresse.getText().isEmpty()) {
            Toolkit.getDefaultToolkit().beep();
            adresse.requestFocus();
            fehler.setText("Bitte Straße und Hausnummer eingeben");
            return ! formValid;
        }
        if (plz.getText().isEmpty()) {
            Toolkit.getDefaultToolkit().beep();
            plz.requestFocus();
            fehler.setText("Bitte Postleitzahl eingeben");
            return ! formValid;
        }
        if (ort.getText().isEmpty()) {
            Toolkit.getDefaultToolkit().beep();
            ort.requestFocus();
            fehler.setText("Bitte Ort eingeben");
            return ! formValid;
        }
        if (land.getSelectionModel().isEmpty()) {
            Toolkit.getDefaultToolkit().beep();
            land.requestFocus();
            fehler.setText("Bitte Land auswählen");
            return ! formValid;
        }
        if (bundesland.getSelectionModel().isEmpty()) {
            Toolkit.getDefaultToolkit().beep();
            bundesland.requestFocus();
            fehler.setText("Bitte Bundesland auswählen");
            return ! formValid;
        }
        if (kontoinhaber.getText().isEmpty()) {
            Toolkit.getDefaultToolkit().beep();
            kontoinhaber.requestFocus();
            fehler.setText("Bitte Kontoinhaber eingeben");
            return ! formValid;
        }
        if (iban.getText().isEmpty()) {
            Toolkit.getDefaultToolkit().beep();
            iban.requestFocus();
            fehler.setText("Bitte IBAN eingeben");
            return ! formValid;
        }
        if (bic.getText().isEmpty()) {
            Toolkit.getDefaultToolkit().beep();
            bic.requestFocus();
            fehler.setText("Bitte BIC eingeben");
            return ! formValid;
        }
        if (bank.getText().isEmpty()) {
            Toolkit.getDefaultToolkit().beep();
            bank.requestFocus();
            fehler.setText("Bitte Bank eingeben");
            return ! formValid;
        }
        if (plz.getText().length() == 5 && land.getSelectionModel().selectedItemProperty().get() != "Deutschland" ||
        plz.getText().length() == 4 && land.getSelectionModel().selectedItemProperty().get() != "Österreich") {
            Toolkit.getDefaultToolkit().beep();
            plz.requestFocus();
            plzFehler.setText("PLZ und Land stimmen nicht überein");
            fehler.setText("Bitte überprüfen Sie Ihre Eingaben");
            return ! formValid;
        }
        return formValid;
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
