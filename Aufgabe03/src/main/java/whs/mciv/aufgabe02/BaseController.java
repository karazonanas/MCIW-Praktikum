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

public abstract class BaseController implements Initializable {

    protected final String BEENDEN_HINWEIS = "Sie haben das Formular bearbeitet, möchten Sie wirklich Ihre Eingaben verwerfen?";
    protected final String BEENDEN_TITEL = "Beenden";

    @FXML
    protected Button speichern, abbrechen;

    protected Stage stage;
    protected boolean wurdeGespeichert = false;

    protected void initButtons() {
//        fehler.setMaxSize(300,14);
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
     * @param errorType Typ des Fehlers (MESSAGE_WARNUNG|MESSAGE_FEHLER)
     * @param message Fehler-Meldung
     */
    protected void setMessage(Alert.AlertType errorType, String message) {
        Alert.AlertType alertType = Alert.AlertType.NONE;
        String titel = "";

        if (errorType == Alert.AlertType.WARNING) {
            titel = "Warnung";
        } else if (errorType == Alert.AlertType.ERROR) {
            titel = "Fehler";
        }
        Alert meldung = new Alert(alertType, message, ButtonType.OK);
        meldung.setHeaderText("");
        meldung.setTitle(titel);
        meldung.show();
    }

    /**
     * Überprüfe, ob alle Pflichtfelder ausgefüllt sind
     * @param form alle Pflichtfelder
     * @return wahr, wenn alle Felder ausgefüllt sind.
     */
    protected boolean validateForm(LinkedHashMap<String, Control> form) {
        for (String key : form.keySet()) {
            Control item = form.get(key);
            if (item.contextMenuProperty().getBean().getClass().getName().equals("javafx.scene.control.TextField")) {
                TextField field = (TextField) item;
                if (field.getText().isEmpty()) {
                    Toolkit.getDefaultToolkit().beep();
                    field.requestFocus();
                    setMessage(Alert.AlertType.ERROR, "Bitte " + key + " eingeben");
                    return false;
                }
            } else if (item.contextMenuProperty().getBean().getClass().getName().equals("javafx.scene.control.ComboBox")) {
                ComboBox comboBox = (ComboBox) item;
                if (comboBox.getSelectionModel().isEmpty()) {
                 Toolkit.getDefaultToolkit().beep();
                 comboBox.requestFocus();
                 setMessage(Alert.AlertType.ERROR, "Bitte " + key + "auswählen");
                 return false;
                }
            }
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
            Control item = form.get(key);
            if (item.contextMenuProperty().getBean().getClass().getName().equals("javafx.scene.control.TextField")) {
                TextField field = (TextField) item;
                if (! field.getText().isEmpty()) {
                    return false;
                }
            } else if (item.contextMenuProperty().getBean().getClass().getName().equals("javafx.scene.control.ComboBox")) {
                ComboBox comboBox = (ComboBox) item;
                if (! comboBox.getSelectionModel().isEmpty()) {
                    return false;
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
