package whs.mciv.aufgabe02;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.util.*;

public abstract class BaseController implements Initializable {
    public static char MESSAGE_FEHLER = 'f';
    public static char MESSAGE_WARNUNG = 'w';

    protected final String BEENDEN_HINWEIS = "Sie haben das Formular bearbeitet, möchten Sie wirklich Ihre Eingaben verwerfen?";
    protected final String BEENDEN_TITEL = "Beenden";

    @FXML
    protected Label fehler, warnung;

    @FXML
    protected Button speichern, abbrechen;

    protected Stage stage;
    protected boolean wurdeGespeichert = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    protected void initButtons() {
//        fehler.setMaxSize(300,14);
        abbrechen.setOnAction((ActionEvent event) -> {
            boolean formIsEmpty = isFormEmpty();
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
    protected void setMessage(char errorType, String message) {
        fehler.setText("");
        warnung.setText("");

        if (errorType == MESSAGE_WARNUNG) {
            warnung.setText(message);
        } else if (errorType == MESSAGE_FEHLER) {
            fehler.setText(message);
        }
    }

    protected boolean validateForm(LinkedHashMap<String, Control> form) {
        for (String key : form.keySet()) {
            Control item = form.get(key);
            if (item.contextMenuProperty().getBean().getClass().getName().equals("javafx.scene.control.TextField")) {
                TextField field = (TextField) item;
                if (field.getText().isEmpty()) {
                    Toolkit.getDefaultToolkit().beep();
                    field.requestFocus();
                    setMessage(BaseController.MESSAGE_FEHLER, "Bitte " + key + " eingeben");
                    return false;
                }
            } else if (item.contextMenuProperty().getBean().getClass().getName().equals("javafx.scene.control.ComboBox")) {
                ComboBox comboBox = (ComboBox) item;
                if (comboBox.getSelectionModel().isEmpty()) {
                 Toolkit.getDefaultToolkit().beep();
                 comboBox.requestFocus();
                 setMessage(BaseController.MESSAGE_FEHLER, "Bitte " + key + "auswählen");
                 return false;
                }
            }
        }
        return true;
    }

    protected boolean isFormEmpty(LinkedHashMap<String, Control> form) {
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

    public abstract boolean isFormEmpty();

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
