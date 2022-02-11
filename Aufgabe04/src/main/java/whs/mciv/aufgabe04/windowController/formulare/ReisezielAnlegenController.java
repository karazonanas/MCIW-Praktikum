/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whs.mciv.aufgabe04.windowController.formulare;

import whs.mciv.aufgabe04.daten.N;
import whs.mciv.aufgabe04.daten.reiseziele.Reiseziel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ReisezielAnlegenController extends FormularController {

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

    /**
     * Controller-spezifisch: Überprüfe, ob Formular bearbeitet wurde
     *
     * @return wahr, wenn das Formular bearbeitet wurde
     */
    @Override
    public boolean wasFormEdited() {
        /*
        TODO
        */
        return false;
    }

    /**
     * Setze Styling der Felder zurück
     */
    protected void resetStylingOfElements() {
        name.setStyle("");
        beschreibung.setStyle("");
        land.setStyle("");
        preisHalbpension.setStyle("");
        preisVollpension.setStyle("");
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

    @Override
    public void fillForm(N form) {

    }
}
