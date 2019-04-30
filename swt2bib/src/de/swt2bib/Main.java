package de.swt2bib;

import de.swt2bib.datenlogik.dao.AccountDAO;
import de.swt2bib.datenlogik.dao.AusleiheDAO;
import de.swt2bib.datenlogik.dao.GenreDAO;
import de.swt2bib.datenlogik.dao.HistoryDAO;
import de.swt2bib.datenlogik.dao.KategorieDAO;
import de.swt2bib.datenlogik.dao.MedienDAO;
import de.swt2bib.fachlogik.accountverwaltung.Accountverwaltung;
import de.swt2bib.fachlogik.ausleihverwaltung.Ausleiheverwaltung;
import de.swt2bib.fachlogik.Controller;
import de.swt2bib.fachlogik.genreverwaltung.Genreverwaltung;
import de.swt2bib.fachlogik.historyverwaltung.Historyverwaltung;
import de.swt2bib.fachlogik.kategorieverwaltung.Kategorienverwaltung;
import de.swt2bib.fachlogik.medienverwaltung.Medienverwaltung;

/**
 *
 * @author TODO - Author
 */
public class Main {
    public static void main(String[] args) {
        Accountverwaltung accountverwaltung = new Accountverwaltung(new AccountDAO());
        Medienverwaltung medienverwaltung = new Medienverwaltung(new MedienDAO());
        Ausleiheverwaltung ausleiheverwaltung = new Ausleiheverwaltung(new AusleiheDAO());
        Kategorienverwaltung kategorienverwaltung = new Kategorienverwaltung(new KategorieDAO());
        Genreverwaltung genreverwaltung = new Genreverwaltung(new GenreDAO());
        Historyverwaltung historyverwaltung = new Historyverwaltung(new HistoryDAO());
        
        Controller controller = new Controller(accountverwaltung, medienverwaltung,ausleiheverwaltung, kategorienverwaltung,genreverwaltung, historyverwaltung);
    }
}
