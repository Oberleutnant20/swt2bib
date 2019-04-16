package de.swt2bib.datenlogik.idao;

import de.swt2bib.fachlogik.accountverwaltung.Account;
import de.swt2bib.info.exceptions.ConnectionError;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Tim Lorse
 */
public interface IAccountDAO {
    List<Account> laden() throws IOException, ConnectionError;
    void speichern(List<Account> accountListe)  throws IOException, ConnectionError;
    void update(List<Account> accountListe) throws IOException, ConnectionError;
}
