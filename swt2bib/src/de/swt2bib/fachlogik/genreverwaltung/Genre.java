package de.swt2bib.fachlogik.genreverwaltung;

/**
 *
 * @author TODO - Author
 */
public class Genre {

    private int id;
    private String bezeichnung;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Genre(int id, String bezeichnung) {
        this.id=id;
        this.bezeichnung = bezeichnung;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }
}
