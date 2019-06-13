package de.swt2bib.datenlogik.dao;

import de.swt2bib.datenlogik.Database;
import de.swt2bib.datenlogik.idao.IGenreDAO;
import de.swt2bib.datenlogik.dto.Genre;
import de.swt2bib.info.exceptions.ConnectionError;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tim Lorse
 */
public class GenreDAO extends ElternDAO implements IGenreDAO {

    // Attribute
    private final Database db = new Database();
    private final Connection con = db.connect_mysql_schema();
    private ResultSet rs = null;

    /**
     * Läd die Datenbank Informationen von den Genres in eine Liste.
     *
     * @return Genre Liste
     * @throws IOException IO Fehler
     * @throws ConnectionError Datenbankverbindungsfehler
     */
    @Override
    public List<Genre> laden() throws IOException, ConnectionError {
        ArrayList<Genre> ret = new ArrayList<>();
        if (con != null) {
            try {
                String stmnt = db.getResultSQLStatement("genre");
                PreparedStatement ptsm = con.prepareStatement(stmnt);
                rs = ptsm.executeQuery();
                while (rs.next()) {
                    Genre genre = new Genre(rs.getInt(1), rs.getString(2));
                    ret.add(genre);
                }
            } catch (SQLException ex) {
                System.err.println("GenreDAO laden: " + ex);
            }
        } else {
            throw new ConnectionError();
        }
        return ret;
    }

    /**
     * Speichert Liste von Genres in der Datenbank ab.
     *
     * @param genreListe Liste mit Genres, welche noch nicht in der Datenbank
     * sind
     * @throws IOException IO Fehler
     * @throws ConnectionError Datenbankverbindungsfehler
     */
    @Override
    public void speichern(List<Genre> genreListe) throws IOException, ConnectionError {
        if (con != null) {
            genreListe.forEach((genre) -> {
                try {
                    String stmnt = "INSERT INTO Genre(g_Name) "
                            + "VALUES('" + genre.getBezeichnung() + "');";
                    PreparedStatement ptsm = con.prepareStatement(stmnt);
                    ptsm.execute();
                } catch (SQLException ex) {
                    Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } else {
            throw new ConnectionError();
        }
    }
}
