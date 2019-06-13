package de.swt2bib.datenlogik.idao;

import de.swt2bib.datenlogik.dto.Genre;
import de.swt2bib.info.exceptions.ConnectionError;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Tim Lorse
 */
public interface IGenreDAO {
    public List<Genre> laden() throws IOException, ConnectionError;
    public void speichern(List<Genre> GenreListe)  throws IOException, ConnectionError;
}
