package de.swt2bib.fachlogik.accountverwaltung;

import de.swt2bib.datenlogik.dto.Account;
import de.swt2bib.datenlogik.idao.IAccountDAO;
import de.swt2bib.fachlogik.ElternVerwaltung;
import de.swt2bib.info.exceptions.ConnectionError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author root
 */
public class Accountverwaltung extends ElternVerwaltung {

    // Attribute
    private ArrayList<Account> accountListe;
    private ArrayList<Account> accountListeUpdate;
    private ArrayList<Account> accountListeDelete;
    private ArrayList<Account> accountListeRef;
    private IAccountDAO accountDAO;

    /**
     * Konstruktor für die Accountverwaltung.
     *
     * @param accountDAO Datenbankobjekt
     */
    public Accountverwaltung(IAccountDAO accountDAO) {
        accountListe = new ArrayList<>();
        accountListeRef = new ArrayList<>();
        accountListeUpdate = new ArrayList<>();
        accountListeDelete = new ArrayList<>();
        this.accountDAO = accountDAO;
        laden();
    }

    /**
     * Speichert eine Account Liste ab.
     *
     * @throws IOException IO Fehler
     * @throws ConnectionError Datenbankverbindungsfehler
     */
    public void speichern() throws IOException, ConnectionError {
        List<Account> liste = new ArrayList<>();
        if (accountListe.size() > accountListeRef.size()) {
            liste = accountListe.subList(accountListeRef.size(), accountListe.size());
        }
        accountDAO.speichern(liste);
        accountDAO.update(accountListeUpdate);
    }

    /**
     * Läd die Account Liste.
     */
    public void laden() {
        accountListe.clear();
        accountListeRef.clear();
        de.swt2bib.Logger.debug(this, "laden");
        try {
            List<Account> liste = accountDAO.laden();
            for (Account account : liste) {
                accountListe.add(account);
                accountListeRef.add(account);
            }
        } catch (Exception e) {
        }
    }

    /**
     * Updatet einen Account Eintrag.
     *
     * @param account Account
     */
    public void update(Account account) {
        if (!accountListeUpdate.add(account)) {
            String error = "Account gibt es bereits.";
        }
        accountListe.add(account);
        notifyPanels();
    }

    /**
     * Löscht den übergebenen Account.
     *
     * @param account Account welcher gelöscht werden soll
     */
    public void delete(Account account) {
        if (!accountListeDelete.add(account)) {
            String error = "Account gibt es bereits.";
        } else {
            accountListe.remove(account);
            notifyPanels();
        }
    }

    /**
     * Fügt einen Account der Liste hinzu.
     *
     * @param account Neuer Account
     */
    public void add(Account account) {
        if (!accountListe.add(account)) {
            String error = "Ausleihe gibt es bereits.";
        }
        notifyPanels();
    }

    /**
     * Ruft die Account Liste auf.
     *
     * @return Account Liste
     */
    public ArrayList<Account> get() {
        ArrayList<Account> liste = new ArrayList<>();
        accountListe.forEach((account) -> {
            liste.add(account);
        });
        return liste;
    }
}
