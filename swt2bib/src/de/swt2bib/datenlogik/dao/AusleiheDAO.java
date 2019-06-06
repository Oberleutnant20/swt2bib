package de.swt2bib.datenlogik.dao;

import de.swt2bib.datenlogik.Database;
import de.swt2bib.datenlogik.idao.IAusleiheDAO;
import de.swt2bib.fachlogik.ausleihverwaltung.Ausleihe;
import de.swt2bib.info.exceptions.ConnectionError;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tim Lorse
 */
public class AusleiheDAO extends ElternDAO implements IAusleiheDAO {

    private final Database db = new Database();
    private final Connection con = db.connect_mysql_schema();
    private ResultSet rs = null;

    @Override
    public List<Ausleihe> laden() throws IOException, ConnectionError {
        ArrayList<Ausleihe> ret = new ArrayList<>();
        if (con != null) {
            try {
                PreparedStatement ptsm = con.prepareStatement("SELECT a_ID, a_Date, u_ID, m_ID, km_ID FROM ausleihe;");
                rs = ptsm.executeQuery();
                while (rs.next()) {
                    long id = rs.getLong("a_ID");
                    long medienID = rs.getLong("m_ID");
                    Date datum = rs.getDate("a_Date");
                    int uID = rs.getInt("u_ID");
                    long kID = rs.getLong("km_ID");
                    ret.add(new Ausleihe(id, medienID, datum, uID, kID));
                }
            } catch (SQLException ex) {
                System.err.println("AusleiheDAO laden: " + ex);
            }
        } else {
            throw new ConnectionError();
        }
        return ret;
    }

    @Override
    public void speichern(List<Ausleihe> ausleiheListe) throws IOException, ConnectionError {
        if (con != null) {
            for (Ausleihe ausleihe : ausleiheListe) {
                try {
                    String pattern = "YYYY-MM-DD";
                    String mysqlDateString = new SimpleDateFormat(pattern).format(ausleihe.getDate());
                    PreparedStatement ptsm = con.prepareStatement("INSERT INTO Ausleihe(a_DATE, u_ID, m_id, km_id) "
                            + "VALUES('" + mysqlDateString + "', " + ausleihe.getUserid() + ", " + ausleihe.getMedienid() + ", " + ausleihe.getKategorieid() + ");");

                    ptsm.execute();
                } catch (SQLException ex) {
                    Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            throw new ConnectionError();
        }
    }

    @Override
    public void loeschen(List<Ausleihe> ausleiheListe) throws IOException, ConnectionError {
        if(con != null){
            for (Ausleihe ausleihe : ausleiheListe) {
                try {
                    PreparedStatement ptsm = con.prepareStatement("DELETE FROM Ausleihe WHERE a_ID LIKE "+ausleihe.getId());
                    ptsm.execute();
                } catch (SQLException ex) {
                    Logger.getLogger(AusleiheDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        } else {
            throw new ConnectionError();
        }
    }
}
