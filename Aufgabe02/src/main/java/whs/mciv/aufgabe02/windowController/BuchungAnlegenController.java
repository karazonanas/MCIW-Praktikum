package whs.mciv.aufgabe02.windowController;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;
import whs.mciv.aufgabe02.BaseController;
import whs.mciv.aufgabe02.daten.buchung.Buchung;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import whs.mciv.aufgabe02.daten.kunde.Kunde;
import whs.mciv.aufgabe02.daten.kunde.KundenDaten;
import whs.mciv.aufgabe02.daten.reiseziele.Reiseziel;
import whs.mciv.aufgabe02.daten.reiseziele.ReisezielDaten;

public class BuchungAnlegenController extends BaseController {

    @FXML
    private TextField id;

    @FXML
    private ComboBox<Kunde> kundeComboBox;

    @FXML
    private TextField buchungsStatus;

    @FXML
    private TextField personenanzahl;

    @FXML
    private DatePicker anreisedatum;

    @FXML
    private TextField anzahlDerNaechte;

    @FXML
    private ComboBox<Reiseziel> reisezielComboBox;

    @FXML
    private ComboBox<String> verpflegung;

    @FXML
    private TextField gesamtpreis;

    private Buchung buchung;

    private ArrayList<String> reisezielName = new ArrayList<>();

    private ArrayList<String> kundenNamen = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Erzeige neues Buchung-Objekt um die Daten des Formulars später
        // speichern zu können. Der Buchung-Konstruktor erzeugt auch die ID.
        buchung = new Buchung();
        this.id.setText(buchung.getId());

        this.setzeReiseziele();
        this.setzeKundenDaten();

        this.changeReiseziel();
    }

    private void setzeKundenDaten() {
        ObservableList<Kunde> kundenDaten = FXCollections.observableArrayList();
        Collection<Kunde> alleKunden = KundenDaten.getAllKunden();
        List<Kunde> alleKundenSortiert = alleKunden.stream().sorted(Comparator.comparing(Kunde::getNachname)).collect(Collectors.toList());

        kundenDaten.addAll(alleKundenSortiert);

        this.kundeComboBox.setItems(kundenDaten);
        this.kundeComboBox.setConverter(new StringConverter<Kunde>() {
            @Override
            public String toString(Kunde kundenDatensatz) {
                return kundenDatensatz.getNachname() + ", " + kundenDatensatz.getVorname() + " (" + kundenDatensatz.getId() + ")";
            }

            @Override
            public Kunde fromString(String string) {
                return kundeComboBox.getItems().stream().filter(kunde -> kunde.getId().equals(string)).findFirst().orElse(null);
            }
        });
    }

    private void setzeReiseziele() {
        ObservableList<Reiseziel> reisezielDaten = FXCollections.observableArrayList();
        Collection<Reiseziel> alleReiseziele = ReisezielDaten.getAllReiseziele();
        List<Reiseziel> alleReisezieleSortiert = alleReiseziele.stream().sorted(Comparator.comparing(Reiseziel::getName)).collect(Collectors.toList());

        reisezielDaten.addAll(alleReisezieleSortiert);

        this.reisezielComboBox.setItems(reisezielDaten);
        this.reisezielComboBox.setConverter(new StringConverter<Reiseziel>() {
            @Override
            public String toString(Reiseziel reisezielDatensatz) {
                return reisezielDatensatz.getName() + " (" + reisezielDatensatz.getId() + ")";
            }

            @Override
            public Reiseziel fromString(String string) {
                return reisezielComboBox.getItems().stream().filter(reiseziel -> reiseziel.getId().equals(string)).findFirst().orElse(null);
            }
        });
    }

    private void changeReiseziel() {
        this.reisezielComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Reiseziel>() {
            @Override
            public void changed(ObservableValue<? extends Reiseziel> observableValue, Reiseziel reiseziel, Reiseziel ausgewaehltesZiel) {
                if (ausgewaehltesZiel != null && verpflegung.getSelectionModel().selectedItemProperty().get() != null) {
                    String ausgewaehlteVerpflegung = (String) verpflegung.getSelectionModel().selectedItemProperty().get();

                    updateGesamtpreis(ausgewaehltesZiel, ausgewaehlteVerpflegung);
                }
            }
        });

        this.verpflegung.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String ausgewaehlteVerpflegung) {
                if (ausgewaehlteVerpflegung != null && reisezielComboBox.getSelectionModel().selectedItemProperty().get() != null) {
                    Reiseziel ausgewaehltesZiel = (Reiseziel) reisezielComboBox.getSelectionModel().selectedItemProperty().get();

                    updateGesamtpreis(ausgewaehltesZiel, ausgewaehlteVerpflegung);
                }
            }
        });
    }

    private void updateGesamtpreis(Reiseziel ausgewaehltesZiel, String ausgewaehlteVerpflegung) {
        Double neuerPreis = 0.00;

        switch (ausgewaehlteVerpflegung) {
            case "Vollpension":
                neuerPreis = (double) (ausgewaehltesZiel.getPreisVollpension()) / 100;
                break;
            case "Halbpension":
                neuerPreis = (double) (ausgewaehltesZiel.getPreisHalbpension()) / 100;
                break;
        }

        gesamtpreis.setText(String.format("%.2f", neuerPreis));
    }

    public void setStage(Stage stage) {
        this.stage = stage;
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
