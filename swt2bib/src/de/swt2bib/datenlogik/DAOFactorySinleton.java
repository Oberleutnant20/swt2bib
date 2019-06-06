package de.swt2bib.datenlogik;

import de.swt2bib.datenlogik.dao.AccountDAO;
import de.swt2bib.datenlogik.dao.AusleiheDAO;
import de.swt2bib.datenlogik.dao.ElternDAO;
import de.swt2bib.datenlogik.dao.GenreDAO;
import de.swt2bib.datenlogik.dao.HistoryDAO;
import de.swt2bib.datenlogik.dao.KategorieDAO;
import de.swt2bib.datenlogik.dao.MedienDAO;

/**
 *
 * @author root
 */
class DAOFactorySingleton {

    //Create an object of SingleObject
    private static DAOFactorySingleton instance = new DAOFactorySingleton();

    //make the constructor private so that this class cannot be
    //instantiated
    private DAOFactorySingleton() {
    }

    //Get the only object available
    public static DAOFactorySingleton getInstance() {
        return instance;
    }

    public ElternDAO getDAO(String art) {
        switch (art) {
            case "Account":
                return new AccountDAO();
            case "Medien":
                return new MedienDAO();
            case "Ausleihe":
                return new AusleiheDAO();
            case "Kategorien":
                return new KategorieDAO();
            case "Genre":
                return new GenreDAO();
            case "History":
                return new HistoryDAO();
            default:
                return null;
        }
    }
}
