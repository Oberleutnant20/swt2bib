package de.swt2bib.fachlogik.historyverwaltung;

import de.swt2bib.datenlogik.dto.History;
import de.swt2bib.datenlogik.idao.IHistoryDAO;
import de.swt2bib.fachlogik.ElternVerwaltung;
import de.swt2bib.info.exceptions.ConnectionError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author root
 */
public class Historyverwaltung extends ElternVerwaltung {

    private ArrayList<History> historyListe;
    private ArrayList<History> historyListeRef;
    private IHistoryDAO historyDAO;

    public Historyverwaltung(IHistoryDAO historyDAO) {
        historyListe = new ArrayList<History>();
        historyListeRef = new ArrayList<History>();
        this.historyDAO = historyDAO;
    }

    public void speichern() throws IOException, ConnectionError {
        List<History> liste = new ArrayList<>();
        if (historyListe.size() > historyListeRef.size()) {
            liste = historyListe.subList(historyListeRef.size(), historyListe.size());
            System.out.println("hm");
        }
        historyDAO.speichern(liste);
    }

    public void laden() {
        historyListe.clear();
        de.swt2bib.Logger.debug(this, "laden");
        try {
            List<History> liste = historyDAO.laden();
            for (History history : liste) {
                historyListe.add(history);
                historyListeRef.add(history);
            }

        } catch (Exception e) {
        }
    }

    public void add(History history) {
        if (!historyListe.add(history)) {
            String error = "Ausleihe gibt es bereits.";
        }
        notifyPanels();
    }

    public void delete(History history) {
        if (!historyListe.remove(history)) {
            String error = "Ausleihe gibt es nicht.";
        }
        notifyPanels();
    }

    public List<History> get() {
        ArrayList<History> liste = new ArrayList<>();
        historyListe.forEach((history) -> {
            liste.add(history);
        });
        return liste;
    }
}
