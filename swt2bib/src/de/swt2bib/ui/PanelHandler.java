package de.swt2bib.ui;

import de.swt2bib.datenlogik.dto.Account;
import de.swt2bib.datenlogik.dto.Ausleihe;
import de.swt2bib.datenlogik.dto.Genre;
import de.swt2bib.datenlogik.dto.History;
import de.swt2bib.datenlogik.dto.Kategorie;
import de.swt2bib.datenlogik.dto.Medien;
import de.swt2bib.fachlogik.Controller;
import de.swt2bib.Logger;
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

    public PanelHandler(Controller controller, List<Genre> genreListe, List<Kategorie> kategorieListe) {
        ui = new UI(genreListe, kategorieListe, this);
        this.controller = controller;
        this.genreListe = genreListe;
        this.kategorieListe = kategorieListe;
        initPanels();
        ui.add(suchePanel);
        ui.getjPanel1().setVisible(false);
        initObsever();
        suchePanel.update();
        suchePanel.setVisible(true);
    }

    private void initObsever() {
        de.swt2bib.Logger.debug(this, "initObsever");
        controller.setAccountObserver(accountBearbeitenPanel, accountsBearbeitenPanel);
        controller.setAusleiheObserver(ausleihenPanel, ausleihenBearbeitenPanel);
        controller.setGenreObserver(selectPanel, suchePanel);
        controller.setHistoryObserver(historyPanel);
        controller.setKategorieObserver(selectPanel, suchePanel);
        controller.setMedienObserver(ausleihenPanel, ausleihenBearbeitenPanel, selectPanel, suchePanel);

    }

    private void initPanels() {
        de.swt2bib.Logger.debug(this, "initPanels");
        loginPanel = new LoginPanel(this);
        accountBearbeitenPanel = new AccountBearbeitenPanel(this);
        historyPanel = new HistoryPanel(this);
        accountsBearbeitenPanel = new AccountsBearbeitenPanel(this);
        ausleihenPanel = new AusleihenPanel(this);
        optionPanel = new OptionPanel(this);
        ausleihenBearbeitenPanel = new AusleihenBearbeitenPanel(this);
        selectPanel = new SelectPanel(this);
        suchePanel = new SuchePanel(this);
    }

    public void panelUnsichtbar() {
        de.swt2bib.Logger.debug(this, "panelUnsichtbar");
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
        de.swt2bib.Logger.debug(this, "login");
        if (controller.setAktuellerUser(accountname, passwort) != null) {
            this.aktuellerUser = controller.setAktuellerUser(accountname, passwort);
            if (controller.isMitarbeiter()) {
                ui.setMitarbeiterOnline();
            }
            ui.setUserOnline();
            controller.ladeUserDaten();
            controller.initUpdate();
            return true;
        }
        return false;
    }

    public void saveAccountChange(int id, String hausnummer, String name, int plz, String ort, String strasse, String vorname, String passwort, boolean mitarbeiter, String accountname) {
        Account a = new Account(accountname, passwort, mitarbeiter, id, vorname, name, plz, strasse, hausnummer, ort);
        controller.saveAccountChange(a);
    }

    public void saveMediumChange(Medien m) {
        controller.saveMediumChange(m);
    }

    public void deleteAusleihe(Ausleihe a) {
        controller.deleteAusleihe(a);
    }

    public void saveAccount(int id, String hausnummer, String name, int plz, String ort, String strasse, String vorname, String passwort, boolean mitarbeiter, String accountname) {
        controller.saveAccount(new Account(accountname, passwort, mitarbeiter, id, vorname, name, plz, strasse, hausnummer, ort));
    }

    public List<Ausleihe> getAusleihe() {
        return controller.getAllAusleihenListe();
    }

    public List<History> getHistory() {
        return controller.getHistoryListe();
    }

    public List<Medien> returnMedien() {
        return controller.getAllMedien();
    }

    public void loadAdminAccounts() {
        accountsBearbeitenPanel.setAccountListe(controller.getAllAccountsListe());
    }

    public void loadAusleihen() {
        ausleihenBearbeitenPanel.setAusleihenListe(controller.getAllAusleihenListe());
    }

    public Medien mapHistoryAndMedium(History selected) {
        Medien medium = null;
        List<Medien> liste = controller.getAllMedien();
        for (int i = 0; i < liste.size(); i++) {
            if (liste.get(i).getId() == selected.getMedienid()) {
                medium = liste.get(i);
            }
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
            id = controller.getAllAusleihenListe().get(controller.getAllAusleihenListe().size() - 1).getId() + 1;
        } catch (Exception e) {
            id = 0;
        }
        Ausleihe a = new Ausleihe(id, userid, date, aktuellerUser.getUserid(), katid);
        controller.saveAusleihe(a);
    }

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

    public String getKatBezeichnung(long kategorienId) {
        List<Kategorie> kat = getKategorieListe();
        String bez = "";
        for (Kategorie kategorie : kat) {
            if (kategorie.getId() == kategorienId) {
                return kategorie.getBezeichnung();
            }
        }
        return bez;
    }

    public String getGenBezeichnung(long genreId) {
        List<Genre> gen = getGenreListe();
        String bez = "";
        for (Genre genre : gen) {
            if (genre.getId() == genreId) {
                return genre.getBezeichnung();
            }
        }
        return bez;
    }

    public int getVerfuegbare(int medienId) {
        int verfuegbare = 0;

        for (Medien medien : controller.getAllMedien()) {
            if (medien.getId() == medienId) {
                verfuegbare = medien.getAnzahl();
                Logger.info(this, "gefunden");
            }
        }
        Logger.info(this, verfuegbare + "verfügbare gefunden");
        for (Ausleihe ausleihe : controller.getAllAusleihenListe()) {
            if (ausleihe.getMedienid() == medienId) {
                verfuegbare--;
            }
        }
        return verfuegbare;
    }

    public List<Medien> getMedienliste() {
        Logger.info(this, "Medienliste gezogen");
        return controller.getAllMedien();
    }
}
