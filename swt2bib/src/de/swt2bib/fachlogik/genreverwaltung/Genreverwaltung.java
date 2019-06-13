package de.swt2bib.fachlogik.genreverwaltung;

import de.swt2bib.datenlogik.dto.Genre;
import de.swt2bib.datenlogik.idao.IGenreDAO;
import de.swt2bib.fachlogik.ElternVerwaltung;
import de.swt2bib.info.exceptions.ConnectionError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author root
 */
public class Genreverwaltung extends ElternVerwaltung {

    // Attribut
    private ArrayList<Genre> genreListe;
    private ArrayList<Genre> genreListeRef;
    private IGenreDAO genreDAO;

    /**
     * Konstruktor für die Genreverwaltung.
     *
     * @param genreDAO Genre Datenabnkobjekt
     */
    public Genreverwaltung(IGenreDAO genreDAO) {
        genreListe = new ArrayList<>();
        genreListeRef = new ArrayList<>();
        this.genreDAO = genreDAO;
        laden();
    }

    /**
     * Speichert die Genre Liste ab.
     *
     * @throws IOException IO Fehler
     * @throws ConnectionError Datenbankverbidungsfehler
     */
    public void speichern() throws IOException, ConnectionError {
        List<Genre> liste = new ArrayList<>();
        if (genreListe.size() > genreListeRef.size()) {
            liste = genreListe.subList(genreListeRef.size(), genreListe.size());
        }
        genreDAO.speichern(liste);
    }

    /**
     * Läd die Genre Liste.
     */
    public void laden() {
        genreListe.clear();
        genreListeRef.clear();
        de.swt2bib.Logger.debug(this, "laden");
        try {
            List<Genre> liste = genreDAO.laden();
            liste.forEach((genre) -> {
                genreListe.add(genre);
                genreListeRef.add(genre);
            });
        } catch (Exception e) {
            de.swt2bib.Logger.error(this, "laden");
        }
    }

    /**
     * Fügt ein Genre Element der Liste hinzu.
     *
     * @param genre Neues Genre Element
     */
    public void add(Genre genre) {
        if (!genreListe.add(genre)) {
            de.swt2bib.Logger.error(this, "Genre gibt es bereits.");
        }
        notifyPanels();
    }

    /**
     * Löscht ein Genre Element.
     *
     * @param genre Genre, welches gelöscht werden soll
     */
    public void delete(Genre genre) {
        if (!genreListe.remove(genre)) {
            de.swt2bib.Logger.error(this, "Genre gibt es nicht.");
        } else {
            notifyPanels();
        }
    }

    public List<Genre> get() {
        ArrayList<Genre> liste = new ArrayList<>();
        genreListe.forEach((genre) -> {
            liste.add(genre);
        });
        return liste;
    }
}
