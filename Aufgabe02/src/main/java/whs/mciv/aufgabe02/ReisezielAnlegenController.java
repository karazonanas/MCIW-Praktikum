/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whs.mciv.aufgabe02;

import whs.mciv.aufgabe02.daten.reiseziele.Reiseziel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ReisezielAnlegenController implements Initializable {

    @FXML
    private TextField id;
    
    @FXML
    private TextField name;
    
    @FXML
    private TextArea beschreibung;
    
    @FXML
    private TextField land;
    
    @FXML
    private TextField preisHalbpension;
    
    @FXML
    private TextField preisVollpension; 

    private Reiseziel reiseziel;
    private boolean wurdeGespeichert = false;
    private Stage stage;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Erzeige neues Reiseziel-Objekt um die Daten des Formulars später
        // speichern zu können. Der Reiseziel-Konstruktor erzeugt auch die ID.
        reiseziel = new Reiseziel();
        
        id.setText(reiseziel.getId());
    }
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    public boolean wurdeGespeichert() {
        return wurdeGespeichert;
    }
    
    @FXML
    private void speichern() {
        // Das speichern ist für diese Praktikumsaufgabe noch nicht notwendig.
        wurdeGespeichert = true;
        stage.close();
    }
    
    @FXML
    private void abbrechen() {
        wurdeGespeichert = false;
        stage.close();
    }
}
