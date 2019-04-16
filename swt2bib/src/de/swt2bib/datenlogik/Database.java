package de.swt2bib.datenlogik;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
//import java.util.logging.Level;
//import java.util.logging.Logger;

/**
 * Database Class with Connect, Disconnect, Resultset and Blobfile.
 *
 * @author Tim Lorse
 */
public class Database {

    //Attribute
    private final static String schema = "swt2bib";

    /**
     *
     * @return Datenbankschema
     */
    public static String getSchema() {
        return schema;
    }

    /**
     * Connection for Database HyperSQL(HSQLDB) on Localhost with Login SA.
     *
     * @return Connection if Successful
     */
    public Connection connect_hsqldb() {
        return connect_hsqldb("SA", "");
    }

    /**
     * Connection for Database MySQL on Localhost with Login root.
     *
     * @return Connection if Successful
     */
    public Connection connect_mysql() {
        return connect_mysql("root", "");
    }

    /**
     * Connection for Database MySQL on Localhost with Login root.
     *
     * @return Connection if Successful
     */
    public Connection connect_mysql_schema() {
        return connect_mysql("jdbc:mysql://localhost:3306/" + schema, "root", "");
    }

    /**
     * Connection for Database HyperSQL(HSQLDB) on Localhost without Login User.
     *
     * @param usr Username to Connect with the Database.
     * @param passwd Password to Authentication the User.
     * @return Connection if Successful
     */
    public Connection connect_hsqldb(String usr, String passwd) {
        return connect_hsqldb("jdbc:hsqldb:hsql://localhost/", usr, passwd);
    }

    /**
     * Connection for Database MySQL on Localhost without Login User.
     *
     * @param usr Username to Connect with the Database.
     * @param passwd Password to Authentication the User.
     * @return Connection if Successful
     */
    public Connection connect_mysql(String usr, String passwd) {
        return connect_mysql("jdbc:mysql://localhost:3306", usr, passwd);
    }

    /**
     * Connection for Database HyperSQL(HSQLDB) without Login User, Password and
     * URL
     *
     * @param url URL to the Database for Example<br>
     * <strong>HSQLDB: </strong>"jdbc:hsqldb:hsql://localhost/"
     * @param usr Username to Connect with the Database.
     * @param passwd Password to Authentication the User.
     * @return Connection if Successful
     */
    public Connection connect_hsqldb(String url, String usr, String passwd) {
        return connect(url, usr, passwd, "org.hsqldb.jdbcDriver");
    }

    /**
     * Connection for Database MySQL without Login User, Password and URL
     *
     * @param url URL to the Database for Example<br>
     * <strong>MySQL: </strong>"jdbc:mysql://localhost:3306"
     * @param usr Username to Connect with the Database.
     * @param passwd Password to Authentication the User.
     * @return Connection if Successful
     */
    public Connection connect_mysql(String url, String usr, String passwd) {
        return connect(url, usr, passwd, "com.mysql.jdbc.Driver");
    }

    /**
     * Connection without any User, Password or Driver.
     *
     * @param url URL to the Database for Example <br>
     * <strong>HSQLDB: </strong>"jdbc:hsqldb:hsql://localhost/"<br>
     * <strong>MySQL: </strong>"jdbc:mysql://localhost:3306"
     * @param usr Username to Connect with the Database.
     * @param passwd Password to Authentication the User.
     * @param driver Database driver. For Example<br>
     * <strong>HSQLDB: </strong>"org.hsqldb.jdbcDriver"<br>
     * <strong>MySQL: </strong>"com.mysql.jdbc.Driver"
     * @return Connection if Successful
     */
    public Connection connect(String url, String usr, String passwd, String driver) {
        Connection con = null;
        try {
            // Treiber Laden
            Class.forName(driver);
            con = DriverManager.getConnection(url, usr, passwd);
        } catch (ClassNotFoundException cnfe) {
            System.err.println("connect: Database Driver not found: " + cnfe);
        } catch (SQLException ex) {
            System.err.println("connect: " + ex);
        }
        return con;
    }

