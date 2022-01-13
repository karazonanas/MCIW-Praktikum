package whs.mciv.aufgabe02.windowController;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
import java.util.LinkedHashMap;
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
    private ComboBox land;

    @FXML
    private ComboBox bundesland;

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
    private Label plzFehler;

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
        initButtons();
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

    public boolean validateForm() {

        LinkedHashMap<String, Control> form = createForm();
        boolean formValid = validateForm(form);

//        /*
//         * @ToDo: Frage nach, ob IBAN, BIC und Bank Pflichtfelder sind
//         */
        if (formValid) {
            if (! iban.getText().matches(FilterIban.ibanRegexFinal)) {
                Toolkit.getDefaultToolkit().beep();
                iban.requestFocus();
                setMessage(BaseController.MESSAGE_FEHLER,"Die IBAN ist nicht gültig!");
                return ! formValid;
            }
            if (plz.getText().length() == 5 && ! land.getSelectionModel().selectedItemProperty().get().equals("Deutschland") ||
                    plz.getText().length() == 4 && ! land.getSelectionModel().selectedItemProperty().get().equals("Österreich")) {
                Toolkit.getDefaultToolkit().beep();
                plz.requestFocus();
                plzFehler.setText("PLZ und Land stimmen nicht überein");
                setMessage(BaseController.MESSAGE_FEHLER,"Bitte überprüfen Sie Ihre Eingaben");
                return ! formValid;
            }
        }
        return formValid;
    }

    public boolean isFormEmpty() {
        LinkedHashMap<String, Control> form = createForm();
        return isFormEmpty(form) &&
                land.getSelectionModel().selectedItemProperty().get().equals("Deutschland");
    }

    private LinkedHashMap<String, Control> createForm() {
        LinkedHashMap<String, Control> form = new LinkedHashMap<>();
        form.put("Vorname", vorname);
        form.put("Nachname", nachname);
        form.put("Telefonnummer", telefonnummer);
        form.put("Straße und Hausnummer", adresse);
        form.put("PLZ", plz);
        form.put("Ort", ort);
        form.put("Bundesland", bundesland);
        form.put("Kontoinhaber", kontoinhaber);
        form.put("IBAN", iban);
        form.put("BIC", bic);
        form.put("Bank", bank);

        return form;
    }

    @FXML
    public void speichern() {
        wurdeGespeichert = true;
        stage.close();
    }

    @FXML
    public void abbrechen() {
        wurdeGespeichert = false;
        stage.close();
    }
}
