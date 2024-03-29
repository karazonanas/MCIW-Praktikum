package whs.mciv.aufgabe02.daten.buchung;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Diese Klasse stellt statische Methoden bereit, um alle gespeicherten
 * Buchungen zu verwalten.
 */
public abstract class BuchungDaten {
    /** Liste der Buchungen (IDs der Reiseziele werden als Schlüssel verwendet) */
    private static HashMap<String, Buchung> buchungen = new HashMap<>();

    /**
     * Abfragen aller gespeicherter Buchungen.
     * @return Alle gespeicherten Buchungen
     */
    public static Set<Buchung> getAllReiseziele() {
        return Collections.unmodifiableSet(new HashSet(buchungen.values().stream().filter(rz -> rz != null).collect(Collectors.toList()))
        );
    }

    /**
     * Abfragen einer bestimmten Buchung anhand der ID.
     * @param buchungId ID der gesuchten Buchung
     * @return Die gesuchte Buchung oder null, falls keine Buchung mit dieser
     * ID vorhanden ist.
     */
    public static Buchung getBuchung(String buchungId) {
        return buchungen.get(buchungId);
    }

    /**
     * Speichert eine Buchung.
     * @param buchung Die zu speichernde Buchung
     * @return true, wenn das Speichern erfolgreich war, andernfalls false.
     */
    public static boolean speichereBuchung(Buchung buchung) {
        if (buchungen.containsKey(buchung.getId())) {
            buchungen.put(buchung.getId(), buchung);
            return true;
        }
        return false;
    }

    /**
     * Löscht eine Buchung.
     * @param buchung Die zu löschende Buchung
     * @return true, wenn das Löschen erfolgreich war, andernfalls false.
     */
    public static boolean loescheBuchung(Buchung buchung) {
        if (buchungen.containsKey(buchung.getId())) {
            buchungen.remove(buchung.getId());
            return true;
        }
        return false;
    }

    /**
     * Erzeugt eine neue ID für eine Buchung.
     * @return Die ID der neuen Buchung
     */
    public static String erzeugeId() {
        int aktuellesJahr = LocalDate.now().getYear();
        String id = "B" + aktuellesJahr + (buchungen.size() + 1);
        buchungen.put(id, null);
        return id;
    }
}
