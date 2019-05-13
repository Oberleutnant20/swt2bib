package de.swt2bib.fachlogik.crypt;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Tim Lorse
 */
public class Password {

    /**
     * Salt mit einer festgelegten Länge von 256 Zeichen.
     */
    private final String salt = "-lbDm)238HLh?/)gJ/dhAEHtL!Hzd>7pJ9fi%)Hw/o%v!SJ67*beh1+ngzBm2Xz<Mz)-8w&6?RvWgAjUx0TFa(AHHf(8m16b&LnHaLLgHfWdG&haz!G!/2H?D/1%RKlgoTvBl+sGJyL+iYmjqV)6&BB+c)O#N9IKJIhPoZa8Wl#H)mkEujtjuDrWE*<F)KF<MIOV1s4meOkndTZ9j3e%lE9NUYoLI?gdE&9H3yayx)rUBG&AaEf1pjJ>JQI1Klxg";

    /**
     * Verschlüsselungsalgorithmus mit MD2 mit
     * vorgegebenem SALT von 256 Zeichen
     * Länge.
     *
     * Zum Speichern in einer Datenbank wird eine Zeichen Länge von
     * "VARCHAR(48)" benötigt. Bei einer Passwort-Länge von 256 Zeichen.
     *
     * @param passwd Angabe vom Passwort
     * @return gibt das Verschlüsselte Passwort zurück
     * @throws NoSuchAlgorithmException meldet einen Fehler, wenn es kein
     * Algorithmus ist
     * @throws UnsupportedEncodingException meldet einen Fehler, wenn es nicht
     * UTF-8 ist
     */
    public String getMD2(String passwd) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return getMD2(passwd, this.salt);
    }

    /**
     * Verschlüsselungsalgorithmus mit MD2 ohne SALT Vorgabe.
     *
     * Zum Speichern in einer Datenbank wird eine Zeichen Länge von
     * "VARCHAR(48)" benötigt. Bei einer SALT- und Passwort-Länge von 256
     * Zeichen.
     *
     * @param passwd Angabe vom Passwort
     * @param salt Angabe eines Salts
     * @return gibt das Verschlüsselte Passwort zurück
     * @throws NoSuchAlgorithmException meldet einen Fehler, wenn es kein
     * Algorithmus ist
     * @throws UnsupportedEncodingException meldet einen Fehler, wenn es nicht
     * UTF-8 ist
     */
    public String getMD2(String passwd, String salt) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String ret = null;
        MessageDigest md = MessageDigest.getInstance("MD2");
        md.update(salt.getBytes("UTF-8"));
        byte[] bytes = md.digest(passwd.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (int x = 0; x < bytes.length; x++) {
            sb.append(Integer.toString((bytes[x] & 0xFF) + 0x100, 16)).substring(1);
        }
        ret = sb.toString();
        return ret;
    }

    /**
     * Verschlüsselungsalgorithmus mit MD5 mit vorgegebenem SALT von 256 Zeichen
     * Länge.
     *
     * Zum Speichern in einer Datenbank wird eine Zeichen Länge von
     * "VARCHAR(48)" benötigt. Bei einer Passwort-Länge von 256 Zeichen.
     *
     * @param passwd Angabe vom Passwort
     * @return gibt das Verschlüsselte Passwort zurück
     * @throws NoSuchAlgorithmException meldet einen Fehler, wenn es kein
     * Algorithmus ist
     * @throws UnsupportedEncodingException meldet einen Fehler, wenn es nicht
     * UTF-8 ist
     */
    public String getMD5(String passwd) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return getMD5(passwd, this.salt);
    }

    /**
     * Verschlüsselungsalgorithmus mit MD5 ohne SALT Vorgabe.
     *
     * Zum Speichern in einer Datenbank wird eine Zeichen Länge von
     * "VARCHAR(48)" benötigt. Bei einer SALT- und Passwort-Länge von 256
     * Zeichen.
     *
     * @param passwd Angabe vom Passwort
     * @param salt Angabe eines Salts
     * @return gibt das Verschlüsselte Passwort zurück
     * @throws NoSuchAlgorithmException meldet einen Fehler, wenn es kein
     * Algorithmus ist
     * @throws UnsupportedEncodingException meldet einen Fehler, wenn es nicht
     * UTF-8 ist
     */
    public String getMD5(String passwd, String salt) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String ret = null;
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(salt.getBytes("UTF-8"));
        byte[] bytes = md.digest(passwd.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (int x = 0; x < bytes.length; x++) {
            sb.append(Integer.toString((bytes[x] & 0xFF) + 0x100, 16)).substring(1);
        }
        ret = sb.toString();
        return ret;
    }

    /**
     * Verschlüsselungsalgorithmus mit SHA-224 mit vorgegebenem SALT von 256
     * Zeichen Länge.
     *
     * Zum Speichern in einer Datenbank wird eine Zeichen Länge von
     * "VARCHAR(84)" benötigt. Bei einer Passwort-Länge von 256 Zeichen.
     *
     * @param passwd Angabe vom Passwort
     * @return gibt das Verschlüsselte Passwort zurück
     * @throws NoSuchAlgorithmException meldet einen Fehler, wenn es kein
     * Algorithmus ist
     * @throws UnsupportedEncodingException meldet einen Fehler, wenn es nicht
     * UTF-8 ist
     */
    public String getSHA224(String passwd) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return getSHA224(passwd, this.salt);
    }

    /**
     * Verschlüsselungsalgorithmus mit SHA-224 ohne SALT Vorgabe.
     *
     * Zum Speichern in einer Datenbank wird eine Zeichen Länge von
     * "VARCHAR(84)" benötigt. Bei einer SALT- und Passwort-Länge von 256
     * Zeichen.
     *
     * @param passwd Angabe vom Passwort
     * @param salt Angabe eines Salts
     * @return gibt das Verschlüsselte Passwort zurück
     * @throws NoSuchAlgorithmException meldet einen Fehler, wenn es kein
     * Algorithmus ist
     * @throws UnsupportedEncodingException meldet einen Fehler, wenn es nicht
     * UTF-8 ist
     */
    public String getSHA224(String passwd, String salt) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String ret = null;
        MessageDigest md = MessageDigest.getInstance("SHA-224");
        md.update(salt.getBytes("UTF-8"));
        byte[] bytes = md.digest(passwd.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (int x = 0; x < bytes.length; x++) {
            sb.append(Integer.toString((bytes[x] & 0xFF) + 0x100, 16)).substring(1);
        }
        ret = sb.toString();
        return ret;
    }

    /**
     * Verschlüsselungsalgorithmus mit SHA-256 mit vorgegebenem SALT von 256
     * Zeichen Länge.
     *
     * Zum Speichern in einer Datenbank wird eine Zeichen Länge von
     * "VARCHAR(96)" benötigt. Bei einer Passwort-Länge von 256 Zeichen.
     *
     * @param passwd Angabe vom Passwort
     * @return gibt das Verschlüsselte Passwort zurück
     * @throws NoSuchAlgorithmException meldet einen Fehler, wenn es kein
     * Algorithmus ist
     * @throws UnsupportedEncodingException meldet einen Fehler, wenn es nicht
     * UTF-8 ist
     */
    public String getSHA256(String passwd) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return getSHA256(passwd, this.salt);
    }

    /**
     * Verschlüsselungsalgorithmus mit SHA-256 ohne SALT Vorgabe.
     *
     * Zum Speichern in einer Datenbank wird eine Zeichen Länge von
     * "VARCHAR(96)" benötigt. Bei einer SALT- und Passwort-Länge von 256
     * Zeichen.
     *
     * @param passwd Angabe vom Passwort
     * @param salt Angabe eines Salts
     * @return gibt das Verschlüsselte Passwort zurück
     * @throws NoSuchAlgorithmException meldet einen Fehler, wenn es kein
     * Algorithmus ist
     * @throws UnsupportedEncodingException meldet einen Fehler, wenn es nicht
     * UTF-8 ist
     */
    public String getSHA256(String passwd, String salt) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String ret = null;
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt.getBytes("UTF-8"));
        byte[] bytes = md.digest(passwd.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (int x = 0; x < bytes.length; x++) {
            sb.append(Integer.toString((bytes[x] & 0xFF) + 0x100, 16)).substring(1);
        }
        ret = sb.toString();
        return ret;
    }

    /**
     * Verschlüsselungsalgorithmus mit SHA-384 mit vorgegebenem SALT von 256
     * Zeichen Länge.
     *
     * Zum Speichern in einer Datenbank wird eine Zeichen Länge von
     * "VARCHAR(114)" benötigt. Bei einer Passwort-Länge von 256 Zeichen.
     *
     * @param passwd Angabe vom Passwort
     * @return gibt das Verschlüsselte Passwort zurück
     * @throws NoSuchAlgorithmException meldet einen Fehler, wenn es kein
     * Algorithmus ist
     * @throws UnsupportedEncodingException meldet einen Fehler, wenn es nicht
     * UTF-8 ist
     */
    public String getSHA384(String passwd) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return getSHA384(passwd, this.salt);
    }

    /**
     * Verschlüsselungsalgorithmus mit SHA-384 ohne SALT Vorgabe.
     *
     * Zum Speichern in einer Datenbank wird eine Zeichen Länge von
     * "VARCHAR(114)" benötigt. Bei einer SALT- und Passwort-Länge von 256
     * Zeichen.
     *
     * @param passwd Angabe vom Passwort
     * @param salt Angabe eines Salts
     * @return gibt das Verschlüsselte Passwort zurück
     * @throws NoSuchAlgorithmException meldet einen Fehler, wenn es kein
     * Algorithmus ist
     * @throws UnsupportedEncodingException meldet einen Fehler, wenn es nicht
     * UTF-8 ist
     */
    public String getSHA384(String passwd, String salt) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String ret = null;
        MessageDigest md = MessageDigest.getInstance("SHA-384");
        md.update(salt.getBytes("UTF-8"));
        byte[] bytes = md.digest(passwd.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (int x = 0; x < bytes.length; x++) {
            sb.append(Integer.toString((bytes[x] & 0xFF) + 0x100, 16)).substring(1);
        }
        ret = sb.toString();
        return ret;
    }

    /**
     * Verschlüsselungsalgorithmus mit SHA-512 mit vorgegebenem SALT von 256
     * Zeichen Länge.
     *
     * Zum Speichern in einer Datenbank wird eine Zeichen Länge von
     * "VARCHAR(192)" benötigt. Bei einer Passwort-Länge von 256 Zeichen.
     *
     * @param passwd Angabe vom Passwort
     * @return gibt das Verschlüsselte Passwort zurück
     * @throws NoSuchAlgorithmException meldet einen Fehler, wenn es kein
     * Algorithmus ist
     * @throws UnsupportedEncodingException meldet einen Fehler, wenn es nicht
     * UTF-8 ist
     */
    public String getSHA512(String passwd) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return getSHA512(passwd, this.salt);
    }

    /**
     * Verschlüsselungsalgorithmus mit SHA-512 ohne SALT Vorgabe.
     *
     * Zum Speichern in einer Datenbank wird eine Zeichen Länge von
     * "VARCHAR(192)" benötigt. Bei einer SALT- und Passwort-Länge von 256
     * Zeichen.
     *
     * @param passwd Angabe vom Passwort
     * @param salt Angabe eines Salts
     * @return gibt das Verschlüsselte Passwort zurück
     * @throws NoSuchAlgorithmException meldet einen Fehler, wenn es kein
     * Algorithmus ist
     * @throws UnsupportedEncodingException meldet einen Fehler, wenn es nicht
     * UTF-8 ist
     */
    public String getSHA512(String passwd, String salt) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String ret = null;
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(salt.getBytes("UTF-8"));
        byte[] bytes = md.digest(passwd.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (int x = 0; x < bytes.length; x++) {
            sb.append(Integer.toString((bytes[x] & 0xFF) + 0x100, 16)).substring(1);
        }
        ret = sb.toString();
        return ret;
    }
}
