package whs.mciv.aufgabe02.daten.reiseziele;

import java.util.Objects;

/**
 * Ein Objekt dieser Klasse repräsentiert ein Reiseziel, für das Buchungen
 * angelegt werden können.
 * Bereits vorhandene Reiseziele werden in der Klasse ReisezielDaten verwaltet.
 */
public class Reiseziel {
    
    /** Die ID des Reiseziels. Wird automatisch im Konstruktor erzeugt. */
    private String id;
    
    /** Der Name des Reiseziels. */
    private String name;
    
    /** Eine kurze Beschreibung des Reiseziels. */
    private String beschreibung;
    
    /** Das Land, in dem sich das Reiseziel befindet.
     */
    private String land;
    
    /** Der Preis in Cent (Währung: Euro) für eine Person und Nacht mit
     * Halbpension (Übernachtung + 2 Mahlzeiten).  */
    private int preisHalbpension;
    
    /** Der Preis in Cent (Währung: Euro) für eine Person und Nacht mit
     * Halbpension (Übernachtung + 3 Mahlzeiten).  */
    private int preisVollpension;
    
    /**
     * Erzeugt ein neues Reiseziel inklusiver einer neuen ID.
     */
    public Reiseziel() {
        id = ReisezielDaten.erzeugeId();
    }

    /**
     * Gibt die ID des Reiseziels zurück.
     * @return ID
     */
    public String getId() {
        return id;
    }

    /**
     * Gibt den Namen des Reiseziels zurück.
     * @return Name des Reiseziels
     */
    public String getName() {
        return name;
    }

    /**
     * Setzt den Namen des Reiseziels.
     * @param name Name des Reiseziels.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gibt die Beschreibung des Reiseziels zurück.
     * @return Kurze Beschreibung des Reiseziels
     */
    public String getBeschreibung() {
        return beschreibung;
    }

    /**
     * Setzt die Beschreibung des Reiseziels.
     * @param beschreibung Kurze Beschreibung des Reiseziels
     */
    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    /**
     * Gibt das Land zurück, in dem sich das Reiseziel befindet.
     * @return Land des Reiseziels
     */
    public String getLand() {
        return land;
    }

    /**
     * Setzt das Land, in dem sich das Reiseziel befindet.
     * @param land Land des Reiseziels
     */
    public void setLand(String land) {
        this.land = land;
    }

    /**
     * Gibt den Preis in Cent (Währung: Euro) für eine Person und Nacht mit
     * Halbpension (Übernachtung + 2 Mahlzeiten) zurück.
     * @return Halbpensionspreis
     */
    public int getPreisHalbpension() {
        return preisHalbpension;
    }

    /**
     * Setzt den Preis in Cent (Währung: Euro) für eine Person und Nacht mit
     * Halbpension (Übernachtung + 2 Mahlzeiten).
     * @param preisHalbpension Halbpensionspreis
     */
    public void setPreisHalbpension(int preisHalbpension) {
        this.preisHalbpension = preisHalbpension;
    }

    /**
     * Gibt den Preis in Cent (Währung: Euro) für eine Person und Nacht mit
     * Vollpension (Übernachtung + 3 Mahlzeiten) zurück.
     * @return Vollpensionspreis
     */
    public int getPreisVollpension() {
        return preisVollpension;
    }
    
    /**
     * Setzt den Preis in Cent (Währung: Euro) für eine Person und Nacht mit
     * Vollpensions (Übernachtung + 3 Mahlzeiten).
     * @param preisVollpension Vollpensionspreis
     */
    public void setPreisVollpension(int preisVollpension) {
        this.preisVollpension = preisVollpension;
    }
    
    @Override
    public String toString() {
        return name;
    }    

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        
        if (!(obj instanceof Reiseziel)) {
            return false;
        }
        
        Reiseziel other = (Reiseziel) obj;
        
        if (Objects.equals(other.id, this.id)) {
            return true;
        }
        
        return false;
    }
    
    
}
