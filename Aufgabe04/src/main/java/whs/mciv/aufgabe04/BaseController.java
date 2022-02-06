package whs.mciv.aufgabe04;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public abstract class BaseController implements Initializable {

    protected Stage stage;

    @FXML
    protected Button speichern, abbrechen;

    public void initButtons() {
        abbrechen.setOnAction((ActionEvent event) -> {
            onActionAbbrechen();
        });
        speichern.setOnAction((ActionEvent event) -> {
            onActionSpeichern();
        });
    }

    /**
     * Wird aufgerufen, wenn das Formular nicht abgebrochen/geschlossen werden soll
     */
    protected void callIfAbbrechenAborted() {
        // ToDo: Whats should be done?
    }

    /**
     * Gebe eine Warnung oder einen Fehler als Nachricht aus
     *
     * @param alertType Typ des Fehlers (MESSAGE_WARNUNG|MESSAGE_FEHLER)
     * @param message   Fehler-Meldung
     */
    public static void setMessage(Alert.AlertType alertType, String message) {
        String titel = "";

        switch (alertType) {
            case ERROR -> titel = "Fehler";
            case WARNING -> titel = "Warnung";
            case INFORMATION -> titel = "Info";
            case CONFIRMATION -> titel = "Bestätigung";
        }

        Alert meldung = new Alert(alertType, message, ButtonType.OK);
        meldung.setHeaderText("");
        meldung.setTitle(titel);
        meldung.showAndWait();
    }

    protected abstract void onActionSpeichern();

    protected abstract void onActionAbbrechen();

    protected abstract boolean showController();

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
