package whs.mciv.aufgabe02.daten;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Diese Klasse stellt statische Methoden bereit, um alle gespeicherten
 * Reiseziele zu verwalten.
 */
public abstract class ReisezielDaten {
    /** Liste der Reiseziele (IDs der Reiseziele werden als Schlüssel verwendet) */
    private static HashMap<String, Reiseziel> reiseziele = new HashMap<>();
    
    /**
     * Abfragen aller gespeicherter Reiseziele.
     * @return Alle gespeicherten Reiseziele
     */
    public static Set<Reiseziel> getAllReiseziele() {
        return Collections.unmodifiableSet(new HashSet(reiseziele.values().stream().filter(rz -> rz != null).collect(Collectors.toList()))
        );
    }
    
    /**
     * Abfragen eines bestimmten Reiseziels anhand der ID.
     * @param reisezielId ID des gesuchten Reiseziels
     * @return Das gesuchte Reiseziel oder null, falls kein Reiseziel mit dieser
     * ID vorhanden ist.
     */
    public static Reiseziel getReiseziel(String reisezielId) {
        return reiseziele.get(reisezielId);
    }
    
    /**
     * Speichert ein Reiseziel.
     * @param reiseziel Das zu speichernde Reiseziel
     * @return true, wenn das Speichern erfolgreich war, andernfalls false.
     */
    public static boolean speichereReiseziel(Reiseziel reiseziel) {
        if (reiseziele.containsKey(reiseziel.getId())) {
            reiseziele.put(reiseziel.getId(), reiseziel);
            return true;
        }
        return false;
    }
    
    /**
     * Löscht ein Reiseziel.
     * @param reiseziel Das zu löschende Reiseziel
     * @return true, wenn das Löschen erfolgreich war, andernfalls false.
     */
    public static boolean loescheReiseziel(Reiseziel reiseziel) {
        if (reiseziele.containsKey(reiseziel.getId())) {
            reiseziele.remove(reiseziel.getId());
            return true;
        }
        return false;
    }
    
    /**
     * Erzeugt eine neue ID für ein Reiseziel.
     * @return Die ID des neuen Reiseziels
     */
    public static String erzeugeId() {
        int aktuellesJahr = LocalDate.now().getYear();
        String id = "RZ" + aktuellesJahr + (reiseziele.size() + 1);
        reiseziele.put(id, null);
        return id;
    }
}
