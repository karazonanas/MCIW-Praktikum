package whs.mciv.aufgabe04.daten.kunde;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class KundenDaten {
    private static HashMap<String, Kunde> kunden = new HashMap<>();
    public static int letzteId = 0;

    public static Set<Kunde> getAllKunden() {
        return Collections.unmodifiableSet(new HashSet<>(kunden.values().stream().filter(k -> k != null).collect(Collectors.toList())));
    }

    public static HashMap<String, Kunde> getKunden() {
        return kunden;
    }

    public static Kunde getKunde (String kundenId) {
        return kunden.get(kundenId);
    }

    public static boolean kundeExistiert(String buchungId) {
        if (kunden.containsKey(buchungId)) {
            return kunden.get(buchungId) != null;
        }

        return false;
    }

    public static boolean speichereKunde (Kunde kunde) {
        if (kunden.containsKey(kunde.getId())) {
            kunden.put(kunde.getId(), kunde);
            return true;
        }
        return false;
   }

   public static boolean loescheKunde (Kunde kunde) {
        if (kunden.containsKey(kunde.getId())) {
            kunden.remove(kunde.getId());
            return true;
        }
        return false;
   }

    public static boolean loescheKundenBeiId(String kundenId) {
        if (kunden.containsKey(kundenId)) {
            kunden.remove(kundenId);
            letzteId = letzteId - 1;

            return true;
        }

        return false;
    }

   public static String erzeugeId () {
       int aktuellesJahr = LocalDate.now().getYear();
       letzteId = letzteId + 1;
       String id = "KD" + aktuellesJahr + letzteId;
       kunden.put(id, null);
       return id;
   }
}
