package de.swt2bib;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

/**
 *
 * @author root
 */
public class Logger {

    private static final Logger instance = new Logger();
    private static boolean debug = false;
    private static boolean warnung = false;
    private static File f;
    private static Writer writer;
    
    /**
     * Konstruktor der Klasse ist privat. Damit nicht weitere Instancen erstellt
     * werden können. Außerdem, wird hier das Log-File erstellt.
     */
    private Logger() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        f = new File(strDate + ".log");
        if (f.exists()) {
            f.delete();
        }
        try {
            f.createNewFile();
            writer = new BufferedWriter(new FileWriter(f, true));
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(Logger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @return eine Instanz von der Klasse Logger.
     */
    public static Logger getInstance() {
        return instance;
    }

    /**
     * Loggt eine Debug Ausgabe.
     *
     * @param b true oder false
     */
    public static void enableDebug(boolean b) {
        debug = b;
    }

    /**
     * Loggt <strong>Warnmeldungen</strong>.
     *
     * @param b true oder false
     */
    public static void enableWarnung(boolean b) {
        warnung = b;
    }

    /**
     * Loggt <strong>Info</strong> Meldungen im Log-File.
     *
     * @param o Objekt von welchem die Information ausgeht
     * @param msg Nachricht
     */
    public static void info(Object o, String msg) {
        String log = "info - " + o.getClass().getName() + ": " + msg;
        System.out.println(log);
        try {
            writer.append(log + "\n");
            writer.flush();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(Logger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Debug Informationen zu einem Objekt.
     *
     * @param o Objekt
     * @param msg Nachricht
     */
    public static void debug(Object o, String msg) {
        if (debug) {
            String log = "debug - " + o.getClass().getName() + ": " + msg;
            System.out.println(log);
            try {
                writer.append(log + "\n");
                writer.flush();
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(Logger.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Warnungen zu einem Objekt.
     *
     * @param o Objekt
     * @param msg Nachricht
     */
    public static void warnung(Object o, String msg) {
        if (warnung) {
            String log = "warnung - " + o.getClass().getName() + ": " + msg;
            System.out.println(log);
            try {
                writer.append(log + "\n");
                writer.flush();
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(Logger.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Errors zu einem Objekt.
     *
     * @param o Objekt
     * @param msg Nachricht
     */
    public static void error(Object o, String msg) {
        String log = "error - " + o.getClass().getName() + ": " + msg;
        System.out.println(log);
        try {
            writer.append(log + "\n");
            writer.flush();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(Logger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
