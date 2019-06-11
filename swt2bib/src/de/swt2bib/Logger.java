package de.swt2bib;

/**
 *
 * @author root
 */
public class Logger {

    private static Logger instance = new Logger();
    private static boolean debug = false;
    private static boolean warnung = false;
    
    //make the constructor private so that this class cannot be
    //instantiated
    private Logger() {
    }

    //Get the only object available
    public static Logger getInstance() {
        return instance;
    }

    public static void enableDebug(boolean b) {
        debug = b;
    }

    public static void enableWarnung(boolean b) {
        warnung = b;
    }

    public static void info(Object o, String msg) {
        System.out.println("info - " + o.getClass().getName() + ": " + msg);
    }

    public static void debug(Object o, String msg) {
        if (debug) {
            System.out.println("debug - " + o.getClass().getName() + ": " + msg);
        }
    }

    public static void warnung(Object o, String msg) {
        if (warnung) {
            System.out.println("warnung - " + o.getClass().getName() + ": " + msg);
        }
    }

    public static void error(Object o, String msg) {
        System.out.println("error - " + o.getClass().getName() + ": " + msg);
    }
}
