package de.swt2bib.fachlogik;

import de.swt2bib.fachlogik.accountverwaltung.Account;
import de.swt2bib.fachlogik.kategorieverwaltung.Kategorienverwaltung;
import de.swt2bib.fachlogik.accountverwaltung.Accountverwaltung;
import de.swt2bib.fachlogik.ausleihverwaltung.Ausleihe;
import de.swt2bib.fachlogik.ausleihverwaltung.Ausleiheverwaltung;
import de.swt2bib.fachlogik.crypt.Password;
import de.swt2bib.fachlogik.genreverwaltung.Genreverwaltung;
import de.swt2bib.fachlogik.historyverwaltung.History;
import de.swt2bib.fachlogik.historyverwaltung.Historyverwaltung;
import de.swt2bib.fachlogik.medienverwaltung.Medien;
import de.swt2bib.fachlogik.medienverwaltung.Medienverwaltung;
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
 * @author TODO - Author
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

    public Controller(Accountverwaltung accountverwaltung, Medienverwaltung medienverwaltung, Ausleiheverwaltung ausleiheverwaltung, Kategorienverwaltung kategorienverwaltung, Genreverwaltung genreverwaltung,Historyverwaltung historyverwaltung) {
        this.accountverwaltung = accountverwaltung;
        this.medienverwaltung = medienverwaltung;
        this.ausleiheverwaltung = ausleiheverwaltung;
        this.kategorienverwaltung = kategorienverwaltung;
        this.genreverwaltung = genreverwaltung;
        this.historyverwaltung = historyverwaltung;
        start();
    }
    
    private void start() {
        accountverwaltung.laden();
        medienverwaltung.laden();
        ausleiheverwaltung.laden();
        kategorienverwaltung.laden();
        genreverwaltung.laden();
        historyverwaltung.laden();
        ausleihenPruefen();
        panelHandler = new PanelHandler(this, genreverwaltung.get(),kategorienverwaltung.get());
    }
    
    public Account getAktuellerUser(){
        return aktuellerUser;
    }
    
    public Account setAktuellerUser(String accountname, String passwort){
        aktuellerUser = matchingUser(accountname, passwort);
        if(aktuellerUser!=null){
           ladeUserDaten();
           return aktuellerUser;
        }
        
        return null;
    }

    public boolean isMitarbeiter() {
        if(aktuellerUser!=null){
            return aktuellerUser.isMitarbeiter();
        }
        return false;
    }

    public void addHistory(History history){
        historyverwaltung.add(history);
    }
    
    public void addAusleihe(Ausleihe ausleihe){
        ausleiheverwaltung.add(ausleihe);
    }
    
    private void ladeUserDaten() {
        historyListe = ladeHistory();
        ausleiheListe = ladeAusleihe();
    }

    private Account matchingUser(String accountname, String passwort) {
        List<Account> list = accountverwaltung.get();
        for(int i = 0; i < list.size() ; i++){
            try {
                if(list.get(i).getPasswort().equals(passwd.getSHA512(passwort))&&list.get(i).getUsername().equals(accountname)){
                    return list.get(i);
                }
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    private ArrayList<History> ladeHistory() {
        int userid = aktuellerUser.getUserid();
        ArrayList<History> list = new ArrayList<History>();
        
        List<History> listegesamt = historyverwaltung.get();
        for(int i = 0; i < listegesamt.size() ; i++){
            if(listegesamt.get(i).getUserid()==userid){
                list.add(listegesamt.get(i));
            }
        }
        return list;
    }

    private ArrayList<Ausleihe> ladeAusleihe() {
        int userid = aktuellerUser.getUserid();
        ArrayList<Ausleihe> list = new ArrayList<Ausleihe>();
        
        List<Ausleihe> listegesamt = ausleiheverwaltung.get();
        for(int i = 0; i < listegesamt.size() ; i++){
            if(listegesamt.get(i).getUserid()==userid){
                list.add(listegesamt.get(i));
            }
        }
        return list;
    }

    public ArrayList<History> getHistoryListe() {
        return historyListe;
    }

    public ArrayList<Ausleihe> getAusleiheListe() {
        return ausleiheListe;
    }

    public void saveAccountChange(Account a) {
       accountverwaltung.update(a);
    }

    public void saveMediumChange(Medien m) {
       medienverwaltung.update(m);
    }

    public void deleteAusleihe(Ausleihe a) {
       ausleiheverwaltung.delete(a);
    }

    public void saveAccount(Account account) {
        accountverwaltung.add(account);
    }

    public void deleteHistory(History h) {
        historyverwaltung.delete(h);
    }

    public ArrayList<Ausleihe> getAllAusleihenListe() {
        return ausleiheverwaltung.get();
    }

    public ArrayList<Account> getAllAccountsListe() {
        return accountverwaltung.get();
    }

    public ArrayList<Medien> getAllMedien() {
        System.out.println("getAllMedien in Controller");
        return medienverwaltung.get();
    }

    public void saveDB() throws IOException, ConnectionError {
        accountverwaltung.speichern();
        medienverwaltung.speichern();
        ausleiheverwaltung.speichern();
        kategorienverwaltung.speichern();
        genreverwaltung.speichern();
        historyverwaltung.speichern();
    }

    private void ausleihenPruefen() {
        ArrayList<Ausleihe> liste = ausleiheverwaltung.get();
        Date heute = new Date();
        int id = historyverwaltung.get().size();
        for (int i = 0; i < liste.size(); i++) {
            if(liste.get(i).getDate().before(heute)){
                History history = new History(id++,liste.get(i).getUserid(),liste.get(i).getMedienid(), liste.get(i).getKategorieid());
                //historyListe.add(history);
                ausleiheverwaltung.delete(liste.get(i));
            }
        }
    }

    public void saveAusleihe(Ausleihe a) {
        ausleiheverwaltung.add(a);
    }
    
    public String generatePwHash(String passwd){
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
