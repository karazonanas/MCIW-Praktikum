package whs.mciv.aufgabe04;

import whs.mciv.aufgabe04.daten.buchung.Buchung;
import whs.mciv.aufgabe04.daten.buchung.BuchungDaten;
import whs.mciv.aufgabe04.daten.kunde.Kunde;
import whs.mciv.aufgabe04.daten.reiseziele.Reiseziel;
import whs.mciv.aufgabe04.daten.reiseziele.ReisezielDaten;
import whs.mciv.aufgabe04.daten.kunde.KundenDaten;
import java.io.IOException;
import java.time.LocalDate;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import whs.mciv.aufgabe04.windowController.formulare.FormularController;

public class BuchungsTool extends Application {
    private static final String HAUPTFENSTER_VIEW = "Hauptfenster.fxml";
    private static final String REISEZIEL_ANLEGEN_VIEW = "formulare/ReisezielAnlegen.fxml";
    private static final String KUNDE_ANLEGEN_VIEW = "formulare/KundeAnlegen.fxml";
    private static final String BUCHUNG_ANLEGEN_VIEW = "formulare/BuchungAnlegen.fxml";
    private static final String KUNDE_UEBERSICHT_VIEW = "uebersichten/KundeUebersicht.fxml";
    private static final String BUCHUNG_UEBERSICHT_VIEW = "uebersichten/BuchungUebersicht.fxml";

    private static final String HAUPTFENSTER_TITLE = "Buchungssystem";
    private static final String REISEZIEL_ANLEGEN_TITLE = "Reiseziel anlegen";
    private static final String KUNDE_ANLEGEN_TITLE = "Kunde anlegen";
    private static final String BUCHUNG_ANLEGEN_TITLE = "Buchung anlegen";
    private static final String KUNDE_UEBERSICHT_TITLE = "Kundenübersicht";
    private static final String BUCHUNGS_UEBERSICHT_TITLE = "Buchungsübersicht";

    private Stage hauptfensterStage;

