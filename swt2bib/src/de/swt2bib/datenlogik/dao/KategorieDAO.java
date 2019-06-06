package de.swt2bib.datenlogik.dao;

import de.swt2bib.datenlogik.Database;
import de.swt2bib.datenlogik.idao.IKategorieDAO;
import de.swt2bib.fachlogik.kategorieverwaltung.Kategorie;
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

    private final Database db = new Database();
    private final Connection con = db.connect_mysql_schema();
    private ResultSet rs = null;

    @Override
    public List<Kategorie> laden() throws IOException, ConnectionError {
        ArrayList<Kategorie> ret = new ArrayList<>();
        if (con != null) {
            try {
                PreparedStatement ptsm = con.prepareStatement(db.getResultSQLStatement("kategorieMedien"));
                rs = ptsm.executeQuery();
                while (rs.next()) {
                    ret.add(new Kategorie(rs.getInt(1), rs.getString(2), rs.getString(3)));
                }
            } catch (SQLException ex) {
                System.err.println("KategorieDAO laden: " + ex);
            }
        } else {
            throw new ConnectionError();
        }
        return ret;
    }

    @Override
    public void speichern(List<Kategorie> kategorieListe) throws IOException, ConnectionError {
        if (con != null) {
            for (Kategorie kategorie : kategorieListe) {
                try {
                    PreparedStatement ptsm = con.prepareStatement("INSERT INTO KategorieMedien(km_name, km_beschreibung) "
                            + "VALUES('" + kategorie.getName() + "', '" + kategorie.getBezeichnung() + "');");
                    ptsm.execute();
                } catch (SQLException ex) {
                    Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            throw new ConnectionError();
        }
    }

}
