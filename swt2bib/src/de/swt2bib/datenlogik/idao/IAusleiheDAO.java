package de.swt2bib.datenlogik.idao;

import de.swt2bib.datenlogik.dto.Ausleihe;
import de.swt2bib.info.exceptions.ConnectionError;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Tim Lorse
 */
public interface IAusleiheDAO {
    public List<Ausleihe> laden() throws IOException, ConnectionError;
    public void speichern(List<Ausleihe> ausleiheListe)  throws IOException, ConnectionError;
    public void loeschen (List<Ausleihe> ausleiheListe) throws IOException, ConnectionError;
}