    /**
     * Disconnect the Database
     *
     * @param con Connection
     * @return True or False
     */
    public boolean disconnect(Connection con) {
        boolean ret = false;
        if (con != null) {
            try {
                if (!con.isClosed()) {
                    con.close();
                    ret = true;
                }
            } catch (SQLException ex) {
                System.err.println("Exception in disconnect: " + ex);
            }
        }
        return ret;
    }

    /**
     * Resultset from a complete Table
     *
     * @param con Connection
     * @param table Tablename
     * @return Result
     */
    public static ResultSet getResult_mysql(Connection con, String table) {
        ResultSet rs = null;
        try (PreparedStatement ptsm = con.prepareStatement("SELECT * FROM " + table + ";")) {
            rs = ptsm.executeQuery();
        } catch (SQLException ex) {
            System.err.println("getResult: " + ex);
        }
        return rs;
    }

    /**
     * Resultset from a complete Table
     *
     * @param con Connection
     * @param table Tablename
     * @return Result
     */
    public static ResultSet getResult(Connection con, String table) {
        ResultSet rs = null;
        try (PreparedStatement ptsm = con.prepareStatement("SELECT * FROM " + table)) {
            rs = ptsm.executeQuery();
        } catch (SQLException ex) {
            System.err.println("getResult: " + ex);
        }
        return rs;
    }

    /**
     * Resultset from a complete Table
     *
     * @param con Connection
     * @param table Tablename
     * @param attribut Attributname
     * @param value Value
     * @return Result
     */
    public static ResultSet getResult(Connection con, String table, String attribut, String value) {
        ResultSet rs = null;
        try (PreparedStatement ptsm = con.prepareStatement("SELECT * FROM " + table + " WHERE " + attribut + " LIKE " + value)) {
            rs = ptsm.executeQuery();
        } catch (SQLException ex) {
            System.err.println("getResult: " + ex);
        }
        return rs;
    }

    /**
     * Resultset from a complete Table
     *
     * @param con Connection
     * @param table Tablename
     * @param attribut Attributname
     * @param value Value
     * @return Result
     */
    public static ResultSet getResult_mysql(Connection con, String table, String attribut, String value) {
        ResultSet rs = null;
        try (PreparedStatement ptsm = con.prepareStatement("SELECT * FROM " + table + " WHERE " + attribut + " LIKE " + value + ";")) {
            rs = ptsm.executeQuery();
        } catch (SQLException ex) {
            System.err.println("getResult: " + ex);
        }
        return rs;
    }

    /**
     * Create an new Clob-Blob File
     *
     * @param fileName Filename
     * @param writerArg Writer
     * @return <strong>Clob File</strong>
     * @throws FileNotFoundException Filename not exists
     * @throws IOException failed or interrupted I/O operations
     */
    public static String readFile(String fileName, Writer writerArg) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String nextLine;
        StringBuilder sb = new StringBuilder();
        while ((nextLine = br.readLine()) != null) {
            //System.out.println("Writing: " + nextLine);
            writerArg.write(nextLine);
            sb.append(nextLine);
        }
        // Convert the content into to a string
        String clobData = sb.toString();
        // Return the data.
        return clobData;
    }

    /**
     *
     * @param rs ResultSet
     * @return
     */
    public static ResultSetMetaData getMetaData(ResultSet rs) {
        ResultSetMetaData ret = null;
        try {
            ret = rs.getMetaData();
        } catch (SQLException ex) {
            System.err.println("getMetadata: " + ex);;
        }
        return ret;
    }
    
    /**
     * SQL Statement fÃ¼r alles abrufen "Select * From Tabelle;"
     * @param table angabe der Tabelle
     * @return SQL Statement String
     */
    public static String getResultSQLStatement(String table) {
        return "SELECT * FROM " + table + ";";
    }

}

