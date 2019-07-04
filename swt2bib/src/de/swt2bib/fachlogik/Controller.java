package de.swt2bib.fachlogik;

import de.swt2bib.datenlogik.dto.Account;
import de.swt2bib.datenlogik.dto.Ausleihe;
import de.swt2bib.datenlogik.dto.History;
import de.swt2bib.datenlogik.dto.Medien;
import de.swt2bib.fachlogik.kategorieverwaltung.Kategorienverwaltung;
import de.swt2bib.fachlogik.accountverwaltung.Accountverwaltung;
import de.swt2bib.fachlogik.ausleihverwaltung.Ausleiheverwaltung;
import de.swt2bib.fachlogik.genreverwaltung.Genreverwaltung;
import de.swt2bib.fachlogik.historyverwaltung.Historyverwaltung;
import de.swt2bib.fachlogik.medienverwaltung.Medienverwaltung;
import de.swt2bib.fachlogik.crypt.Password;
import de.swt2bib.fachlogik.languageverwaltung.Languageverwaltung;
import de.swt2bib.ui.ElternPanel;
import de.swt2bib.ui.PanelHandler;
import de.swt2bib.info.exceptions.ConnectionError;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class Controller {

    // Attribute
    private final Accountverwaltung accountverwaltung;
    private final Medienverwaltung medienverwaltung;
    private final Ausleiheverwaltung ausleiheverwaltung;
    private final Kategorienverwaltung kategorienverwaltung;
    private final Genreverwaltung genreverwaltung;
    private final Historyverwaltung historyverwaltung;
    private final Languageverwaltung languageverwaltung;
    private PanelHandler panelHandler;
    private Account aktuellerUser;
    private ArrayList<History> historyListe;
    private ArrayList<Ausleihe> ausleiheListe;
    private Password passwd = new Password();

    /**
     * Konstruktor für den Controller.
     *
     * @param languageverwaltung Verwaltungsklasse für die Sprache
     * @param accountverwaltung Verwaltungsklasse für die Accounts
     * @param medienverwaltung Verwaltungsklasse für die Medien
     * @param ausleiheverwaltung Verwaltungsklasse für die Ausleihen
     * @param kategorienverwaltung Verwaltungsklasse für die Kategorien
     * @param genreverwaltung Verwaltungsklasse für die Genres
     * @param historyverwaltung Verwaltungsklasse für die History
     */
    public Controller(Languageverwaltung languageverwaltung, Accountverwaltung accountverwaltung, Medienverwaltung medienverwaltung,
            Ausleiheverwaltung ausleiheverwaltung, Kategorienverwaltung kategorienverwaltung, Genreverwaltung genreverwaltung, Historyverwaltung historyverwaltung) {
        this.languageverwaltung = languageverwaltung;
        this.accountverwaltung = accountverwaltung;
        this.medienverwaltung = medienverwaltung;
        this.ausleiheverwaltung = ausleiheverwaltung;
        this.kategorienverwaltung = kategorienverwaltung;
        this.genreverwaltung = genreverwaltung;
        this.historyverwaltung = historyverwaltung;
    }

    /**
     * starten des Controllers
     *
     * @param panelHandler Panelhandler
     */
    public void start(PanelHandler panelHandler) {
        de.swt2bib.Logger.info(this, "starten");
        ausleihenPruefen();
        this.panelHandler = panelHandler;
    }

    /**
     * Aktualisierung der Panels beim initialen starten der Anwendung
     */
    public void initUpdate() {
        de.swt2bib.Logger.info(this, "initUpdate");
        accountverwaltung.notifyPanels();
        medienverwaltung.notifyPanels();
        ausleiheverwaltung.notifyPanels();
        kategorienverwaltung.notifyPanels();
        genreverwaltung.notifyPanels();
        historyverwaltung.notifyPanels();
        try {
            languageverwaltung.getDeutsch();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Account getAktuellerUser() {
        return aktuellerUser;
    }

    public Account setAktuellerUser(String accountname, String passwort) {
        de.swt2bib.Logger.info(this, "setAktuellerUser");
        aktuellerUser = matchingUser(accountname, passwort);
        if (aktuellerUser != null) {
            ladeUserDaten();
            return aktuellerUser;
        }

        return null;
    }

    /**
     * prüft ob der aktuelle User ein Mitarbeiter ist
     *
     * @return true: ist Mitarbeiter, false: ist kein Mitarbeiter
     */
    public boolean isMitarbeiter() {
        de.swt2bib.Logger.info(this, "isMitarbeiter");
        if (aktuellerUser != null) {
            return aktuellerUser.isMitarbeiter();
        }
        return false;
    }

    /**
     * Fügt einen Historyeintrag der Liste hinzu
     *
     * @param history Histroy DTO
     */
    public void addHistory(History history) {
        de.swt2bib.Logger.info(this, "addHistory");
        historyverwaltung.add(history);
    }

    /**
     * Fügt eine Ausleihe der Liste hinzu
     *
     * @param ausleihe Ausleihe DTO
     */
    public void addAusleihe(Ausleihe ausleihe) {
        de.swt2bib.Logger.info(this, "addAusleihe");
        ausleiheverwaltung.add(ausleihe);
    }

    /**
     * Lädt die Listen des jeweiligen Users
     */
    public void ladeUserDaten() {
        historyListe = ladeHistory();
        ausleiheListe = ladeAusleihe();
    }

    /**
     * Zieht den passenden User nach accountname und passwort aus der Liste
     * aller User.
     *
     * @param accountname Angabe Accountname
     * @param passwort Angabe Passwort
     * @return Account bei erfolg.
     */
    public Account matchingUser(String accountname, String passwort) {
        de.swt2bib.Logger.info(this, "matchingUser");
        List<Account> list = accountverwaltung.get();
        String compare = generatePwHash(passwort);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getPasswort().equals(compare) && list.get(i).getUsername().equals(accountname)) {
                return list.get(i);
            }
        }
        return null;
    }

    /**
     * Läd die User History Informationen.
     *
     * @return Liste von der History eines Users
     */
    private ArrayList<History> ladeHistory() {
        int userid = aktuellerUser.getUserid();
        ArrayList<History> list = new ArrayList<>();
        de.swt2bib.Logger.info(this, "ladeHistory");
        List<History> listegesamt = historyverwaltung.get();
        for (int i = 0; i < listegesamt.size(); i++) {
            if (listegesamt.get(i).getUserid() == userid) {
                de.swt2bib.Logger.debug(this, "ladeHistory, History füllen");
                list.add(listegesamt.get(i));
            }
        }
        return list;
    }

    /**
     * Läd die aktuelle Ausleihe eines Nutzers.
     *
     * @return Liste eines Nutzers mit Ausgeliehen Büchern
     */
    private ArrayList<Ausleihe> ladeAusleihe() {
        int userid = aktuellerUser.getUserid();
        ArrayList<Ausleihe> list = new ArrayList<>();
        de.swt2bib.Logger.info(this, "ladeAusleihe");
        List<Ausleihe> listegesamt = ausleiheverwaltung.get();
        for (int i = 0; i < listegesamt.size(); i++) {
            de.swt2bib.Logger.debug(this, "ladeAusleihe, Ausleihe Schleife");
            if (listegesamt.get(i).getUserid() == userid) {
                de.swt2bib.Logger.debug(this, "ladeAusleihe, Ausleihe füllen");
                list.add(listegesamt.get(i));
            }
        }
        return list;
    }

    public List<History> getHistoryListe() {
        return historyverwaltung.get();
    }

    /**
     * Reicht den geänderten Account zum speichern in die Verwaltung weiter.
     *
     * @param a Angabe Account
     */
    public void saveAccountChange(Account a) {
        accountverwaltung.update(a);
    }

    /**
     * Speichert verändertes Medium.
     *
     * @param m Angabe Medium
     */
    public void saveMediumChange(Medien m) {
        de.swt2bib.Logger.info(this, "saveMediumChange");
        ArrayList<Medien> list = getAllMedien();
        for (Medien medium : list) {
            if (medium.getId() == m.getId()) {
                medienverwaltung.delete(medium);
            }
        }
        medienverwaltung.update(m);
    }

    /**
     * reicht zu löschende Ausleihe an Verwaltung weiter.
     *
     * @param a Angabe Ausleihe
     */
    public void deleteAusleihe(Ausleihe a) {
        de.swt2bib.Logger.info(this, "deleteAusleihe");
        ausleiheverwaltung.delete(a);
    }

    /**
     * reicht zu speichernden Account weiter.
     *
     * @param account Angabe Account
     */
    public void saveAccount(Account account) {
        de.swt2bib.Logger.info(this, "saveAccount");
        accountverwaltung.add(account);
    }

    /**
     * reicht zu löschenden Historyeintrag weiter.
     *
     * @param h Angabe History
     */
    public void deleteHistory(History h) {
        de.swt2bib.Logger.info(this, "deleteHistory");
        historyverwaltung.delete(h);
    }

    public ArrayList<Ausleihe> getAllAusleihenListe() {
        de.swt2bib.Logger.info(this, "getAllAusleihenListe");
        return ausleiheverwaltung.get();
    }

    public ArrayList<Account> getAllAccountsListe() {
        de.swt2bib.Logger.info(this, "getAllAccountsListe");
        return accountverwaltung.get();
    }

    public ArrayList<Medien> getAllMedien() {
        de.swt2bib.Logger.info(this, "getAllMedien");
        ArrayList<Medien> list = medienverwaltung.get();
        de.swt2bib.Logger.debug(this, "medien" + list.size());
        return list;
    }

    /**
     * Startet die Methoden zum speichern der Einträge in den Verwaltungen.
     *
     * @throws IOException IO Fehler
     * @throws ConnectionError Datenbankverbinungsfehler
     */
    public void saveDB() throws IOException, ConnectionError {
        de.swt2bib.Logger.info(this, "Speichern");
        accountverwaltung.speichern();
        medienverwaltung.speichern();
        ausleiheverwaltung.speichern();
        kategorienverwaltung.speichern();
        genreverwaltung.speichern();
        historyverwaltung.speichern();
    }

    /**
     * Überprüft ob ein Eintrag aus der Ausleihe zur History hinzugefügt werden
     * soll.
     */
    private void ausleihenPruefen() {
        de.swt2bib.Logger.debug(this, "Ausleihe prüfen");
        ArrayList<Ausleihe> liste = ausleiheverwaltung.get();
        Date heute = new Date();
        int id = historyverwaltung.get().size();
        for (int i = 0; i < liste.size(); i++) {
            if (liste.get(i).getDate().before(heute)) {
                History history = new History(id++, liste.get(i).getUserid(), liste.get(i).getMedienid(), liste.get(i).getKategorieid());
                ausleiheverwaltung.delete(liste.get(i));
            }
        }
    }

    /**
     * Reicht eine Ausleihe weiter zum Speichern.
     *
     * @param a Ausleihe
     */
    public void saveAusleihe(Ausleihe a) {
        ausleiheverwaltung.add(a);
    }

    public void setMedienObserver(ElternPanel... panels) {
        for (ElternPanel panel : panels) {
            medienverwaltung.addPanelList(panel);
        }
    }

    public void setKategorieObserver(ElternPanel... panels) {
        for (ElternPanel panel : panels) {
            kategorienverwaltung.addPanelList(panel);
        }
    }

    public void setHistoryObserver(ElternPanel... panels) {
        for (ElternPanel panel : panels) {
            historyverwaltung.addPanelList(panel);
        }
    }

    public void setGenreObserver(ElternPanel... panels) {
        for (ElternPanel panel : panels) {
            genreverwaltung.addPanelList(panel);
        }
    }

    public void setAusleiheObserver(ElternPanel... panels) {
        for (ElternPanel panel : panels) {
            ausleiheverwaltung.addPanelList(panel);
        }
    }

    public void setAccountObserver(ElternPanel... panels) {
        for (ElternPanel panel : panels) {
            accountverwaltung.addPanelList(panel);
        }
    }

    public void setLanguageObserver(ElternPanel... panels) {
        for (ElternPanel panel : panels) {
            languageverwaltung.addPanelList(panel);
        }
    }

    /**
     * Führt eine Sprachen Änderung durch.
     *
     * @param string Sprache, welche genutzt werden soll
     */
    public void changeLanguage(String string) {
        try {
            switch (string) {
                case "deutsch":
                    languageverwaltung.getDeutsch();
                    break;
                case "englisch":
                    languageverwaltung.getEnglisch();
                    break;
                default:
                    languageverwaltung.getDeutsch();
            }
        } catch (Exception e) {
            de.swt2bib.Logger.error(this, string);
        }
    }

    /**
     * Erzeugt ein Passwort Hash für ein Passwort.
     *
     * @param passwd Passwort welches verschlüsstelt werden soll
     * @return verschlüsseltes Passwort
     */
    private String generatePwHash(String passwd) {
        String ret = null;
        try {
            ret = this.passwd.getSHA512(passwd);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
}
