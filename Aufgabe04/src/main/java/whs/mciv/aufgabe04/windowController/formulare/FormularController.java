package whs.mciv.aufgabe04.windowController.formulare;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import whs.mciv.aufgabe04.BaseController;
import whs.mciv.aufgabe04.daten.Datensatz;

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
                    if (field.getText() != null && !field.getText().isEmpty()) {
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
            Optional<ButtonType> beenden = BaseController.beende(BEENDEN_HINWEIS, BEENDEN_TITEL);
            if (beenden.isPresent()) {
                if (Objects.equals(beenden.get().getButtonData().toString(), "YES")) {
                    wurdeGespeichert = false;
                    abbrechen();
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

    public abstract void runOnClose();

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

                        field.textProperty().addListener(new ChangeListener<String>() {
                            @Override
                            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                                if (newValue != null && !newValue.isEmpty()) {
                                    field.setStyle("");
                                }
                            }
                        });

                        allValuesAreValid = false;
                    }
                } else if (item.contextMenuProperty().getBean().getClass().getName().equals("javafx.scene.control.ComboBox")) {
                    ComboBox comboBox = (ComboBox) item;
                    if (comboBox.getSelectionModel().isEmpty()) {
                        missingValues.add(key);
                        comboBox.setStyle("-fx-border-color: red;");

                        comboBox.valueProperty().addListener(new ChangeListener<Object>() {
                            @Override
                            public void changed(ObservableValue<?> observableValue, Object o, Object t1) {
                                if (t1 != null) {
                                    comboBox.setStyle("");
                                }
                            }
                        });

                        allValuesAreValid = false;
                    }
                } else if (item.contextMenuProperty().getBean().getClass().getName().equals("javafx.scene.control.DatePicker")) {
                    DatePicker datePicker = (DatePicker) item;
                    if (datePicker.getEditor().getText() == null || datePicker.getEditor().getText().isEmpty()) {
                        missingValues.add(key);
                        datePicker.setStyle("-fx-border-color: red;");

                        datePicker.getEditor().textProperty().addListener(new ChangeListener<String>() {
                            @Override
                            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                                if (newValue != null && !newValue.isEmpty()) {
                                    datePicker.setStyle("");
                                }
                            }
                        });

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

    public abstract void fillForm(Datensatz form);

    public boolean getWurdeGespeichert() {
        return wurdeGespeichert;
    }

    public void setWurdeGespeichert(boolean wurdeGespeichert) {
        this.wurdeGespeichert = wurdeGespeichert;
    }
}
