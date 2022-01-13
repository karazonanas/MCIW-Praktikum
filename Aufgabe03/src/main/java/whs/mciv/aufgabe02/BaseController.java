package whs.mciv.aufgabe02;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public abstract class BaseController implements Initializable {

    @FXML
    protected Label fehler, warnung;

    @FXML
    protected Button speichern, abbrechen;

    protected Stage stage;
    protected boolean wurdeGespeichert = false;

    protected final String BEENDENHINWEIS = "Sie haben den Formular bearbeitet, möchten Sie wirklich Ihre Eingaben verwerfen";
    protected final String BEENDENTITEL = "Beenden";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    protected void initButtons() {
        abbrechen.setOnAction((ActionEvent event) -> {
            boolean formIsEmpty = isFormEmpty();
            if (formIsEmpty) {
                abbrechen();
            } else {
                Alert meldung = new Alert(Alert.AlertType.WARNING,BEENDENHINWEIS, ButtonType.YES, ButtonType.NO);
                meldung.setHeaderText("");
                meldung.setTitle(BEENDENTITEL);
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
