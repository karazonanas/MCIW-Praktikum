package whs.mciv.aufgabe04.windowController.formulare;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import whs.mciv.aufgabe04.BaseController;
import whs.mciv.aufgabe04.daten.buchung.Buchung;
import whs.mciv.aufgabe04.daten.buchung.BuchungDaten;
import whs.mciv.aufgabe04.daten.kunde.Kunde;
import whs.mciv.aufgabe04.daten.kunde.KundenDaten;
import whs.mciv.aufgabe04.daten.reiseziele.Reiseziel;
import whs.mciv.aufgabe04.daten.reiseziele.ReisezielDaten;
import whs.mciv.aufgabe04.filter.FilterDatum;

import java.awt.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class BuchungAnlegenController extends FormularController {

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
    private Label gesamtpreis;

    private Buchung buchung;

    private ArrayList<String> reisezielName = new ArrayList<>();

    private ArrayList<String> kundenNamen = new ArrayList<>();

    private String anreiseDatumOldValue = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Erzeige neues Buchung-Objekt um die Daten des Formulars später
        // speichern zu können. Der Buchung-Konstruktor erzeugt auch die ID.
        buchung = new Buchung();
        this.id.setText(buchung.getId());

        anreisedatum.setDayCellFactory(new FilterDatum());
        ChangeListener<Boolean> anreiseDatumChangeListener = (observableValue, oldProperty, newProperty) -> {
            // Check format if the field has no focus anymore
            if (!newProperty && anreisedatum.getEditor().getText() != null && !anreisedatum.getEditor().getText().equals(anreiseDatumOldValue)) {
                LocalDate parsedDate = checkAnreisedatum();

                if (parsedDate != null) {
                    anreisedatum.setValue(parsedDate);
                } else {
                    anreisedatum.getEditor().setText(anreiseDatumOldValue);
                }
            }
        };
        anreisedatum.focusedProperty().addListener(anreiseDatumChangeListener);

        ChangeListener<Boolean> personenzahlChangeListener = (observableValue, oldProperty, newProperty) -> {
            // Check format if the field has no focus anymore
            if (!newProperty) {
                if (Integer.parseInt(personenanzahl.getText()) > 30) {
                    setMessage(Alert.AlertType.WARNING,"Die Reise beinhaltet mehr als 30 Teilnehmer!");
                }
            }
        };
        personenanzahl.focusedProperty().addListener(personenzahlChangeListener);

        ChangeListener<Boolean> anzahlDerNaechteChangeListener = (observableValue, oldProperty, newProperty) -> {
            // Check format if the field has no focus anymore
            if (!newProperty) {
                if (Integer.parseInt(anzahlDerNaechte.getText()) > 30) {
                    setMessage(Alert.AlertType.WARNING,"Die Anzahl der Nächte ist größer als 30!");
                }
            }
        };
        anzahlDerNaechte.focusedProperty().addListener(anzahlDerNaechteChangeListener);

        initButtons();

        this.setzeReiseziele();
        this.setzeKundenDaten();

        this.changeReisezielUndVerpflegung();
        this.checkNumberFields();
    }

    /**
     * Überprüfe das Anreisedatum
     *
     * @return das geparste LocalDate oder null
     */
    private LocalDate checkAnreisedatum() {
        LocalDate date;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.GERMAN);

        try {
            date = LocalDate.parse(anreisedatum.getEditor().getText(), formatter);

            if (date.isBefore(LocalDate.now())) {
                setMessage(Alert.AlertType.ERROR,"Das Datum liegt in der Vergangenheit");
                Toolkit.getDefaultToolkit().beep();
                anreisedatum.requestFocus();
            }

            if (date.isAfter(LocalDate.now().minusDays(1)) && date.isBefore(LocalDate.now().plusDays(2))) {
                setMessage(Alert.AlertType.WARNING,"Die Reise startet in weniger als zwei Tagen");
            }
            anreiseDatumOldValue = anreisedatum.getEditor().getText();

            return date;
        } catch (DateTimeParseException e) {
            if (! anreisedatum.getEditor().getText().isEmpty()) {
                setMessage(Alert.AlertType.ERROR,"Das Datum muss dem Format DD/MM/YYYY entsprechen");
                Toolkit.getDefaultToolkit().beep();
                anreisedatum.requestFocus();
            }
        }

        return null;
    }

    /**
     * Fülle die ComboBox für Kunden mit Kundendaten
     */
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

    /**
     * Fülle die ComboBox für Reiseziele mit Reisezielen
     */
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

    /**
     * Listener, wenn Reiseziel oder Verpflegung geändert wurden, um den Preis zu aktualisieren
     */
    private void changeReisezielUndVerpflegung() {
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

        ChangeListener<Boolean> anzahlGeandertChangeListener = new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldProperty, Boolean newProperty) {
                // Wenn kein Fokus auf Feld
                if (!newProperty) {
                    String ausgewaehlteVerpflegung = (String) verpflegung.getSelectionModel().selectedItemProperty().get();
                    Reiseziel ausgewaehltesZiel = (Reiseziel) reisezielComboBox.getSelectionModel().selectedItemProperty().get();

                    // Setze Zahl, wenn Feld leer ist und verlassen wird
                    if (personenanzahl.getText().equals("") || personenanzahl.getText().equals("0")) {
                        personenanzahl.setText("1");
                    }
                    if (anzahlDerNaechte.getText().equals("") || anzahlDerNaechte.getText().equals("0")) {
                        anzahlDerNaechte.setText("1");
                    }

                    updateGesamtpreis(ausgewaehltesZiel, ausgewaehlteVerpflegung);
                }
            }
        };
        this.personenanzahl.focusedProperty().addListener(anzahlGeandertChangeListener);
        this.anzahlDerNaechte.focusedProperty().addListener(anzahlGeandertChangeListener);
    }

    /**
     * Aktualisiere den Gesamtpreis
     *
     * @param ausgewaehltesZiel das ausgewähle Reiseziel
     * @param ausgewaehlteVerpflegung die ausgewählte Verpflegung
     */
    private void updateGesamtpreis(Reiseziel ausgewaehltesZiel, String ausgewaehlteVerpflegung) {
        double neuerPreis = 0.00;

        if (ausgewaehlteVerpflegung != null & ausgewaehltesZiel != null && personenanzahl.getText() != null && anzahlDerNaechte.getText() != null) {
            switch (ausgewaehlteVerpflegung) {
                case "Vollpension" -> neuerPreis = (double) (ausgewaehltesZiel.getPreisVollpension()) / 100;
                case "Halbpension" -> neuerPreis = (double) (ausgewaehltesZiel.getPreisHalbpension()) / 100;
            }

            neuerPreis = neuerPreis * Double.parseDouble(this.personenanzahl.getText()) * Double.parseDouble(this.anzahlDerNaechte.getText());

            gesamtpreis.setText(String.format("%,.2f", neuerPreis) + " €");
        }
    }

    /**
     * Überprüfe Zahlen-Eingabefelder
     */
    private void checkNumberFields() {
        // Anzahl der Reisenden
        this.personenanzahl.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    personenanzahl.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        // Anzahl der Nächte
        this.anzahlDerNaechte.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    anzahlDerNaechte.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    /**
     * Validiere das Formular
     *
     * @return true, wenn alles richtig eingegeben wurde
     */
    public boolean validateForm() {
        LinkedHashMap<String, Control> form = createForm();
        boolean formValid = validateForm(form);

        if (formValid) {
            // Check format of date
            String datum = anreisedatum.getEditor().getText();
            LocalDate date;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.GERMAN);

            try {
                date = LocalDate.parse(datum, formatter);
            } catch (DateTimeParseException e) {
                anreisedatum.requestFocus();
                setMessage(Alert.AlertType.ERROR, "Das Datum muss dem Format DD/MM/YYYY entsprechen");
                anreisedatum.getEditor().selectAll();
                Toolkit.getDefaultToolkit().beep();
                return false;
            }

            if (date.isBefore(LocalDate.now())) {
                anreisedatum.requestFocus();
                setMessage(Alert.AlertType.ERROR, "Das Datum liegt in der Vergangenheit");
                anreisedatum.getEditor().selectAll();
                Toolkit.getDefaultToolkit().beep();
                return false;
            }
        }

        return formValid;
    }

    /**
     * Erstelle HashMap für das Formular
     *
     * @return HashMap mit allen Feldern
     */
    private LinkedHashMap<String, Control> createForm() {
        LinkedHashMap<String, Control> form = new LinkedHashMap<>();
        form.put("Kunde", kundeComboBox);
        form.put("Anzahl der Reisenden", personenanzahl);
        form.put("Anreisedatum", anreisedatum);
        form.put("Anzahl der Übernachtungen",anzahlDerNaechte);
        form.put("Reiseziel", reisezielComboBox);
        form.put("Verpflegung", verpflegung);

        return form;
    }

    /**
     * Setze Styling der Felder zurück
     */
    protected void resetStylingOfElements() {
        kundeComboBox.setStyle("");
        personenanzahl.setStyle("");
        anreisedatum.setStyle("");
        anzahlDerNaechte.setStyle("");
        reisezielComboBox.setStyle("");
        verpflegung.setStyle("");
    }

    /**
     * Controller-spezifisch Überprüfe, ob Formular bearbeitet wurde
     *
     * @return wahr, wenn das Formular bearbeitet wurde
     */
    public boolean wasFormEdited() {
        LinkedHashMap<String, Control> form = createForm();

        return wasFormEdited(form);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void speichern() {
        this.buchung.setKunde(kundeComboBox.getSelectionModel().getSelectedItem());
        this.buchung.setAnreisedatum(anreisedatum.getEditor().getText());
//        @ToDo Gesamtpreis
//        this.buchung.setGesamtpreis();
        this.buchung.setAnzahlderNaechte(Integer.parseInt(anzahlDerNaechte.getText()));
        this.buchung.setPersonenanzahl(Integer.parseInt(personenanzahl.getText()));
        this.buchung.setVerpflegung(verpflegung.getSelectionModel().getSelectedItem());
        this.buchung.setBuchungsStatus(buchungsStatus.getText());
        this.buchung.setReiseziel(reisezielComboBox.getSelectionModel().getSelectedItem());

        BuchungDaten.speichereBuchung(this.buchung);

        wurdeGespeichert = true;
        stage.close();
    }

    @FXML
    public void abbrechen() {
        wurdeGespeichert = false;
        stage.close();
    }
}
