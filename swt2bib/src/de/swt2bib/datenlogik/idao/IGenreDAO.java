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
    List<Genre> laden() throws IOException, ConnectionError;
    void speichern(List<Genre> GenreListe)  throws IOException, ConnectionError;
    //void update(List<Genre> GenreListe) throws IOException, ConnectionError;
}
