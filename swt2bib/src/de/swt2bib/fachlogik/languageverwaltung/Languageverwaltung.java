package de.swt2bib.fachlogik.languageverwaltung;

import de.swt2bib.fachlogik.ElternVerwaltung;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author root
 */
public class Languageverwaltung extends ElternVerwaltung {

    // Attribute
    private Properties props;

    /**
     * Konstruktor für die Sprachenverwaltung.
     */
    public Languageverwaltung() {
        props = new Properties();
    }

    /**
     * Läd die deutschen Properties.
     *
     * @throws FileNotFoundException Datei nicht gefunden
     * @throws IOException IO Fehler
     */
    public void getDeutsch() throws FileNotFoundException, IOException {
        de.swt2bib.Logger.debug(this, "getDeutsch");
        FileReader fileReader = new FileReader("deutsch.props");
        props.load(fileReader);
        notifyLanguagePanels(props);
    }

    /**
     * Läd die Englischen Properties.
     *
     * @throws FileNotFoundException Datei nicht gefunden
     * @throws IOException IO Fehler
     */
    public void getEnglisch() throws FileNotFoundException, IOException {
        de.swt2bib.Logger.debug(this, "getEnglisch");
        FileReader fileReader = new FileReader("englisch.props");
        props.load(fileReader);
        notifyLanguagePanels(props);
    }
}
