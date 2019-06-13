package de.swt2bib.info;

/**
 *
 * @author Tim Lorse
 * @version 0.1
 * @since Version 0.1
 */
public class Informationen {

    private final String name = "Joschi Mehta, Tim Lorse";
    //private final String mail = "";
    private final String version = "0.5";
    private final String copyright = "Joschi Mehta, Tim Lorse";
    private final String info = "Diese Funktion ist noch in der Entwicklung.";
    
    /**
     * Bei der Entwicklerinformation ist<br>
     * Der Author, die Mailadresse, die Version der Datei und das Copyright
     * hinterlegt
     *
     * @return EntwicklerInformationen
     */
    public String printEntwickler() {
        return "Name: " + name + " \n"
                //+ "Mail: " + mail + " \n"
                + "Version: " + version + " \n"
                + "© "+copyright;
    }

    /**
     * Bei der Entwicklungsinformation ist <br>
     * nur der Status der Entwicklung hinterlegt
     *
     * @param info Information zum Entwicklungsstatus
     * @return Status der Entwickung
     */
    public String printEntwicklung(String info) {
        if(info.equals("") || info.isEmpty()){
            return this.info;
        } else {
            return info;
        }
    }
}
