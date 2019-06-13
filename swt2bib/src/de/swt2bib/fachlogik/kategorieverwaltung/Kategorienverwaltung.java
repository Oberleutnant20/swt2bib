package de.swt2bib.fachlogik.kategorieverwaltung;

import de.swt2bib.datenlogik.dto.Kategorie;
import de.swt2bib.datenlogik.idao.IKategorieDAO;
import de.swt2bib.fachlogik.ElternVerwaltung;
import de.swt2bib.info.exceptions.ConnectionError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author root
 */
public class Kategorienverwaltung extends ElternVerwaltung {

    // Attribute
    private ArrayList<Kategorie> kategorieListe;
    private ArrayList<Kategorie> kategorieListeRef;
    private IKategorieDAO kategorieDAO;

    /**
     * Konstruktor für die Kategorie Verwaltung.
     *
     * @param kategorieDAO Kategorie Datenbankobjekt
     */
    public Kategorienverwaltung(IKategorieDAO kategorieDAO) {
        kategorieListe = new ArrayList<>();
        kategorieListeRef = new ArrayList<>();
        this.kategorieDAO = kategorieDAO;
        laden();
    }

    /**
     * Speichert eine Liste von Kategorien ab.
     *
     * @throws IOException IO Fehler
     * @throws ConnectionError Datenbankverbindungsfehler
     */
    public void speichern() throws IOException, ConnectionError {
        List<Kategorie> liste = new ArrayList<>();
        if (kategorieListe.size() > kategorieListeRef.size()) {
            liste = kategorieListe.subList(kategorieListeRef.size(), kategorieListe.size());
        }
        kategorieDAO.speichern(liste);
    }

    /**
     * Läd eine Liste von Kategorien.
     */
    public void laden() {
        kategorieListe.clear();
        kategorieListeRef.clear();
        de.swt2bib.Logger.debug(this, "laden");
        try {
            List<Kategorie> liste = kategorieDAO.laden();
            liste.forEach((kategorie) -> {
                kategorieListe.add(kategorie);
                kategorieListeRef.add(kategorie);
            });
        } catch (Exception e) {
            de.swt2bib.Logger.error(this, "laden");
        }
    }

    /**
     * Fügt ein neues Kategorie Element in der Liste ein.
     *
     * @param kategorie neues Kategorie Element
     */
    public void add(Kategorie kategorie) {
        if (!kategorieListe.add(kategorie)) {
            de.swt2bib.Logger.error(this, "Kategorie gibt es bereits.");
        }
        notifyPanels();
    }

    /**
     * löscht ein Kategorie Element.
     *
     * @param kategorie Kategorie, welche gelöscht werden soll
     */
    public void delete(Kategorie kategorie) {
        if (!kategorieListe.remove(kategorie)) {
            de.swt2bib.Logger.error(this, "Kategorie gibt es nicht.");
        }
        notifyPanels();
    }

    public List<Kategorie> get() {
        ArrayList<Kategorie> liste = new ArrayList<>();
        kategorieListe.forEach((kategorie) -> {
            liste.add(kategorie);
        });
        return liste;
    }
}
