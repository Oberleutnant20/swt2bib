package de.swt2bib.datenlogik.dao;

import de.swt2bib.datenlogik.Database;
import de.swt2bib.datenlogik.dto.Genre;
import de.swt2bib.datenlogik.idao.IMedienDAO;
import de.swt2bib.datenlogik.dto.Kategorie;
import de.swt2bib.datenlogik.dto.Medien;
import de.swt2bib.info.exceptions.ConnectionError;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tim Lorse
 */
public class MedienDAO extends ElternDAO implements IMedienDAO {

    // Attribute
    private final Database db = new Database();
    private final Connection con = db.connect_mysql_schema();
    private ResultSet rs = null;
    private GenreDAO gen = new GenreDAO();
    private KategorieDAO kat = new KategorieDAO();

    /**
     * Läd die Datenbank Informationen von den Medien in eine Liste.
     *
     * @return Liste mit Medien Informationen.
     * @throws IOException IO Fehler
     * @throws ConnectionError Datenbankverbindungsfehler
     */
    @Override
    public List<Medien> laden() throws IOException, ConnectionError {
        ArrayList<Medien> ret = new ArrayList<>();
        if (con != null) {
            try {
                String stmnt = db.getResultSQLStatement("medien");
                PreparedStatement ptsm = con.prepareStatement(stmnt);
                rs = ptsm.executeQuery();
                List<Genre> genreListe = gen.laden();
                List<Kategorie> kategorieListe = kat.laden();
                while (rs.next()) {
                    String isbn = rs.getString("m_ISBN");
                    long barcode = rs.getLong("m_barcode");
                    String titel = rs.getString("m_Titel");
                    int id = rs.getInt("m_ID");
                    int anzahl = rs.getInt("m_Anzahl");
                    long kmid = rs.getInt("km_ID");
                    long gid = rs.getInt("g_ID");
                    String author = rs.getString("m_Author");
                    String desc = rs.getString("m_Beschreibung");

                    Medien medien = new Medien(isbn, barcode, gid, kmid, titel, id, anzahl, author, desc);
                    ret.add(medien);
                }
            } catch (SQLException ex) {
                System.err.println("MedienDAO laden: " + ex);
            }
        } else {
            throw new ConnectionError();
        }
        return ret;
    }

    /**
     * Speichert eine MedienListe in der Datenbank ab.
     *
     * @param medienListe Medien liste welche eingefügt werden soll
     * @throws IOException IO Fehler
     * @throws ConnectionError Datenbankverbindungsfehler
     */
    @Override
    public void speichern(List<Medien> medienListe) throws IOException, ConnectionError {
        if (con != null) {
            for (Medien medien : medienListe) {
                try {
                    String stmnt = "INSERT INTO Medien(m_Titel, m_Author, m_ISBN, m_Barcode, m_Anzahl, m_beschreibung, km_ID, g_ID) "
                            + "VALUES('" + medien.getName() + "','" + medien.getAuthor() + "','"
                            + medien.getIsbn() + "'," + medien.getBarcodenummer() + ", "
                            + medien.getAnzahl() + ", '" + medien.getDesc() + "', "
                            + medien.getKategorienId() + ", " + medien.getGenreId() + ");";
                    PreparedStatement ptsm = con.prepareStatement(stmnt);
                    ptsm.execute();
                } catch (SQLException ex) {
                    Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            throw new ConnectionError();
        }
    }

    /**
     * Updatet Medien Einträge.
     *
     * @param medienListe MedienListe mit den Einträgen, welche Upgedatet werden
     * sollen
     * @throws IOException IO Fehler
     * @throws ConnectionError Datenbankverbindungsfehler
     */
    @Override
    public void update(List<Medien> medienListe) throws IOException, ConnectionError {
        if (con != null) {
            for (Medien medien : medienListe) {
                try {
                    String name = medien.getName();
                    String isbn = medien.getIsbn();
                    long barcode = medien.getBarcodenummer();
                    int anzahl = medien.getAnzahl();
                    String author = medien.getAuthor();
                    String desc = medien.getDesc();
                    long gID = medien.getGenreId();
                    long mID = medien.getId();
                    long kID = medien.getKategorienId();
                    String stmnt = "UPDATE Medien SET m_Titel = '" + name
                            + "', m_Author = '" + author + "', m_ISBN = '" + isbn
                            + "', m_Barcode =" + barcode + ", m_Anzahl = " + anzahl
                            + ", m_beschreibung = '" + desc + "', km_ID = " + kID
                            + ", g_ID = " + gID + " WHERE m_ID LIKE " + mID + ";";
                    PreparedStatement ptsm = con.prepareStatement(stmnt);
                    ptsm.execute();
                } catch (SQLException ex) {
                    Logger.getLogger(MedienDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            throw new ConnectionError();
        }
    }
}
