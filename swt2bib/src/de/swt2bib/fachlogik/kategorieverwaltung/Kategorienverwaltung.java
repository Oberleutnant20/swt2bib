package de.swt2bib.fachlogik.kategorieverwaltung;

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

    private ArrayList<Kategorie> kategorieListe;
    private ArrayList<Kategorie> kategorieListeRef;
    private IKategorieDAO kategorieDAO;

    public Kategorienverwaltung(IKategorieDAO kategorieDAO) {
        kategorieListe = new ArrayList<Kategorie>();
        kategorieListeRef = new ArrayList<Kategorie>();
        this.kategorieDAO = kategorieDAO;
    }

    public void speichern() throws IOException, ConnectionError {
        List<Kategorie> liste = new ArrayList<>();
        if (kategorieListe.size() > kategorieListeRef.size()) {
            liste = kategorieListe.subList(kategorieListeRef.size(), kategorieListe.size());
        }
        kategorieDAO.speichern(liste);
    }

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
        }
    }

    public void add(Kategorie kategorie) {
        if (!kategorieListe.add(kategorie)) {
            String error = "Ausleihe gibt es bereits.";
        }
        notifyPanels();
    }

    public void delete(Kategorie kategorie) {
        if (!kategorieListe.remove(kategorie)) {
            String error = "Ausleihe gibt es nicht.";
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
