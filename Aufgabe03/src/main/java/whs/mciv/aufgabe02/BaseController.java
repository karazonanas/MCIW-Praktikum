package whs.mciv.aufgabe02;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.util.*;
import java.util.stream.Collectors;

public abstract class BaseController implements Initializable {

    protected final String BEENDEN_HINWEIS = "Sie haben das Formular bearbeitet, möchten Sie wirklich Ihre Eingaben verwerfen?";
    protected final String BEENDEN_TITEL = "Beenden";
    private final String[] HASDEFAULTVALUE = {"Land", "Anzahl der Reisenden", "Anzahl der Übernachtungen"};

    @FXML
    protected Button speichern, abbrechen;

    protected Stage stage;
    protected boolean wurdeGespeichert = false;

    protected void initButtons() {
        abbrechen.setOnAction((ActionEvent event) -> {
            boolean formIsEmpty = wasFormEdited();
            if (formIsEmpty) {
                abbrechen();
            } else {
                Alert meldung = new Alert(Alert.AlertType.WARNING, BEENDEN_HINWEIS, ButtonType.YES, ButtonType.NO);
                meldung.setHeaderText("");
                meldung.setTitle(BEENDEN_TITEL);
                Optional<ButtonType> beenden = meldung.showAndWait();
                if (beenden.isPresent()) {
                    if (Objects.equals(beenden.get().getButtonData().toString(), "YES")) {
                        abbrechen();
                    }
                }
            }
        });
        speichern.setOnAction((ActionEvent event) -> {
            boolean formIsValid = validateForm();
            if (formIsValid) {
                speichern();
            }
        });
    }

    /**
     * Gebe eine Warnung oder einen Fehler als Nachricht aus
     *
     * @param alertType Typ des Fehlers (MESSAGE_WARNUNG|MESSAGE_FEHLER)
     * @param message   Fehler-Meldung
     */
    public static void setMessage(Alert.AlertType alertType, String message) {
        String titel = "";

        if (alertType == Alert.AlertType.WARNING) {
            titel = "Warnung";
        } else if (alertType == Alert.AlertType.ERROR) {
            titel = "Fehler";
        }
        Alert meldung = new Alert(alertType, message, ButtonType.OK);
        meldung.setHeaderText("");
        meldung.setTitle(titel);
        meldung.showAndWait();
    }

    /**
     * Überprüfe, ob alle Pflichtfelder ausgefüllt sind
     *
     * @param form alle Pflichtfelder
     * @return wahr, wenn alle Felder ausgefüllt sind.
     */
    protected boolean validateForm(LinkedHashMap<String, Control> form) {
        LinkedHashMap<String, String> missingValues = new LinkedHashMap<>();
        boolean allValuesAreValid = true;

        resetStylingOfElements();

        for (String key : form.keySet()) {
            if (! Arrays.stream(HASDEFAULTVALUE).anyMatch(key::equals)) {
                Control item = form.get(key);
                if (item.contextMenuProperty().getBean().getClass().getName().equals("javafx.scene.control.TextField")) {
                    TextField field = (TextField) item;
                    if (field.getText().isEmpty()) {
                        missingValues.put(key, "- " + key + "\n");
                        field.setStyle("-fx-border-color: red;");

                        allValuesAreValid = false;
                    }
                } else if (item.contextMenuProperty().getBean().getClass().getName().equals("javafx.scene.control.ComboBox")) {
                    ComboBox comboBox = (ComboBox) item;
                    if (comboBox.getSelectionModel().isEmpty()) {
                        missingValues.put(key, "- " + key + "\n");
                        comboBox.setStyle("-fx-border-color: red;");

                        allValuesAreValid = false;
                    }
                } else if (item.contextMenuProperty().getBean().getClass().getName().equals("javafx.scene.control.DatePicker")) {
                    DatePicker datePicker = (DatePicker) item;
                    if (datePicker.getEditor().getText().isEmpty()) {
                        missingValues.put(key, "- " + key + "\n");
                        datePicker.setStyle("-fx-border-color: red;");

                        allValuesAreValid = false;
                    }
                }
            }
        }

        if (!allValuesAreValid) {
            Toolkit.getDefaultToolkit().beep();
            setMessage(Alert.AlertType.ERROR, "Es wurden nicht alle erforderlichen Felder ausgefüllt:\n" + missingValues.values().stream().collect(Collectors.joining()));
            Optional<String> firstInvalidElement = missingValues.keySet().stream().findFirst();

            firstInvalidElement.ifPresent(s -> form.get(s).requestFocus());

            return false;
        }

        return true;
    }

    /**
     * Überprüfe, ob Formular bearbeitet wurde
     *
     * @return wahr, wenn das Formular bearbeitet wurde
     */
    protected boolean wasFormEdited(LinkedHashMap<String, Control> form) {
        for (String key : form.keySet()) {
            if (!Arrays.stream(HASDEFAULTVALUE).anyMatch(key::equals)) {
                Control item = form.get(key);
                if (item.contextMenuProperty().getBean().getClass().getName().equals("javafx.scene.control.TextField")) {
                    TextField field = (TextField) item;
                    if (!field.getText().isEmpty()) {
                        return false;
                    }
                } else if (item.contextMenuProperty().getBean().getClass().getName().equals("javafx.scene.control.ComboBox")) {
                    ComboBox comboBox = (ComboBox) item;
                    if (!comboBox.getSelectionModel().isEmpty()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Controller-spezifisch: Überprüfe, ob Formular bearbeitet wurde
     *
     * @return wahr, wenn das Formular bearbeitet wurde
     */
    public abstract boolean wasFormEdited();

    /**
     * Setze Styling der Felder zurück
     */
    protected abstract void resetStylingOfElements();

    @FXML
    public abstract void abbrechen();

    @FXML
    public abstract void speichern();

    public abstract boolean validateForm();

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public boolean wurdeGespeichert() {
        return this.wurdeGespeichert;
    }

}
