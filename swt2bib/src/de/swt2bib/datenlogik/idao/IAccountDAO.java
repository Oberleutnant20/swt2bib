package de.swt2bib.datenlogik.idao;

import de.swt2bib.datenlogik.dto.Account;
import de.swt2bib.info.exceptions.ConnectionError;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Tim Lorse
 */
public interface IAccountDAO {
    public List<Account> laden() throws IOException, ConnectionError;
    public void speichern(List<Account> accountListe) throws IOException, ConnectionError;
    public void update(List<Account> accountListe) throws IOException, ConnectionError;
}
