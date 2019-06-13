package de.swt2bib.datenlogik.idao;

import de.swt2bib.datenlogik.dto.Medien;
import de.swt2bib.info.exceptions.ConnectionError;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Tim Lorse
 */
public interface IMedienDAO {
    public List<Medien> laden() throws IOException, ConnectionError;
    public void speichern(List<Medien> medienListe)  throws IOException, ConnectionError;
    public void update(List<Medien> medienListe) throws IOException, ConnectionError;
}
