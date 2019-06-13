package de.swt2bib.datenlogik.dto;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class Medien implements Serializable {

    // Attribute
    private long kategorienId;
    private long genreId;
    private String name;
    private long id;
    private String isbn;
    private long barcodenummer;
    private int anzahl;
    private String author;
    private String desc;

    /**
     * Konstruktor für die Medien.
     *
     * @param isbn Angaben der ISBN Nummer
     * @param barcodenummer Angabe des Barcodes
     * @param genreId Angabe zum Genre
     * @param kategorienId Angabe zur Kategorie
     * @param name Titel
     * @param id Medien ID
     * @param anzahl Verfügbare Exemplare
     * @param author Autor vom Buch
     * @param desc Beschreibung zum Buch
     */
    public Medien(String isbn, long barcodenummer, long genreId, long kategorienId, String name, long id, int anzahl, String author, String desc) {
        this.kategorienId = kategorienId;
        this.name = name;
        this.id = id;
        this.genreId = genreId;
        this.barcodenummer = barcodenummer;
        this.isbn = isbn;
        this.anzahl = anzahl;
        this.author = author;
        this.desc = desc;
    }

    public void setKategorien(long kategorienId) {
        this.kategorienId = kategorienId;
    }

    public void setGenre(long genreId) {
        this.genreId = genreId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getKategorienId() {
        return kategorienId;
    }

    public long getGenreId() {
        return genreId;
    }

    public int getAnzahl() {
        return anzahl;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setBarcodenummer(long barcodenummer) {
        this.barcodenummer = barcodenummer;
    }

    public String getIsbn() {
        return isbn;
    }

    public long getBarcodenummer() {
        return barcodenummer;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
