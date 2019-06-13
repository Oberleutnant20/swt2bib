package de.swt2bib;

import de.swt2bib.datenlogik.VerwaltungsFactorySingleton;
import de.swt2bib.fachlogik.accountverwaltung.Accountverwaltung;
import de.swt2bib.fachlogik.ausleihverwaltung.Ausleiheverwaltung;
import de.swt2bib.fachlogik.Controller;
import de.swt2bib.fachlogik.genreverwaltung.Genreverwaltung;
import de.swt2bib.fachlogik.historyverwaltung.Historyverwaltung;
import de.swt2bib.fachlogik.kategorieverwaltung.Kategorienverwaltung;
import de.swt2bib.fachlogik.languageverwaltung.Languageverwaltung;
import de.swt2bib.fachlogik.medienverwaltung.Medienverwaltung;
import de.swt2bib.ui.PanelHandler;

/**
 *
 * @author root
 */
public class Main {

    /**
     * Ausführende Main Methode.
     * 1. Logger einstellen
     * 2. Verwaltungen anlegen
     * 3. Controller und PanelHandler starten
     * @param args Not Used
     */
    public static void main(String[] args) {
        Logger.enableDebug(true);
        Logger.enableWarnung(true);
        VerwaltungsFactorySingleton singleton;
        singleton = VerwaltungsFactorySingleton.getInstance();
        Languageverwaltung languageverwaltung;
        languageverwaltung = (Languageverwaltung) singleton.getVerwaltung("Language");
        Accountverwaltung accountverwaltung;
        accountverwaltung = (Accountverwaltung) singleton.getVerwaltung("Account");
        Medienverwaltung medienverwaltung;
        medienverwaltung = (Medienverwaltung) singleton.getVerwaltung("Medien");
        Ausleiheverwaltung ausleiheverwaltung;
        ausleiheverwaltung = (Ausleiheverwaltung) singleton.getVerwaltung("Ausleihe");
        Kategorienverwaltung kategorienverwaltung;
        kategorienverwaltung = (Kategorienverwaltung) singleton.getVerwaltung("Kategorien");
        Genreverwaltung genreverwaltung;
        genreverwaltung = (Genreverwaltung) singleton.getVerwaltung("Genre");
        Historyverwaltung historyverwaltung;
        historyverwaltung = (Historyverwaltung) singleton.getVerwaltung("History");
        Controller controller;
        controller = new Controller(languageverwaltung, accountverwaltung, medienverwaltung, ausleiheverwaltung, kategorienverwaltung, genreverwaltung, historyverwaltung);
        PanelHandler panelHandler;
        panelHandler = new PanelHandler(controller, genreverwaltung.get(), kategorienverwaltung.get());
        controller.start(panelHandler);
        panelHandler.start();
    }
}
