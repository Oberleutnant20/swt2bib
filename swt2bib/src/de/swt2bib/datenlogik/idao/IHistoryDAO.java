package de.swt2bib.datenlogik.idao;

import de.swt2bib.datenlogik.dto.History;
import de.swt2bib.info.exceptions.ConnectionError;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Tim Lorse
 */
public interface IHistoryDAO {
    public List<History> laden() throws IOException, ConnectionError;
    public void speichern(List<History> historyListe)  throws IOException, ConnectionError;
}

