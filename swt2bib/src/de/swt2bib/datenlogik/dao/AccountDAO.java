package de.swt2bib.datenlogik.dao;

import de.swt2bib.datenlogik.Database;
import de.swt2bib.datenlogik.dto.Account;
import de.swt2bib.datenlogik.idao.IAccountDAO;
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
public class AccountDAO extends ElternDAO implements IAccountDAO {

    // Attribute
    private final Database db = new Database();
    private final Connection con = db.connect_mysql_schema();
    private ResultSet rs = null;

    /**
     * Läd die Datenbank Informationen von den Accounts in eine Liste.
     *
     * @return Liste von Accounts
     * @throws IOException IO Fehler
     * @throws ConnectionError Datenbankverbindungsfehler
     */
    @Override
    public List<Account> laden() throws IOException, ConnectionError {
        ArrayList<Account> ret = new ArrayList<>();
        if (con != null) {
            try {
                PreparedStatement ptsm = con.prepareStatement(db.getResultSQLStatement("user"));
                rs = ptsm.executeQuery();
                while (rs.next()) {
                    String login = rs.getString("u_login");
                    String passwd = rs.getString("u_passwd");
                    boolean mitarbeiter = rs.getBoolean("u_Mitarbeiter");
                    int id = rs.getInt("u_ID");
                    String vorname = rs.getString("u_Vorname");
                    String nachname = rs.getString("u_Nachname");
                    String strasse = rs.getString("u_Strasse");
                    String hausnummer = rs.getString("u_Hausnummer");
                    int plz = rs.getInt("u_PLZ");
                    String ort = rs.getString("u_ort");
                    Account account = new Account(login, passwd, mitarbeiter, id, vorname, nachname, plz, strasse, hausnummer, ort);
                    ret.add(account);
                }
            } catch (SQLException ex) {
                System.err.println("AccountDAO laden: " + ex);
            }
        } else {
            throw new ConnectionError();
        }
        return ret;
    }

    /**
     * Speichert eine Liste mit neuen Accounts in der Datenbank ab.
     *
     * @param accountListe Account liste mit neuen Accounts
     * @throws IOException IO Fehler
     * @throws ConnectionError Datenbankverbindungsfehler
     */
    @Override
    public void speichern(List<Account> accountListe) throws IOException, ConnectionError {
        if (con != null) {
            accountListe.forEach((account) -> {
                try {
                    String stmnt = "INSERT INTO USER(u_Vorname, u_Nachname, u_login, u_Passwd, u_Mitarbeiter, u_Strasse, u_Hausnummer, u_PLZ, u_Ort) "
                            + "VALUES('" + account.getVorname() + "','" + account.getNachname() + "','"
                            + account.getUsername() + "','" + account.getPasswort() + "', "
                            + account.isMitarbeiter() + ", '" + account.getStrasse() + "', '"
                            + account.getHausnummer() + "', " + account.getPlz() + ", '"
                            + account.getOrt() + "');";
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

    /**
     * Updatet eine bestimmte Liste an Accounts.
     *
     * @param accountListe Account Liste
     * @throws IOException IO Fehler
     * @throws ConnectionError Datenbankverbindungsfehler
     */
    @Override
    public void update(List<Account> accountListe) throws IOException, ConnectionError {
        if (con != null) {
            for (Account account : accountListe) {
                try {
                    String vorname = account.getVorname();
                    String nachname = account.getNachname();
                    String username = account.getUsername();
                    String passwd = account.getPasswort();
                    String str = account.getStrasse();
                    String hausnr = account.getHausnummer();
                    int plz = account.getPlz();
                    int id = account.getUserid();
                    String ort = account.getOrt();
                    boolean mitarbeiter = account.isMitarbeiter();
                    String stmnt = "UPDATE USER SET u_Vorname = '" + vorname + "', u_Nachname = '" + nachname
                            + "', u_login = '" + username + "', u_passwd = '" + passwd
                            + "', u_Mitarbeiter =" + mitarbeiter + ", u_Strasse = '" + str
                            + "', u_Hausnummer = '" + hausnr + "', u_PLZ = " + plz
                            + ", u_Ort = '" + ort + "' WHERE u_ID LIKE " + id + ";";
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
}
