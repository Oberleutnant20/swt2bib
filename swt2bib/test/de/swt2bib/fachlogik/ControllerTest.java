package de.swt2bib.fachlogik;

import de.swt2bib.datenlogik.dto.Account;
import de.swt2bib.fachlogik.accountverwaltung.Accountverwaltung;
import de.swt2bib.fachlogik.ausleihverwaltung.Ausleiheverwaltung;
import de.swt2bib.fachlogik.crypt.Password;
import de.swt2bib.fachlogik.genreverwaltung.Genreverwaltung;
import de.swt2bib.fachlogik.historyverwaltung.Historyverwaltung;
import de.swt2bib.fachlogik.kategorieverwaltung.Kategorienverwaltung;
import de.swt2bib.fachlogik.languageverwaltung.Languageverwaltung;
import de.swt2bib.fachlogik.medienverwaltung.Medienverwaltung;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.junit.Assert.*;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author root
 */
public class ControllerTest {

    Password passwd = new Password();
    
    @Mock
    Languageverwaltung languageverwaltung;
    @Mock
    Accountverwaltung accountverwaltung;
    @Mock
    Medienverwaltung medienverwaltung;
    @Mock
    Ausleiheverwaltung ausleiheverwaltung;
    @Mock
    Kategorienverwaltung kategorienverwaltung;
    @Mock
    Genreverwaltung genreverwaltung;
    @Mock
    Historyverwaltung historyverwaltung;
    Controller sut;
    
    public ControllerTest() {
    }
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        sut = new Controller(languageverwaltung, accountverwaltung, medienverwaltung, ausleiheverwaltung, kategorienverwaltung, genreverwaltung, historyverwaltung);
    }

    @Test
    public void testGetAktuellerUser() {
        assertTrue(sut.getAktuellerUser() == null);
    }

    @Test
    public void testmatchinguserTrue() {
        //GIVEN
        String pw = "blub";
        String user = "bloed";
        Account a = null;
        try {
            a = new Account(user, passwd.getSHA512(pw), true, 0, user, user, 0, user, user, pw);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<Account> liste = new ArrayList<>();
        liste.add(a);
        //WHEN
        Mockito.when(accountverwaltung.get()).thenReturn(liste);
        //THEN
        assertTrue(sut.matchingUser(user, pw) == a);
    }

    @Test
    public void testmatchinguserFalse() {
        //GIVEN
        String pw = "blub";
        String user = "bloed";
        Account a = null;
        try {
            a = new Account(user, passwd.getSHA512(pw), true, 0, user, user, 0, user, user, pw);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<Account> liste = new ArrayList<>();
        liste.add(a);
        //WHEN
        Mockito.when(accountverwaltung.get()).thenReturn(liste);
        //THEN
        assertFalse(sut.matchingUser("user", "pw") == a);
    }

    @Test
    public void testIsMitarbeiter() {
        assertFalse(sut.isMitarbeiter());
    }

    @Test
    public void testIsMitarbeiterTrue() {
        //GIVEN
        String pw = "BLÄH";
        String user = "BLÖH";
        Account a = null;
        try {
            a = new Account(user, passwd.getSHA512(pw), true, 0, user, user, 0, user, user, pw);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<Account> liste = new ArrayList<>();
        liste.add(a);
        //WHEN
        Mockito.when(accountverwaltung.get()).thenReturn(liste);
        sut.setAktuellerUser(user, pw);
        //THEN
        assertTrue(sut.isMitarbeiter());
    }

    @Test
    public void testIsMitarbeiterFalse() {
        //GIVEN
        String pw = "BLÄH";
        String user = "BLÖH";
        Account a= null;
        try {
            a = new Account(user, passwd.getSHA512(pw), false, 0, user, user, 0, user, user, pw);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<Account> liste = new ArrayList<>();
        liste.add(a);
        //WHEN
        Mockito.when(accountverwaltung.get()).thenReturn(liste);
        sut.setAktuellerUser(user, pw);
        //THEN
        assertFalse(sut.isMitarbeiter());
    }
}
