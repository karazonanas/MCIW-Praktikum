package whs.mciv.aufgabe04.daten.kunde;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class KundenDaten {
    private static HashMap<String, Kunde> kunden = new HashMap<>();

    public static Set<Kunde> getAllKunden() {
        return Collections.unmodifiableSet(new HashSet<>(kunden.values().stream().filter(k -> k != null).collect(Collectors.toList())));
    }

    public static HashMap<String, Kunde> getKunden() {
        return kunden;
    }

    public static Kunde getKunde (String kundenId) {
        return kunden.get(kundenId);
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

   public static String erzeugeId () {
       int aktuellesJahr = LocalDate.now().getYear();
       String id = "KD" + aktuellesJahr + (kunden.size() + 1);
       kunden.put(id, null);
       return id;
   }

}
