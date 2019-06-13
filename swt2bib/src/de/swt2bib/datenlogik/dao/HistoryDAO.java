package de.swt2bib.datenlogik.dao;

import de.swt2bib.datenlogik.Database;
import de.swt2bib.datenlogik.idao.IHistoryDAO;
import de.swt2bib.datenlogik.dto.History;
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
public class HistoryDAO extends ElternDAO implements IHistoryDAO {

    // Attribute
    private final Database db = new Database();
    private final Connection con = db.connect_mysql_schema();
    private ResultSet rs = null;

    /**
     * Läd die Datenbank Informationen in eine Liste.
     *
     * @return Liste mit History Informationen
     * @throws IOException IO Fehler
     * @throws ConnectionError Datenbankverbindungsfehler
     */
    @Override
    public List<History> laden() throws IOException, ConnectionError {
        ArrayList<History> ret = new ArrayList<>();
        if (con != null) {
            try {
                String stmnt = db.getResultSQLStatement("History");
                PreparedStatement ptsm = con.prepareStatement(stmnt);
                rs = ptsm.executeQuery();
                int columnCount = db.getMetaData(rs).getColumnCount();
                while (rs.next()) {
                    History history = new History(rs.getLong(1), rs.getInt(2), rs.getInt(3), rs.getInt(4));
                    ret.add(history);
                }
            } catch (SQLException ex) {
                System.err.println("HistoryDAO laden: " + ex);
            }
        } else {
            throw new ConnectionError();
        }
        return ret;
    }

    /**
     * Speichert neue History Informationen in der Datenbank ab.
     *
     * @param historyListe History Liste mit neuen Informationen.
     * @throws IOException IO Fehler
     * @throws ConnectionError Datenbankverbindungsfehler
     */
    @Override
    public void speichern(List<History> historyListe) throws IOException, ConnectionError {
        if (con != null) {
            historyListe.forEach((history) -> {
                try {
                    history.getKategorieid();
                    history.getMedienid();
                    history.getUserid();
                    String stmnt = "INSERT INTO History(u_ID, km_ID, m_ID) "
                            + "VALUES(" + history.getUserid() + ", " + history.getKategorieid() + ", "
                            + history.getMedienid() + ");";
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
