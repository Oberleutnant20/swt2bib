package de.swt2bib.info.exceptions;

/**
 *
 * @author Tim Lorse
 * @version 0.1
 * @since 0.1
 */
public class ConnectionError extends Exception{
    public ConnectionError(){
        super("Connection ist nicht vorhanden. Bitte Prüfen. \n"
                + "Sollte der Fehler weiterhin bestehen, bitte die Entwickler kontaktieren.");
    }
}
