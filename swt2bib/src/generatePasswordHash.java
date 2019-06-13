import de.swt2bib.fachlogik.crypt.Password;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Generiert den Passworthash für den Benutzer zum initAccount im SQL Script
 * @author root
 */
public class generatePasswordHash {
    private String username = null;
    private String pw = null;
        
    // Setzen von username und pw
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }
    
    public String getUsername(){
        return username;
    }
    
    public String getPw(){
        return pw;
    }
    
    /**
     * Generiert das Passwort und gibt es auf der Console aus.
     * @param args Not Used
     * @throws NoSuchAlgorithmException Kein Algorithmus
     * @throws UnsupportedEncodingException  Falsches Encoding
     */
    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        generatePasswordHash generate = new generatePasswordHash();
        
        generate.setUsername("Admin");
        generate.setPw("!Administrator@swt2bib");
        
        System.out.println("Gewähltes Passwort für "+generate.getUsername()+": ");
        System.out.println(new Password().getSHA512(generate.getPw()));
    }
}
