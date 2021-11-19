/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whs.mciv.aufgabe02;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 *
 * @author MaJa
 */
public class HauptfensterController implements Initializable {

    private BuchungsTool mainApp;
    
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
        boolean wurdeAngelegt = mainApp.zeigeReisezielAnlegenDialog();
        System.out.println("Angelegt: " + wurdeAngelegt);
    }

    @FXML
    public void zeigeKundeAnlegenDialog(ActionEvent actionEvent) {
        boolean wurdeAngelegt = mainApp.zeigeKundeAnlegenDialog();
    }

    @FXML
    public void zeigeBuchungAnlegenDialog(ActionEvent actionEvent) {
        boolean wurdeAngelegt = mainApp.zeigeBuchungAnlegenDialog();
    }
}
