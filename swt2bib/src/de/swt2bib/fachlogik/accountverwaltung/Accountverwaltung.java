package de.swt2bib.fachlogik.accountverwaltung;

import de.swt2bib.datenlogik.idao.IAccountDAO;
import de.swt2bib.info.exceptions.ConnectionError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author root
 */
public class Accountverwaltung {

    private ArrayList<Account> accountListe;
    private ArrayList<Account> accountListeUpdate;
    private ArrayList<Account> accountListeDelete;
    private ArrayList<Account> accountListeRef;
    private IAccountDAO accountDAO;

    public Accountverwaltung(IAccountDAO accountDAO) {
        accountListe = new ArrayList<Account>();
        accountListeRef = new ArrayList<Account>();
        accountListeUpdate = new ArrayList<Account>();
        accountListeDelete = new ArrayList<Account>();     
        this.accountDAO = accountDAO;
    }

    public void speichern() throws IOException, ConnectionError {
        List<Account> liste = new ArrayList<>();
        if (accountListe.size() > accountListeRef.size()) {
            liste = accountListe.subList(accountListeRef.size(), accountListe.size());
        }
        accountDAO.speichern(liste);
        accountDAO.update(accountListeUpdate);
    }

    public void laden() {
        accountListe.clear();
        accountListeRef.clear();
        try {
            List<Account> liste = accountDAO.laden();
            for (Account account : liste) {
                accountListe.add(account);
                accountListeRef.add(account);
            }
        } catch (Exception e) {
        }
    }

    public void add(Account account) {
        if (!accountListe.add(account)) {
            String error = "Account gibt es bereits.";
        }
    }

    public void delete(Account account) {
        if (!accountListeDelete.add(account)) {
            String error = "Account gibt es bereits.";
        }
        accountListe.remove(account);
    }

    public void update(Account account){
        if (!accountListeUpdate.add(account)) {
            String error = "Account gibt es bereits.";
        }
        accountListe.add(account);
    }
    
    public ArrayList<Account> get() {
        ArrayList<Account> liste = new ArrayList<Account>();
        for (Account account : accountListe) {
            liste.add(account);
        }
        return liste;
    }
}
