package de.swt2bib.ui;

import de.swt2bib.fachlogik.accountverwaltung.Account;
import de.swt2bib.fachlogik.ausleihverwaltung.Ausleihe;
import de.swt2bib.fachlogik.Controller;
import de.swt2bib.fachlogik.genreverwaltung.Genre;
import de.swt2bib.fachlogik.historyverwaltung.History;
import de.swt2bib.fachlogik.kategorieverwaltung.Kategorie;
import de.swt2bib.fachlogik.medienverwaltung.Medien;
import de.swt2bib.ui.panels.AccountBearbeitenPanel;
import de.swt2bib.ui.panels.AccountsBearbeitenPanel;
import de.swt2bib.ui.panels.AusleihenBearbeitenPanel;
import de.swt2bib.ui.panels.AusleihenPanel;
import de.swt2bib.ui.panels.HistoryPanel;
import de.swt2bib.ui.panels.LoginPanel;
import de.swt2bib.ui.panels.OptionPanel;
import de.swt2bib.ui.panels.SelectPanel;
import de.swt2bib.ui.panels.SuchePanel;
import de.swt2bib.info.exceptions.ConnectionError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author root
 */
public class PanelHandler {
    private AusleihenBearbeitenPanel ausleihenBearbeitenPanel;
    private OptionPanel optionPanel;
    private AusleihenPanel ausleihenPanel;
    private HistoryPanel historyPanel;
    private AccountsBearbeitenPanel accountsBearbeitenPanel;
    private AccountBearbeitenPanel accountBearbeitenPanel;
    private LoginPanel loginPanel;
    private SelectPanel selectPanel;
    private SuchePanel suchePanel;
    private UI ui;
    
    private final Controller controller;
    private boolean eingeloggt;
    private boolean mitarbeiter;
    private Account aktuellerUser;
    private List<Genre> genreListe;
    private List<Kategorie> kategorieListe;

    public UI getUi() {
        return ui;
    }

    public List<Genre> getGenreListe() {
        return genreListe;
    }

    public List<Kategorie> getKategorieListe() {
        return kategorieListe;
    }

    public Account getAktuellerUser() {
        return aktuellerUser;
    }

    public SuchePanel getSuchePanel() {
        return suchePanel;
    }

    public AusleihenBearbeitenPanel getAusleihenBearbeitenPanel() {
        return ausleihenBearbeitenPanel;
    }

    public OptionPanel getOptionPanel() {
        return optionPanel;
    }

    public AusleihenPanel getAusleihenPanel() {
        return ausleihenPanel;
    }

    public HistoryPanel getHistoryPanel() {
        return historyPanel;
    }

    public AccountsBearbeitenPanel getAccountsBearbeitenPanel() {
        return accountsBearbeitenPanel;
    }

    public AccountBearbeitenPanel getAccountBearbeitenPanel() {
        return accountBearbeitenPanel;
    }

    public LoginPanel getLoginPanel() {
        return loginPanel;
    }

    public SelectPanel getSelectPanel() {
        return selectPanel;
    }

    public PanelHandler(Controller controller, List genreListe, List kategorieListe) {
        ui = new UI(genreListe, kategorieListe,this, false);
        this.genreListe = genreListe;
        this.kategorieListe = kategorieListe;
        initPanels();
        ui.add(suchePanel);
        ui.getjPanel1().setVisible(false);
        suchePanel.setMedienListe(controller.getAllMedien());
        suchePanel.fillTable();
        suchePanel.setVisible(true);
        this.controller = controller;
    }

    private void initPanels(){
        loginPanel = new LoginPanel(this);
        accountBearbeitenPanel = new AccountBearbeitenPanel(this);
        historyPanel = new HistoryPanel(this);
        accountsBearbeitenPanel = new AccountsBearbeitenPanel(this);
        ausleihenPanel = new AusleihenPanel(this);
        optionPanel = new OptionPanel(this);
        ausleihenBearbeitenPanel = new AusleihenBearbeitenPanel(this);
        selectPanel = new SelectPanel(this);
        suchePanel= new SuchePanel(this);
    }
    public void panelUnsichtbar() {
        selectPanel.setVisible(false);
        loginPanel.setVisible(false);
        suchePanel.setVisible(false); 
        accountBearbeitenPanel.setVisible(false);
        historyPanel.setVisible(false);
        accountsBearbeitenPanel.setVisible(false);
        ausleihenPanel.setVisible(false);
        optionPanel.setVisible(false);
        ausleihenBearbeitenPanel.setVisible(false);
    }

    public boolean login(String accountname, String passwort) {
        if(controller.setAktuellerUser(accountname, passwort)!=null){
            this.aktuellerUser =controller.setAktuellerUser(accountname, passwort);
            if(controller.isMitarbeiter()){
                ui.setMitarbeiterOnline();
            }
            ui.setUserOnline();
            ausleihenPanel.setUserAusleihe(controller.getAusleiheListe());
            historyPanel.setUserHistory(controller.getHistoryListe());
            return true;
        }
        return false;
    }

    public void saveAccountChange(int id, String hausnummer, String name, int plz, String ort, String strasse, String vorname, String passwort, boolean mitarbeiter, String accountname) {
        Account a = new Account(accountname,passwort, mitarbeiter, id, vorname, name, plz, strasse, hausnummer, ort);
        controller.saveAccountChange(a);
    }

    public void saveMediumChange(Medien m) {
        controller.saveMediumChange(m);
    }

    public void deleteAusleihe(Ausleihe a) {
        controller.deleteAusleihe(a);
    }

    public void saveAccount(int id, String hausnummer, String name, int plz, String ort, String strasse, String vorname, String passwort, boolean mitarbeiter, String accountname) {
        controller.saveAccount(new Account(accountname,passwort, mitarbeiter, id, vorname, name, plz, strasse, hausnummer, ort));
    }

    void loadUserAusleihe() {
        ausleihenPanel.setUserAusleihe(controller.getAusleiheListe());
    }

    void loadUserHistory() {
        historyPanel.setUserHistory(controller.getHistoryListe());
    }

    public ArrayList<Medien> returnMedien(){
      return controller.getAllMedien();  
    }
    
    public void loadAdminAccounts() {
        accountsBearbeitenPanel.setAccountListe(controller.getAllAccountsListe());
    }

    public void loadAusleihen() {
        ausleihenBearbeitenPanel.setAusleihenListe(controller.getAllAusleihenListe());
        ausleihenPanel.setAusleihenListe(controller.getAllAusleihenListe());
    }

    public Medien mapHistoryAndMedium(History selected) {
        Medien medium = null;
        ArrayList<Medien> liste = controller.getAllMedien();
        for (int i = 0; i < liste.size(); i++) {
            if(liste.get(i).getId()==selected.getMedienid())
                medium = liste.get(i);
        }
        return medium;
    }

    public void ausloggen() throws IOException, ConnectionError {
        controller.saveDB();
        ui.setUserOffline();
        aktuellerUser = null;
    }

    public void createNewAusleihe(long userid, Date date, long katid) {
       long id;
        try {
            id = controller.getAllAusleihenListe().get(controller.getAllAusleihenListe().size()).getId()+1;
        } catch (Exception e) {
            id=0;
        }
        Ausleihe a = new Ausleihe(id, userid, date, aktuellerUser.getUserid(), katid);
        controller.saveAusleihe(a);
    }
}
