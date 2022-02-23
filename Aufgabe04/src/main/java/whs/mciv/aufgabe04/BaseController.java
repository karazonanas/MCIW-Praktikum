package whs.mciv.aufgabe04;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.util.Optional;

public abstract class BaseController implements Initializable {

    protected Stage stage;

    @FXML
    protected Button button1, button2;

    public void initButtons() {
        button1.setOnAction((ActionEvent event) -> {
            onFirstButton();
        });

        button2.setOnAction((ActionEvent event) -> {
            onSecondButton();
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

    public static Optional<ButtonType> beende(String hinweis, String titel) {
        Alert meldung = new Alert(Alert.AlertType.WARNING, hinweis, ButtonType.YES, ButtonType.NO);
        meldung.setHeaderText("");
        meldung.setTitle(titel);
        return meldung.showAndWait();
    }

    protected abstract void onFirstButton();

    protected abstract void onSecondButton();
    @FXML
    public void abbrechen() {
        stage.close();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }
}
