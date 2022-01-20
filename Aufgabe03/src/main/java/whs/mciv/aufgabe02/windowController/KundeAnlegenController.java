package whs.mciv.aufgabe02.windowController;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import whs.mciv.aufgabe02.BaseController;
import whs.mciv.aufgabe02.daten.kunde.Kunde;
import whs.mciv.aufgabe02.filter.FilterEmail;
import whs.mciv.aufgabe02.filter.FilterIban;
import whs.mciv.aufgabe02.filter.FilterPhoneNumber;
import whs.mciv.aufgabe02.filter.FilterPlz;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;

public class KundeAnlegenController extends BaseController {

    @FXML
    private TextField id;

    @FXML
    private ComboBox<String> anrede;

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
    private ComboBox<String> land;

    @FXML
    private ComboBox<String> bundesland;

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

    private final String[] DE_LAENDER = {"Baden-Württemberg", "Bayern", "Berlin", "Brandenburg", "Bremen", "Hamburg", "Hessen", "Mecklenburg-Vorpommern", "Niedersachsen", "Nordrhein-Westfalen", "Rheinland-Pfalz", "Saarland", "Sachsen", "Sachsen-Anhalt", "Schleswig-Holstein" ,"Thüringen"};
    private final String[] OS_LAENDER = {"Burgenland", "Kärnten", "Niederösterreich", "Oberösterreich", "Salzburg", "Steiermark", "Tirol", "Vorarlberg", "Wien"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.kunde = new Kunde();
        id.setText(kunde.getId());

        landConditionalComboBox();
        sameAsCustomerConditionalCheckbox();

        plz.setTextFormatter(new TextFormatter<>(new FilterPlz('d')));
        telefonnummer.setTextFormatter(new TextFormatter<>(new FilterPhoneNumber()));
        iban.setTextFormatter(new TextFormatter<>(new FilterIban()));
        email.setTextFormatter(new TextFormatter<>(new FilterEmail()));

        abbrechen.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (t1) {
                    email.setTextFormatter(null);
                } else {
                }
            }
        });

        initButtons();
        updateKontoinhaber();
    }

    /**
     * Listener für "gleich wie Kunde" Checkbox
     */
    private void sameAsCustomerConditionalCheckbox() {
        sameAsCustomer.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (t1) {
                    kontoinhaber.setText("");
                } else {
                    kontoinhaber.setText(vorname.getText() + " " + nachname.getText());
                }
                kontoinhaber.setEditable(t1);
                kontoinhaber.setDisable(!t1);
            }
        });
    }

    /**
     * Listener: Aktualisiere den Kontoinhaber
     */
    private void updateKontoinhaber() {
        ChangeListener<Boolean> namensAenderungChangeListener = new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldProperty, Boolean newProperty) {
                // Wenn kein Fokus auf Feld
                if (!newProperty && ! sameAsCustomer.isSelected()) {
                    kontoinhaber.setText(vorname.getText() + " " + nachname.getText());
                }
            }
        };

        this.vorname.focusedProperty().addListener(namensAenderungChangeListener);
        this.nachname.focusedProperty().addListener(namensAenderungChangeListener);
    }

    /**
     * Aktualisiere Bundesländer / Land
     */
    private void landConditionalComboBox() {
        bundesland.getItems().setAll(DE_LAENDER);
        plz.setStyle("");

        land.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                if (t1 != null && t1 instanceof String) {
                    String ausgewaehlteLand = (String) t1;
                    switch (ausgewaehlteLand) {
                        case "Deutschland": bundesland.getItems().setAll(DE_LAENDER);
                            plz.setTextFormatter(new TextFormatter<>(new FilterPlz('d')));

                            if (!plz.getText().matches(FilterPlz.DEFAULT_REGEX)) {
                                Toolkit.getDefaultToolkit().beep();
                                setMessage(Alert.AlertType.WARNING, "Sie haben das Land geändert, jedoch stimmt die PLZ nicht überein. In Deutschland besteht eine PLZ aus 5 Zahlen.");
                                plz.setStyle("-fx-border-color: orange;");
                            }

                            break;
                        case "Österreich": bundesland.getItems().setAll(OS_LAENDER);
                            plz.setTextFormatter(new TextFormatter<>(new FilterPlz('o')));

                            if (!plz.getText().matches(FilterPlz.O_REGEX)) {
                                Toolkit.getDefaultToolkit().beep();
                                setMessage(Alert.AlertType.WARNING, "Sie haben das Land geändert, jedoch stimmt die PLZ nicht überein. In Österreich besteht eine PLZ aus 4 Zahlen.");
                                plz.setStyle("-fx-border-color: orange;");
                            }

                            break;
                    }

                    plz.requestFocus();
                }
            }
        });
    }

    /**
     * Wird aufgerufen, wenn das Formular nicht abgebrochen/geschlossen werden soll
     */
    @Override
    protected void callIfAbbrechenAborted() {
        email.setTextFormatter(new TextFormatter<>(new FilterEmail()));
    }

    /**
     * Setze Styling der Felder zurück
     */
    protected void resetStylingOfElements() {
        anrede.setStyle("");
        vorname.setStyle("");
        nachname.setStyle("");
        telefonnummer.setStyle("");
        adresse.setStyle("");
        plz.setStyle("");
        ort.setStyle("");
        land.setStyle("");
        bundesland.setStyle("");
        iban.setStyle("");
        bic.setStyle("");
        bank.setStyle("");
    }

    /**
     * Validiere Formular
     *
     * @return wahr, wenn alles richtig ist
     */
    public boolean validateForm() {
        LinkedHashMap<String, Control> form = createForm();
        boolean formValid = validateForm(form);

        if (formValid) {
            if (land.getSelectionModel().selectedItemProperty().get().equals("Deutschland") && plz.getText().length() != 5) {
                Toolkit.getDefaultToolkit().beep();
                plz.requestFocus();
                setMessage(Alert.AlertType.ERROR, "Die PLZ einer deutschen Adresse besteht aus fünf Zahlen.");
                plz.setStyle("-fx-border-color: red;");

                return false;

            }

            if (land.getSelectionModel().selectedItemProperty().get().equals("Österreich") && plz.getText().length() != 4) {
                Toolkit.getDefaultToolkit().beep();
                plz.requestFocus();
                setMessage(Alert.AlertType.ERROR, "Die PLZ einer österreichischen Adresse besteht aus vier Zahlen.");
                plz.setStyle("-fx-border-color: red;");

                return false;
            }

            if (!iban.getText().isEmpty() || !bic.getText().isEmpty() || !bank.getText().isEmpty() || sameAsCustomer.isSelected()) {
                if (iban.getText().isEmpty() || bic.getText().isEmpty() || bank.getText().isEmpty() || kontoinhaber.getText().isEmpty()) {
                    ArrayList<String> errorMessages = new ArrayList<>();

                    if (kontoinhaber.getText().isEmpty() && sameAsCustomer.isSelected()) {
                        kontoinhaber.setStyle("-fx-border-color: red;");
                        errorMessages.add("- Es wurde kein Kontoinhaber angegeben\n");
                    }

                    if (iban.getText().isEmpty()) {
                        if (errorMessages.isEmpty()) {
                            iban.requestFocus();
                        }

                        iban.setStyle("-fx-border-color: red;");
                        errorMessages.add( "- Es wurde keine IBAN angegeben\n");
                    }
                    else if (!iban.getText().matches(FilterIban.IBAN_REGEX_FINAL)) {
                        if (errorMessages.isEmpty()) {
                            iban.requestFocus();
                        }

                        iban.requestFocus();
                        errorMessages.add("- Die IBAN ist nicht gültig\n");
                        iban.setStyle("-fx-border-color: red;");
                    }

                    if (bic.getText().isEmpty()) {
                        if (errorMessages.isEmpty()) {
                            bic.requestFocus();
                        }

                        bic.setStyle("-fx-border-color: red;");
                        errorMessages.add("- Der BIC wurde nicht angegeben\n");
                    }

                    if (bank.getText().isEmpty()) {
                        if (errorMessages.isEmpty()) {
                            bank.requestFocus();
                        }

                        bank.setStyle("-fx-border-color: red;");
                        errorMessages.add("- Der Name der Bank fehlt\n");
                    }

                    Toolkit.getDefaultToolkit().beep();
                    setMessage(Alert.AlertType.ERROR, "Sie haben unvollständige oder fehlerhafte Bankdaten angegeben:\n" + String.join("", errorMessages));

                    return false;
                }

                // Der BIC besteht aus mindestens 8 Zeichen
                if (bic.getText().length() < 8) {
                    Toolkit.getDefaultToolkit().beep();
                    bic.requestFocus();
                    setMessage(Alert.AlertType.ERROR, "Der BIC ist nicht gültig!");
                    bic.setStyle("-fx-border-color: red;");

                    return false;
                }
            }
        }

        return formValid;
    }

    /**
     * Controller-spezifisch: Überprüfe, ob Formular bearbeitet wurde
     *
     * @return wahr, wenn das Formular bearbeitet wurde
     */
    public boolean wasFormEdited() {
        LinkedHashMap<String, Control> form = createForm();

        form.put("Anrede", anrede);
        form.put("E-Mail", email);
        form.put("IBAN", iban);
        form.put("BIC", bic);
        form.put("Bank", bank);

        return wasFormEdited(form);
    }

    /**
     * Erstelle Formular-Felder
     *
     * @return LinkedHashMap mit Feldern
     */
    private LinkedHashMap<String, Control> createForm() {
        LinkedHashMap<String, Control> form = new LinkedHashMap<>();
        form.put("Vorname", vorname);
        form.put("Nachname", nachname);
        form.put("Telefonnummer", telefonnummer);
        form.put("Straße und Hausnummer", adresse);
        form.put("PLZ", plz);
        form.put("Ort", ort);
        form.put("Bundesland", bundesland);
        form.put("Land", land);

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