    @Override
    public void start(Stage stage) throws Exception {
        hauptfensterStage = stage;
        
        erzeugeReisezielDaten();
        erzeugeKundenDaten();
        erzeugeBuchungen();
        
        zeigeHauptfenster();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    public void zeigeHauptfenster() throws IOException {
        // FXML view des Hauptfensters laden
        FXMLLoader loader = new FXMLLoader(BuchungsTool.class.getResource(HAUPTFENSTER_VIEW));
        Parent hauptfensterView = loader.load();

        // Der Controller muss die Hauptanwendung kennen, um mit ihr interagieren
        // zu können.
        HauptfensterController hauptfensterController = loader.getController();
        hauptfensterController.setMainApp(this);
        
        Scene scene = new Scene(hauptfensterView);
        hauptfensterStage.setTitle(HAUPTFENSTER_TITLE);
        hauptfensterStage.setScene(scene);
        hauptfensterStage.show();
    }

    public BaseController zeigeReisezielAnlegenDialog() {
        return this.zeigeDialog(REISEZIEL_ANLEGEN_VIEW, REISEZIEL_ANLEGEN_TITLE, hauptfensterStage);
    }

    public BaseController zeigeKundeAnlegenDialog() {
        return this.zeigeDialog(KUNDE_ANLEGEN_VIEW, KUNDE_ANLEGEN_TITLE, hauptfensterStage);
    }

    public BaseController zeigeBuchungAnlegenDialog() {
        return this.zeigeDialog(BUCHUNG_ANLEGEN_VIEW, BUCHUNG_ANLEGEN_TITLE, hauptfensterStage);
    }

    public BaseController zeigeKundeUebersichtDialog() {
        return this.zeigeDialog(KUNDE_UEBERSICHT_VIEW, KUNDE_UEBERSICHT_TITLE, hauptfensterStage);
    }

    public BaseController zeigeBuchungUebersichtDialog() {
        return this.zeigeDialog(BUCHUNG_UEBERSICHT_VIEW, BUCHUNGS_UEBERSICHT_TITLE, hauptfensterStage);
    }

    public BaseController zeigeDialog (String view, String titel, Stage stage) {
        try {
            // Laden der view aus dem FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource(view));
            Parent dialogView = loader.load();

            // Neue Stage erstellen
            Stage dialogStage = new Stage();
            dialogStage.setTitle(titel);

            // Owner ist das Hauptfenster, damit die Modality weiß, wie sie sich verhalten soll
            dialogStage.initOwner(stage);
            dialogStage.initModality(Modality.WINDOW_MODAL);

            // Neue Scene erstellen
            Scene scene = new Scene(dialogView);
            dialogStage.setScene(scene);

            // Der Controller muss die Stage kennen, um sie selbstständig schließen zu können
            BaseController controller = loader.getController();
            controller.setStage(dialogStage);

            if (controller instanceof FormularController formularController) {
                controller.getStage().setOnHidden(h -> formularController.runOnClose());
            }

            // Dialog anzeigen bis er geschlossen wird
            dialogStage.show();

            // Gibt zurück, ob getätigte Angaben gespeichert wurden
            return controller;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void closeStage() {
        hauptfensterStage.close();
    }

    private void erzeugeReisezielDaten() {
        Reiseziel rz = new Reiseziel();
        rz.setName("Playa de Palma - Mallorca");
        rz.setLand("Spanien");
        rz.setBeschreibung("Idyllisches Hotel in der Metropole Palma de Mallorca mit weitläufiger Poollandschaft!");
        rz.setPreisHalbpension(12800);
        rz.setPreisVollpension(25000);
        ReisezielDaten.speichereReiseziel(rz);
        
        rz = new Reiseziel();
        rz.setName("Poseidon Beach Resort - Kreta");
        rz.setLand("Griechenland");
        rz.setBeschreibung("Traumhaft schönes fünf Sterne Hotel auf der griechischen Insel Kreta!");
        rz.setPreisHalbpension(35995);
        rz.setPreisVollpension(50999);
        ReisezielDaten.speichereReiseziel(rz);
        
        rz = new Reiseziel();
        rz.setName("Villa del Cielo - Jandia - Kanarische Inseln");
        rz.setLand("Spanien");
        rz.setBeschreibung("Himmlischer Urlaub auf den Kanaren in der Villa des Himmels.");
        rz.setPreisHalbpension(8075);
        rz.setPreisVollpension(15020);
        ReisezielDaten.speichereReiseziel(rz);
        
        rz = new Reiseziel();
        rz.setName("Hotel Luisa im Schwarzwald");
        rz.setLand("Deutschland");
        rz.setBeschreibung("Mitten in der Natur gelegenes Hotel zum entspannen und Wandern.");
        rz.setPreisHalbpension(20030);
        rz.setPreisVollpension(72525);
        ReisezielDaten.speichereReiseziel(rz);
    }

    private void erzeugeKundenDaten() {
        Kunde kunde = new Kunde();
        kunde.setAnrede("Herr");
        kunde.setVorname("Max");
        kunde.setNachname("Mustermann");
        kunde.setAdresse("Musterstraße 1");
        kunde.setTelefonnummer("3246547987");
        kunde.setOrt("Musterstadt");
        kunde.setPlz("58594");
        kunde.setBundesland("Nordrhein-Westfalen");
        kunde.setLand("Deutschland");
        kunde.setKontoinhaber("Max Mustermann");
        kunde.setIban("DE3045611485500304");
        kunde.setBank("Sparkasse Gelsenkirchen");
        kunde.setBic("WELADED1GEL");
        kunde.setEmail("no@mail.de");
        KundenDaten.speichereKunde(kunde);

        Kunde kunde2 = new Kunde();
        kunde2.setAnrede("Herr");
        kunde2.setVorname("Stephan");
        kunde2.setNachname("Wannemaker");
        kunde2.setTelefonnummer("65498765462");
        kunde2.setAdresse("Ollenhauer Str. 38");
        kunde2.setOrt("Stuttgart Asemwald");
        kunde2.setPlz("70599");
        kunde2.setBundesland("Baden-Württemberg");
        kunde2.setLand("Deutschland");
        kunde2.setKontoinhaber("Stephan Wannemaker");
        kunde2.setIban("DE12500105177264191851");
        kunde2.setBank("Sparkasse Gelsenkirchen");
        kunde2.setBic("WELADED1GEL");
        kunde2.setEmail("no2@mail.de");
        KundenDaten.speichereKunde(kunde2);

        Kunde kunde3 = new Kunde();
        kunde3.setAnrede("Frau");
        kunde3.setVorname("Christin");
        kunde3.setNachname("Kuhn");
        kunde3.setTelefonnummer("65498765436");
        kunde3.setAdresse("Kieler Strasse 43");
        kunde3.setOrt("Engelsberg");
        kunde3.setPlz("84549");
        kunde3.setBundesland("Bayern");
        kunde3.setLand("Deutschland");
        kunde3.setKontoinhaber("Christin Kuhn");
        kunde3.setIban("DE46500105175887234626");
        kunde3.setBank("Sparkasse Gelsenkirchen");
        kunde3.setBic("WELADED1GEL");
        kunde3.setEmail("no3@mail.de");
        KundenDaten.speichereKunde(kunde3);
    }

    private void erzeugeBuchungen() {

        Buchung buchung1= new Buchung();
        buchung1.setReiseziel(ReisezielDaten.getReiseziel("RZ20221"));
        buchung1.setPersonenanzahl(2);
        buchung1.setAnzahlderNaechte(2);
        buchung1.setAnreisedatum(LocalDate.of(2022,6,12));
        buchung1.setKunde(KundenDaten.getKunde("KD20221"));
        buchung1.setVerpflegung("Vollpension");
        BuchungDaten.speichereBuchung(buchung1);

        Buchung buchung2= new Buchung();
        buchung2.setReiseziel(ReisezielDaten.getReiseziel("RZ20223"));
        buchung2.setPersonenanzahl(6);
        buchung2.setAnzahlderNaechte(4);
        buchung2.setAnreisedatum(LocalDate.of(2022,8,12));
        buchung2.setKunde(KundenDaten.getKunde("KD20222"));
        buchung2.setVerpflegung("Vollpension");
        BuchungDaten.speichereBuchung(buchung2);

        Buchung buchung3= new Buchung();
        buchung3.setReiseziel(ReisezielDaten.getReiseziel("RZ20221"));
        buchung3.setPersonenanzahl(6);
        buchung3.setAnzahlderNaechte(4);
        buchung3.setAnreisedatum(LocalDate.of(2022,12,12));
        buchung3.setKunde(KundenDaten.getKunde("KD20223"));
        buchung3.setVerpflegung("Vollpension");
        BuchungDaten.speichereBuchung(buchung3);
    }
}
