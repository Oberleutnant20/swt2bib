package de.swt2bib.fachlogik.medienverwaltung;

import de.swt2bib.datenlogik.dto.Medien;
import de.swt2bib.datenlogik.idao.IMedienDAO;
import de.swt2bib.fachlogik.ElternVerwaltung;
import de.swt2bib.info.exceptions.ConnectionError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author root
 */
public class Medienverwaltung extends ElternVerwaltung {

    // Attribute
    private ArrayList<Medien> medienListe;
    private ArrayList<Medien> medienListeRef;
    private ArrayList<Medien> medienListeUpdate;
    private ArrayList<Medien> medienListeDelete;
    private IMedienDAO medienDAO;

    /**
     * Konstruktor für die Medienverwaltung.
     *
     * @param medienDAO Medien Datenbankobjekt
     */
    public Medienverwaltung(IMedienDAO medienDAO) {
        medienListe = new ArrayList<>();
        medienListeRef = new ArrayList<>();
        medienListeUpdate = new ArrayList<>();
        medienListeDelete = new ArrayList<>();
        this.medienDAO = medienDAO;
        laden();
    }

    /**
     * Speichert die Medien Liste ab.
     *
     * @throws IOException IO Fehler
     * @throws ConnectionError Datenbankverbindungsfehler
     */
    public void speichern() throws IOException, ConnectionError {
        List<Medien> liste = new ArrayList<>();
        if (medienListe.size() > medienListeRef.size()) {
            liste = medienListe.subList(medienListeRef.size(), medienListe.size());
        }
        medienDAO.speichern(liste);
        medienDAO.update(medienListeUpdate);
    }

    /**
     * Läd die Medien.
     */
    public void laden() {
        medienListe.clear();
        medienListeRef.clear();
        de.swt2bib.Logger.debug(this, "laden");
        try {
            List<Medien> liste = medienDAO.laden();
            for (Medien medium : liste) {
                medienListe.add(medium);
                medienListeRef.add(medium);
            }
        } catch (Exception e) {
            de.swt2bib.Logger.error(this, "laden");
        }
    }

    /**
     * Fügt der Medienliste neue Elemente hinzu.
     *
     * @param medium Medium, welches hinzugefügt werden soll
     */
    public void add(Medien medium) {
        if (!medienListe.add(medium)) {
            de.swt2bib.Logger.error(this, "Medium gibt es bereits.");
        }
        notifyPanels();
    }

    /**
     * Löscht ein Medien Element.
     *
     * @param medien Medium, welches gelöscht werden soll
     */
    public void delete(Medien medien) {
        if (!medienListe.remove(medien)) {
            de.swt2bib.Logger.error(this, "Medium gibt es nicht.");
        }
        notifyPanels();
    }

    /**
     * Updatet ein Medien Element.
     *
     * @param medien Medium, welches upgedatet werden soll
     */
    public void update(Medien medien) {
        if (!medienListe.add(medien)) {
            de.swt2bib.Logger.error(this, "Medium gibt es bereits.");
        }
        medienListeUpdate.add(medien);
        notifyPanels();
    }

    public ArrayList<Medien> get() {
        ArrayList<Medien> liste = new ArrayList<>();
        for (Medien medien : medienListe) {
            liste.add(medien);
            de.swt2bib.Logger.debug(this, medien.getName());
        }
        return liste;
    }
}
