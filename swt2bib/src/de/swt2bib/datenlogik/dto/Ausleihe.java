package de.swt2bib.datenlogik.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author root
 */
public class Ausleihe implements Serializable {

    // Attribute
    private long id;
    private long medienid;
    private Date date;
    private int userid;
    private long kategorieid;

    /**
     * Konstruktor für Ausleihen.
     *
     * @param id Ausleihe ID
     * @param medienid Medien ID
     * @param date Datum
     * @param userid Nutzer ID
     * @param kategorieid Kategorie ID
     */
    public Ausleihe(long id, long medienid, Date date, int userid, long kategorieid) {
        this.id = id;
        this.medienid = medienid;
        this.date = date;
        this.userid = userid;
        this.kategorieid = kategorieid;
    }

    public long getId() {
        return id;
    }

    public long getMedienid() {
        return medienid;
    }

    public long getKategorieid() {
        return kategorieid;
    }

    public Date getDate() {
        return date;
    }

    public int getUserid() {
        return userid;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setMedienid(long medienid) {
        this.medienid = medienid;
    }

    public void setKategorieid(long kategorieid) {
        this.kategorieid = kategorieid;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
