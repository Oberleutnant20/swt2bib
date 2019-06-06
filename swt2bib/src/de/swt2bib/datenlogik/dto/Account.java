package de.swt2bib.datenlogik.dto;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class Account implements Serializable {

    private String username;
    private String passwort;
    private boolean mitarbeiter;
    private int userid;
    private String vorname;
    private String nachname;
    private int plz;
    private String strasse;
    private String hausnummer;
    private String ort;

    public Account(String username, String passwort, boolean mitarbeiter, int userid, String vorname, String nachname, int plz, String strasse, String hausnummer, String ort) {
        this.username = username;
        this.passwort = passwort;
        this.mitarbeiter = mitarbeiter;
        this.userid = userid;
        this.vorname = vorname;
        this.nachname = nachname;
        this.plz = plz;
        this.strasse = strasse;
        this.hausnummer = hausnummer;
        this.ort = ort;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public void setMitarbeiter(boolean mitarbeiter) {
        this.mitarbeiter = mitarbeiter;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public void setPlz(int plz) {
        this.plz = plz;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public void setHausnummer(String hausnummer) {
        this.hausnummer = hausnummer;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswort() {
        return passwort;
    }

    public boolean isMitarbeiter() {
        return mitarbeiter;
    }

    public int getUserid() {
        return userid;
    }

    public String getVorname() {
        return vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public int getPlz() {
        return plz;
    }

    public String getStrasse() {
        return strasse;
    }

    public String getHausnummer() {
        return hausnummer;
    }

    public String getOrt() {
        return ort;
    }
}
