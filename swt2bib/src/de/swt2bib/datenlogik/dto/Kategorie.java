package de.swt2bib.datenlogik.dto;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class Kategorie implements Serializable {

    private long id;
    private String name;
    private String bezeichnung;

    public Kategorie(long id, String name, String bezeichnung) {
        this.id = id;
        this.name = name;
        this.bezeichnung = bezeichnung;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
