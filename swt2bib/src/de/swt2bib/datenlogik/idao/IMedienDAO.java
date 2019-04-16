package de.swt2bib.datenlogik.idao;


import de.swt2bib.fachlogik.medienverwaltung.Medien;
import de.swt2bib.info.exceptions.ConnectionError;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Tim Lorse
 */
public interface IMedienDAO {
    List<Medien> laden() throws IOException, ConnectionError;
    void speichern(List<Medien> medienListe)  throws IOException, ConnectionError;
    void update(List<Medien> medienListe) throws IOException, ConnectionError;
}
