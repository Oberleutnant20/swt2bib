package de.swt2bib.datenlogik.dto;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class Genre implements Serializable {

    // Attribute
    private int id;
    private String bezeichnung;

    /**
     * Konstruktor für die Genres.
     *
     * @param id Genre ID
     * @param bezeichnung Name des Genres
     */
    public Genre(int id, String bezeichnung) {
        this.id = id;
        this.bezeichnung = bezeichnung;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }
}
