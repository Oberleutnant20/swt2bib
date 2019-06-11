package de.swt2bib.datenlogik;

import de.swt2bib.datenlogik.dao.AccountDAO;
import de.swt2bib.datenlogik.dao.AusleiheDAO;
import de.swt2bib.datenlogik.dao.GenreDAO;
import de.swt2bib.datenlogik.dao.HistoryDAO;
import de.swt2bib.datenlogik.dao.KategorieDAO;
import de.swt2bib.datenlogik.dao.MedienDAO;
import de.swt2bib.fachlogik.accountverwaltung.Accountverwaltung;
import de.swt2bib.fachlogik.ausleihverwaltung.Ausleiheverwaltung;
import de.swt2bib.fachlogik.ElternVerwaltung;
import de.swt2bib.fachlogik.genreverwaltung.Genreverwaltung;
import de.swt2bib.fachlogik.historyverwaltung.Historyverwaltung;
import de.swt2bib.fachlogik.kategorieverwaltung.Kategorienverwaltung;
import de.swt2bib.fachlogik.medienverwaltung.Medienverwaltung;

/**
 *
 * @author root
 */
public class VerwaltungsFactorySingleton {
    //Create an object of SingleObject
   private static VerwaltungsFactorySingleton instance = new VerwaltungsFactorySingleton();

   //make the constructor private so that this class cannot be
   //instantiated
   private VerwaltungsFactorySingleton(){}

   //Get the only object available
   public static VerwaltungsFactorySingleton getInstance(){
      return instance;
   }

   public ElternVerwaltung getVerwaltung(String art){
      DAOFactorySingleton singleton = DAOFactorySingleton.getInstance();
       switch (art){
          case "Account" :
              AccountDAO accountdao = (AccountDAO)singleton.getDAO("Account");
              return new Accountverwaltung(accountdao);
          case "Medien" :
              MedienDAO mediendao = (MedienDAO)singleton.getDAO("Medien");
              return new Medienverwaltung(mediendao);
          case "Ausleihe" :
              AusleiheDAO ausleihedao = (AusleiheDAO)singleton.getDAO("Ausleihe");
              return new Ausleiheverwaltung(ausleihedao);
          case "Kategorien" :
              KategorieDAO kategoriedao = (KategorieDAO)singleton.getDAO("Kategorien");
              return new Kategorienverwaltung(kategoriedao);
          case "Genre" :
              GenreDAO genredao = (GenreDAO)singleton.getDAO("Genre");
              return new Genreverwaltung(genredao);
          case "History" :
              HistoryDAO historydao = (HistoryDAO)singleton.getDAO("History");
              return new Historyverwaltung(historydao);
          default:return null;
      }
   }
}
