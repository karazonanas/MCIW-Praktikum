package whs.mciv.aufgabe02;

import whs.mciv.aufgabe02.daten.reiseziele.Reiseziel;
import whs.mciv.aufgabe02.daten.reiseziele.ReisezielDaten;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class BuchungsTool extends Application {
    private static final String HAUPTFENSTER_VIEW = "Hauptfenster.fxml";
    private static final String HAUPTFENSTER_TITLE = "Buchungssystem";
    private static final String REISEZIEL_ANLEGEN_VIEW = "ReisezielAnlegen.fxml";
    private static final String KUNDE_ANLEGEN_VIEW = "KundeAnlegen.fxml";
    private static final String BUCHUNG_ANLEGEN_VIEW = "BuchungAnlegen.fxml";
    private static final String REISEZIEL_ANLEGEN_TITLE = "Reiseziel anlegen";
    private static final String KUNDE_ANLEGEN_TITLE = "Kunde anlegen";
    private static final String BUCHUNG_ANLEGEN_TITLE = "Buchung anlegen";

    private Stage hauptfensterStage;

    @Override
    public void start(Stage stage) throws Exception {
        hauptfensterStage = stage;
        
        erzeugeReisezielDaten();
        
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

    public boolean zeigeReisezielAnlegenDialog() {
        return this.zeigeDialog(REISEZIEL_ANLEGEN_VIEW, REISEZIEL_ANLEGEN_TITLE);
    }

    public boolean zeigeKundeAnlegenDialog() {
        return this.zeigeDialog(KUNDE_ANLEGEN_VIEW, KUNDE_ANLEGEN_TITLE);
    }

    public boolean zeigeBuchungAnlegenDialog() {
        return this.zeigeDialog(BUCHUNG_ANLEGEN_VIEW, BUCHUNG_ANLEGEN_TITLE);
    }

    private boolean zeigeDialog (String view, String titel) {
        try {
            // Laden der view aus dem FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource(view));
            Parent dialogView = loader.load();

            // Neue Stage erstellen
            Stage dialogStage = new Stage();
            dialogStage.setTitle(titel);

            // Owner ist das Hauptfenster, damit die Modality weiß, wie sie sich verhalten soll
            dialogStage.initOwner(hauptfensterStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);

            // Neue Scene erstellen
            Scene scene = new Scene(dialogView);
            dialogStage.setScene(scene);

            // Der Controller muss die Stage kennen, um sie selbstständig schließen zu können
            BaseController controller = loader.getController();
            controller.setStage(dialogStage);

            // Dialog anzeigen bis er geschlossen wird
            dialogStage.showAndWait();

            // Gibt zurück, ob getätigte Angaben gespeichert wurden
            return controller.wurdeGespeichert();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
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
}
