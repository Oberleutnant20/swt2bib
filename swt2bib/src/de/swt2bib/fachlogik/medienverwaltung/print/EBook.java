package de.swt2bib.fachlogik.medienverwaltung.print;

import de.swt2bib.fachlogik.genreverwaltung.Genre;
import de.swt2bib.fachlogik.kategorieverwaltung.Kategorie;
import de.swt2bib.fachlogik.medienverwaltung.Medien;

/**
 *
 * @author TODO - Author
 */
public class EBook extends Medien{  
    public EBook(String isbn, int barcodenummer, Genre genre, Kategorie kategorien, String name, boolean ausgeliehen, boolean vorgemerkt, int id,int anzahl, String author, String beschreibung) {
        super(isbn, barcodenummer, genre, kategorien, name, ausgeliehen, vorgemerkt, id,anzahl, author, beschreibung);
    }
}
