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

    private Accountverwaltung accountverwaltung;
    private Medienverwaltung medienverwaltung;
    private Ausleiheverwaltung ausleiheverwaltung;
    private Kategorienverwaltung kategorienverwaltung;
    private Genreverwaltung genreverwaltung;
    private Historyverwaltung historyverwaltung;
    private PanelHandler panelHandler;
    private Account aktuellerUser;
    private ArrayList<History> historyListe;
    private ArrayList<Ausleihe> ausleiheListe;
    private Password passwd = new Password();

    public Controller(Accountverwaltung accountverwaltung, Medienverwaltung medienverwaltung, Ausleiheverwaltung ausleiheverwaltung, Kategorienverwaltung kategorienverwaltung, Genreverwaltung genreverwaltung, Historyverwaltung historyverwaltung) {
        this.accountverwaltung = accountverwaltung;
        this.medienverwaltung = medienverwaltung;
        this.ausleiheverwaltung = ausleiheverwaltung;
        this.kategorienverwaltung = kategorienverwaltung;
        this.genreverwaltung = genreverwaltung;
        this.historyverwaltung = historyverwaltung;
        start();
    }

    private void start() {
        de.swt2bib.Logger.info(this, "starten");
        initVerwaltungLaden();
        ausleihenPruefen();
        panelHandler = new PanelHandler(this, genreverwaltung.get(), kategorienverwaltung.get());
    }

    private void initVerwaltungLaden() {
        accountverwaltung.laden();
        medienverwaltung.laden();
        ausleiheverwaltung.laden();
        kategorienverwaltung.laden();
        genreverwaltung.laden();
        historyverwaltung.laden();
    }

    public void initUpdate() {
        de.swt2bib.Logger.info(this, "initUpdate");
        medienverwaltung.notifyPanels();
        ausleiheverwaltung.notifyPanels();
        kategorienverwaltung.notifyPanels();
        genreverwaltung.notifyPanels();
        historyverwaltung.notifyPanels();
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

    public boolean isMitarbeiter() {
        de.swt2bib.Logger.info(this, "isMitarbeiter");
        if (aktuellerUser != null) {
            return aktuellerUser.isMitarbeiter();
        }
        return false;
    }

    public void addHistory(History history) {
        de.swt2bib.Logger.info(this, "addHistory");
        historyverwaltung.add(history);
    }

    public void addAusleihe(Ausleihe ausleihe) {
        de.swt2bib.Logger.info(this, "addAusleihe");
        ausleiheverwaltung.add(ausleihe);
    }

    public void ladeUserDaten() {
        historyListe = ladeHistory();
        ausleiheListe = ladeAusleihe();
    }
    
    public Account matchingUser(String accountname, String passwort) {
        de.swt2bib.Logger.info(this, "matchingUser");
        List<Account> list = accountverwaltung.get();
        String compare = generatePwHash(passwort);
        for(int i = 0; i < list.size() ; i++){
            if(list.get(i).getPasswort().equals(compare)&&list.get(i).getUsername().equals(accountname)){
                return list.get(i);
            }
        }
        return null;
    }

    private ArrayList<History> ladeHistory() {
        int userid = aktuellerUser.getUserid();
        ArrayList<History> list = new ArrayList<History>();
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

    private ArrayList<Ausleihe> ladeAusleihe() {
        int userid = aktuellerUser.getUserid();
        ArrayList<Ausleihe> list = new ArrayList<Ausleihe>();
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

    public void saveAccountChange(Account a) {
        accountverwaltung.update(a);
    }

    public void saveMediumChange(Medien m) {
        de.swt2bib.Logger.info(this, "saveMediumChange");
        for (Medien medium : getAllMedien()) {
            if (medium.getId() == m.getId()) {
                medienverwaltung.delete(medium);
            }
        }

        medienverwaltung.update(m);
    }

    public void deleteAusleihe(Ausleihe a) {
        de.swt2bib.Logger.info(this, "deleteAusleihe");
        ausleiheverwaltung.delete(a);
    }

    public void saveAccount(Account account) {
        de.swt2bib.Logger.info(this, "saveAccount");
        accountverwaltung.add(account);
    }

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
        de.swt2bib.Logger.debug(this, "medien" + medienverwaltung.get().size() + "");
        return medienverwaltung.get();
    }

    public void saveDB() throws IOException, ConnectionError {
        de.swt2bib.Logger.info(this, "Speichern");
        accountverwaltung.speichern();
        medienverwaltung.speichern();
        ausleiheverwaltung.speichern();
        kategorienverwaltung.speichern();
        genreverwaltung.speichern();
        historyverwaltung.speichern();
    }

    private void ausleihenPruefen() {
        de.swt2bib.Logger.debug(this, "Ausleihe prüfen");
        ArrayList<Ausleihe> liste = ausleiheverwaltung.get();
        Date heute = new Date();
        int id = historyverwaltung.get().size();
        for (int i = 0; i < liste.size(); i++) {
            if (liste.get(i).getDate().before(heute)) {
                History history = new History(id++, liste.get(i).getUserid(), liste.get(i).getMedienid(), liste.get(i).getKategorieid());
                //historyListe.add(history);
                ausleiheverwaltung.delete(liste.get(i));
            }
            //System.out.println("ka");
        }
    }

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
    
    private String generatePwHash(String passwd){
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
