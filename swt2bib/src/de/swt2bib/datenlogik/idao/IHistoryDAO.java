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
    List<History> laden() throws IOException, ConnectionError;
    void speichern(List<History> historyListe)  throws IOException, ConnectionError;
    //void update(List<History> historyListe) throws IOException, ConnectionError;
}

