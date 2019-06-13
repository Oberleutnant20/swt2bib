package de.swt2bib.datenlogik.idao;

import de.swt2bib.datenlogik.dto.Kategorie;
import de.swt2bib.info.exceptions.ConnectionError;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Tim Lorse
 */
public interface IKategorieDAO {
    public List<Kategorie> laden() throws IOException, ConnectionError;
    public void speichern(List<Kategorie> kategorieListe)  throws IOException, ConnectionError;
}
