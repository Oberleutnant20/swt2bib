package de.swt2bib.fachlogik.ausleihverwaltung;

import de.swt2bib.datenlogik.dto.Ausleihe;
import de.swt2bib.datenlogik.idao.IAusleiheDAO;
import de.swt2bib.fachlogik.ElternVerwaltung;
import de.swt2bib.info.exceptions.ConnectionError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author root
 */
public class Ausleiheverwaltung extends ElternVerwaltung {

    private ArrayList<Ausleihe> ausleiheListe;
    private ArrayList<Ausleihe> ausleiheListeRef;
    private ArrayList<Ausleihe> ausleiheListeUpdate;
    private ArrayList<Ausleihe> ausleiheListeDelete;
    private IAusleiheDAO ausleiheDAO;

    public Ausleiheverwaltung(IAusleiheDAO ausleiheDAO) {
        ausleiheListe = new ArrayList<Ausleihe>();
        ausleiheListeRef = new ArrayList<Ausleihe>();
        ausleiheListeUpdate = new ArrayList<Ausleihe>();
        ausleiheListeDelete = new ArrayList<Ausleihe>();
        this.ausleiheDAO = ausleiheDAO;
    }

    public void speichern() throws IOException, ConnectionError {
        List<Ausleihe> liste = new ArrayList<>();
        if (ausleiheListe.size() > ausleiheListeRef.size()) {
            liste = ausleiheListe.subList(ausleiheListeRef.size(), ausleiheListe.size());
        }
        ausleiheDAO.speichern(liste);
        ausleiheDAO.loeschen(ausleiheListeDelete);
    }

    public void laden() {
        ausleiheListe.clear();
        ausleiheListeRef.clear();
        de.swt2bib.Logger.debug(this, "laden");
        try {
            List<Ausleihe> liste = ausleiheDAO.laden();
            for (Ausleihe ausleihe : liste) {
                ausleiheListe.add(ausleihe);
                ausleiheListeRef.add(ausleihe);
            }

        } catch (Exception e) {
            de.swt2bib.Logger.error(this, "laden Error");
        }
    }

    public void add(Ausleihe ausleihe) {
        if (!ausleiheListe.add(ausleihe)) {
            String error = "Ausleihe gibt es bereits.";
        }
        notifyPanels();
    }

    public void delete(Ausleihe ausleihe) {
        if (!ausleiheListeDelete.add(ausleihe)) {
            String error = "Ausleihe gibt es nicht.";
        } else {
            ausleiheListe.remove(ausleihe);
            notifyPanels();
        }
    }

    public void update(Ausleihe ausleihe) {
        if (!ausleiheListeUpdate.add(ausleihe)) {
            String error = "Ausleihe gibt es nicht.";
        }
        notifyPanels();
    }

    public ArrayList<Ausleihe> get() {
        ArrayList<Ausleihe> liste = new ArrayList<Ausleihe>();
        for (Ausleihe ausleihe : ausleiheListe) {
            liste.add(ausleihe);
        }
        return liste;
    }
}
