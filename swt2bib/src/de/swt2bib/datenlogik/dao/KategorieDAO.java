package de.swt2bib.datenlogik.dao;

import de.swt2bib.datenlogik.Database;
import de.swt2bib.datenlogik.idao.IKategorieDAO;
import de.swt2bib.datenlogik.dto.Kategorie;
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
public class KategorieDAO extends ElternDAO implements IKategorieDAO {

    // Attribute
    private final Database db = new Database();
    private final Connection con = db.connect_mysql_schema();
    private ResultSet rs = null;

    /**
     * Läd die Datenbank Informationen von den Kategorien in eine Liste.
     *
     * @return Liste mit Kategorien
     * @throws IOException IO Fehler
     * @throws ConnectionError Datenbankverbindungsfehler
     */
    @Override
    public List<Kategorie> laden() throws IOException, ConnectionError {
        ArrayList<Kategorie> ret = new ArrayList<>();
        if (con != null) {
            try {
                String stmnt = db.getResultSQLStatement("kategorieMedien");
                PreparedStatement ptsm = con.prepareStatement(stmnt);
                rs = ptsm.executeQuery();
                while (rs.next()) {
                    Kategorie kategorie = new Kategorie(rs.getInt(1), rs.getString(2), rs.getString(3));
                    ret.add(kategorie);
                }
            } catch (SQLException ex) {
                System.err.println("KategorieDAO laden: " + ex);
            }
        } else {
            throw new ConnectionError();
        }
        return ret;
    }

    /**
     * Speichert neue Kategorien in die Datenbank.
     *
     * @param kategorieListe Liste der zu Speichernden Elemente
     * @throws IOException IO Fehler
     * @throws ConnectionError Datenbankverbindungsfehler
     */
    @Override
    public void speichern(List<Kategorie> kategorieListe) throws IOException, ConnectionError {
        if (con != null) {
            kategorieListe.forEach((kategorie) -> {
                try {
                    String stmnt = "INSERT INTO KategorieMedien(km_name, km_beschreibung) "
                            + "VALUES('" + kategorie.getName() + "', '" + kategorie.getBezeichnung() + "');";
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
