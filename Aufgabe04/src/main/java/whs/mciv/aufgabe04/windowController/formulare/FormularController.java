package whs.mciv.aufgabe04.windowController.formulare;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import whs.mciv.aufgabe04.BaseController;
import whs.mciv.aufgabe04.daten.N;

import java.awt.*;
import java.util.*;

public abstract class FormularController extends BaseController {

    protected final String BEENDEN_HINWEIS = "Sie haben das Formular bearbeitet, möchten Sie wirklich Ihre Eingaben verwerfen?";
    protected final String BEENDEN_TITEL = "Abbrechen";
    private final String[] HASDEFAULTVALUE = {"Land", "Anzahl der Reisenden", "Anzahl der Übernachtungen"};
    protected boolean wurdeGespeichert = false;

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

    @Override
    protected void onFirstButton() {
        boolean formIsValid = validateForm();
        if (formIsValid) {
            speichern();
        }
    }

    @Override
    protected void onSecondButton(){
        boolean formIsEmpty = wasFormEdited();

        if (formIsEmpty) {
            wurdeGespeichert = false;
            abbrechen();
        } else {
            Alert meldung = new Alert(Alert.AlertType.WARNING, BEENDEN_HINWEIS, ButtonType.YES, ButtonType.NO);
            meldung.setHeaderText("");
            meldung.setTitle(BEENDEN_TITEL);
            Optional<ButtonType> beenden = meldung.showAndWait();
            if (beenden.isPresent()) {
                if (Objects.equals(beenden.get().getButtonData().toString(), "YES")) {
                    wurdeGespeichert = false;
                    abbrechen();
                } else {
                    callIfAbbrechenAborted();
                }
            }
        }
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
    public abstract void speichern();

    public abstract boolean validateForm();
    /**
     * Überprüfe, ob alle Pflichtfelder ausgefüllt sind
     *
     * @param form alle Pflichtfelder
     * @return wahr, wenn alle Felder ausgefüllt sind.
     */
    protected boolean validateForm(LinkedHashMap<String, Control> form) {
        LinkedHashSet<String> missingValues = new LinkedHashSet<String>();
        boolean allValuesAreValid = true;

        resetStylingOfElements();

        for (String key : form.keySet()) {
            if (! Arrays.stream(HASDEFAULTVALUE).anyMatch(key::equals)) {
                Control item = form.get(key);
                if (item.contextMenuProperty().getBean().getClass().getName().equals("javafx.scene.control.TextField")) {
                    TextField field = (TextField) item;
                    if (field.getText() == null || field.getText().isEmpty()) {
                        missingValues.add(key);
                        field.setStyle("-fx-border-color: red;");

                        allValuesAreValid = false;
                    }
                } else if (item.contextMenuProperty().getBean().getClass().getName().equals("javafx.scene.control.ComboBox")) {
                    ComboBox comboBox = (ComboBox) item;
                    if (comboBox.getSelectionModel().isEmpty()) {
                        missingValues.add(key);
                        comboBox.setStyle("-fx-border-color: red;");

                        allValuesAreValid = false;
                    }
                } else if (item.contextMenuProperty().getBean().getClass().getName().equals("javafx.scene.control.DatePicker")) {
                    DatePicker datePicker = (DatePicker) item;
                    if (datePicker.getEditor().getText().isEmpty()) {
                        missingValues.add(key);
                        datePicker.setStyle("-fx-border-color: red;");

                        allValuesAreValid = false;
                    }
                }
            }
        }

        if (!allValuesAreValid) {
            Toolkit.getDefaultToolkit().beep();
            setMessage(Alert.AlertType.WARNING, "Es wurden nicht alle erforderlichen Felder ausgefüllt: \n - " + String.join("\n - ", missingValues));
            Optional<String> firstInvalidElement = missingValues.stream().findFirst();

            firstInvalidElement.ifPresent(s -> form.get(s).requestFocus());

            return false;
        }
        return true;
    }

    public abstract void fillForm(N form);

    public boolean getWurdeGespeichert() {
        return wurdeGespeichert;
    }

    public void setWurdeGespeichert(boolean wurdeGespeichert) {
        this.wurdeGespeichert = wurdeGespeichert;
    }
}
