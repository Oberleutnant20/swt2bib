package de.swt2bib.fachlogik.historyverwaltung;

/**
 *
 * @author TODO - Author
 */
public class History {
    long id;
    int userid;
    long medienid;
    long kategorieid;

    public History(long id, int userid, long medienid, long kategorieid) {
        this.id = id;
        this.userid = userid;
        this.medienid = medienid;
        this.kategorieid = kategorieid;
    }

    
    
    public long getKategorieid() {
        return kategorieid;
    }

    public long getId() {
        return id;
    }

    public int getUserid() {
        return userid;
    }

    public long getMedienid() {
        return medienid;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void setMedienid(long medienid) {
        this.medienid = medienid;
    }

    public void setKategorieid(long kategorieid) {
        this.kategorieid = kategorieid;
    }
    
}
