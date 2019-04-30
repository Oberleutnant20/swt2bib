package de.swt2bib.fachlogik.genreverwaltung;

import de.swt2bib.datenlogik.idao.IGenreDAO;
import de.swt2bib.info.exceptions.ConnectionError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author root
 */
public class Genreverwaltung {
    
    private ArrayList<Genre> genreListe;
    private ArrayList<Genre> genreListeRef;
    private IGenreDAO genreDAO;

    public Genreverwaltung(IGenreDAO genreDAO) {
        genreListe = new ArrayList<Genre>();
        genreListeRef = new ArrayList<Genre>();
        this.genreDAO = genreDAO;
    }

    public void speichern() throws IOException, ConnectionError {
        List<Genre> liste = new ArrayList<>();
        if(genreListe.size() > genreListeRef.size()){
            liste = genreListe.subList(genreListeRef.size(), genreListe.size());
        }
        genreDAO.speichern(liste);
    }

    public void laden() {
        genreListe.clear();
        genreListeRef.clear();
        try {
            List<Genre> liste = genreDAO.laden();
            liste.forEach((genre) -> {
                genreListe.add(genre);
                genreListeRef.add(genre);
            });
        } catch (Exception e) {
        }
    }

    public void add(Genre genre) {
        if (!genreListe.add(genre)) {
            String error = "Ausleihe gibt es bereits.";
        }
    }

    public void delete(Genre genre) {
        if (!genreListe.remove(genre)) {
            String error = "Ausleihe gibt es nicht.";
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
