/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whs.mciv.aufgabe02.windowController;

import whs.mciv.aufgabe02.BaseController;
import whs.mciv.aufgabe02.daten.reiseziele.Reiseziel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ReisezielAnlegenController extends BaseController {

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Erzeige neues Reiseziel-Objekt um die Daten des Formulars später
        // speichern zu können. Der Reiseziel-Konstruktor erzeugt auch die ID.
        reiseziel = new Reiseziel();
        
        id.setText(reiseziel.getId());
    }

    @Override
    public boolean isFormEmpty() {
        /*
        TODO
        */
        return false;
    }

    @FXML
    public void speichern() {
        // Das Speichern ist für diese Praktikumsaufgabe noch nicht notwendig.
        wurdeGespeichert = true;
        stage.close();
    }

    @Override
    public boolean validateForm() {
        /*
        TODO
        */
        return false;
    }

    @FXML
    public void abbrechen() {
        wurdeGespeichert = false;
        stage.close();
    }
}
