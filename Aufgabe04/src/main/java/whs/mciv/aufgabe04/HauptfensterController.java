/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whs.mciv.aufgabe04;

import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author MaJa
 */
public class HauptfensterController implements Initializable {

    private BuchungsTool mainApp;
    private final String BEENDEN_HINWEIS = "Möchten Sie wirklich die Anwendung und alle dazugehörigen Fenster schließen?";
    private final String BEENDEN_TITEL = "Anwendung beenden";
    private final String DEV_INFO = "Buchungssystem WHS\nVersion 1.0.3 \nEntwickelt von Anas Karazon und Enes Erk";

    public void setMainApp(BuchungsTool mainApp){
        this.mainApp = mainApp;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    @FXML
    private void zeigeReisezielAnlegenDialog(ActionEvent event) {
        System.out.println("Reiseziel Anlegen");
        BaseController controller = mainApp.zeigeReisezielAnlegenDialog();
//        System.out.println("Angelegt: " + wurdeAngelegt);
    }

    @FXML
    public void zeigeKundeAnlegenDialog(ActionEvent actionEvent) {
        BaseController controller = mainApp.zeigeKundeAnlegenDialog();
    }

    @FXML
    public void zeigeBuchungAnlegenDialog(ActionEvent actionEvent) {
        BaseController controller = mainApp.zeigeBuchungAnlegenDialog();
    }

    @FXML
    public void zeigeKundeUebersichtDialog(ActionEvent actionEvent) {
        BaseController controller = mainApp.zeigeKundeUebersichtDialog();
    }

    @FXML
    public void zeigeBuchungUebersichtDialog(ActionEvent actionEvent) {
        BaseController controller = mainApp.zeigeBuchungUebersichtDialog();
    }

    @FXML
    private void beenden() {
        Alert meldung = new Alert(Alert.AlertType.WARNING, BEENDEN_HINWEIS, ButtonType.YES, ButtonType.NO);
        meldung.setHeaderText("");
        meldung.setTitle(BEENDEN_TITEL);
        Optional<ButtonType> beenden = meldung.showAndWait();
        if (beenden.isPresent()) {
            if (Objects.equals(beenden.get().getButtonData().toString(), "YES")) {
                mainApp.closeStage();
            }
        }
    }

    @FXML
    private void showDevInfo() {
        BaseController.setMessage(Alert.AlertType.INFORMATION, DEV_INFO);
    }
}
