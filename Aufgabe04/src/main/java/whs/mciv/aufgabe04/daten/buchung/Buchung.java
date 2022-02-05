package whs.mciv.aufgabe04.daten.buchung;

import whs.mciv.aufgabe04.daten.kunde.Kunde;
import whs.mciv.aufgabe04.daten.reiseziele.Reiseziel;

import java.util.Objects;

/**
 * Ein Objekt dieser Klasse repräsentiert eine Buchung.
 * Bereits vorhandene Buchungen werden in der Klasse BuchungDaten verwaltet.
 */
public class Buchung {

    /** Die ID der Buchung. Wird automatisch im Konstruktor erzeugt. */
    private String id;

    /** Die Kunden-ID. */
    private Kunde kunde;

    /** Status der Buchung **/
    private String buchungsStatus;

    /** Personenanzahl in der Buchung */
    private int personenanzahl;

    /** Das Anreisedatum @ToDo: DateTime */
    private String anreisedatum;

    /** Anzahl der Übernachtungen */
    private int anzahlderNaechte;

    /** Das Reiseziel */
    private Reiseziel reiseziel;

    /** Verpflegung */
    private String verpflegung;

    /** Gesamtpreis der Buchung */
    private double gesamtpreis;


    /**
     * Erzeugt eine neue Buchung inklusiver einer neuen ID.
     */
    public Buchung() {
        id = BuchungDaten.erzeugeId();
    }

    /**
     * Gibt die ID des Reiseziels zurück.
     * @return ID
     */
    public String getId() {
        return id;
    }

    /**
     * Gibt den Kunden der Buchung zurück.
     * @return Kunden-Objekt der Buchung
     */
    public Kunde getKunde() {
        return kunde;
    }

    /**
     * Setzt den Kunden der Buchung.
     * @param kunde Kunden-Objekt.
     */
    public void setKunde(Kunde kunde) {
        this.kunde = kunde;
    }

    /**
     * Gebe den Status zurück
     * @return Status der Buchung
     */
    public String getBuchungsStatus() {
        return buchungsStatus;
    }

    /**
     * Setze den Status der Buchung
     * @param status Status der Buchung
     */
    public void setBuchungsStatus(String buchungsStatus) {
        this.buchungsStatus = buchungsStatus;
    }

    /**
     * Gebe die Personenanzahl zurück
     * @return Personenanzahl
     */
    public int getPersonenanzahl() {
        return personenanzahl;
    }

    /**
     * Setze die Personenanzahl
     * @param personenanzahl Personenanzahl der Buchung
     */
    public void setPersonenanzahl(int personenanzahl) {
        this.personenanzahl = personenanzahl;
    }

    /**
     * Gibt das Anreisedatum zurück
     * @return Anreisedatum der Buchung
     */
    public String getAnreisedatum() {
        return anreisedatum;
    }

    /**
     * Setzt das Anreisedatum
     * @param anreisedatum der Buchung
     */
    public void setAnreisedatum(String anreisedatum) {
        this.anreisedatum = this.anreisedatum;
    }

    /**
     * Gebe die Anzahl der Nächte zurück
     * @return Anzahl der Nächte der Buchung
     */
    public int getAnzahlderNaechte() {
        return anzahlderNaechte;
    }

    /**
     * Setze die Anzahl der Nächte
     * @param anzahlderNaechte Anzahl der Nächte der Buchung
     */
    public void setAnzahlderNaechte(int anzahlderNaechte) {
        this.anzahlderNaechte = this.anzahlderNaechte;
    }

    /**
     * Gibt das Reiseziel zurück
     * @return Reiseziel
     */
    public Reiseziel getReiseziel() {
        return reiseziel;
    }

    /**
     * Setzt die Reiseziel
     * @param reiseziel Reiseziel
     */
    public void setReiseziel(Reiseziel reiseziel) {
        this.reiseziel = reiseziel;
    }

    /**
     * Gebe die Verpflegung zurück
     * @return Gebuchte Verpflegung
     */
    public String getVerpflegung() {
        return verpflegung;
    }

    /**
     * Setze die Verpflegung (Halb-/Vollpension)
     * @param verpflegung Verpflegung
     */
    public void setVerpflegung(String verpflegung) {
        this.verpflegung = this.verpflegung;
    }

    /**
     * Gibt den Gesamtpreis in Euro zurück
     * @return Gesamtpreis
     */
    public double getGesamtpreis() {
        return gesamtpreis;
    }

    /**
     * Setzt den Gesamtpreis in Euro
     * @param preis Gesamtpreis
     */
    public void setGesamtpreis(double gesamtpreis) {
        this.gesamtpreis = gesamtpreis;
    }

    @Override
    public String toString() {
        return this.id;
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

        if (!(obj instanceof Buchung)) {
            return false;
        }

        Buchung other = (Buchung) obj;

        if (Objects.equals(other.id, this.id)) {
            return true;
        }

        return false;
    }
}