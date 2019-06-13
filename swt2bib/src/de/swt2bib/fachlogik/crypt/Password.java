package de.swt2bib.fachlogik.crypt;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * Class for generating Password Hashes to save this in Databases
 *
 * @author Tim Lorse
 */
public class Password {

    /**
     * Salt mit einer festgelegten Länge von 256 Zeichen.
     */
    private final String salt = "-lbDm)238HLh?/)gJ/dhAEHtL!Hzd>7pJ9fi%)Hw/o%v!SJ67*beh1+ngzBm2Xz<Mz)-8w&6?RvWgAjUx0TFa(AHHf(8m16b&LnHaLLgHfWdG&haz!G!/2H?D/1%RKlgoTvBl+sGJyL+iYmjqV)6&BB+c)O#N9IKJIhPoZa8Wl#H)mkEujtjuDrWE*<F)KF<MIOV1s4meOkndTZ9j3e%lE9NUYoLI?gdE&9H3yayx)rUBG&AaEf1pjJ>JQI1Klxg";

    /**
     * AES Verschlüsselungs Key
     */
    private SecretKeySpec secretKey;
    
    /**
     * Key als Byte Array
     */
    private static byte[] key;

    /**
     * Verschlüsselungsalgorithmus mit MD2 mit vorgegebenem SALT von 256 Zeichen
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

    /**
     * Generiert den den SecretKey für AES
     *
     * @param myKey Wort zum generieren des keys
     */
    public void setAesKey(String myKey) {
        MessageDigest sha;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-512");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
        }
    }

    /**
     * Verschlüsselt das Passwort mit AES
     *
     * @param strToEncrypt zu verschlüsselndes Passwort
     * @return verschlüsseltes Passwort
     */
    public String aesEncrypt(String strToEncrypt) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    /**
     * Entschlüsselt das mit AES verschlüsselte Passwort
     *
     * @param strToDecrypt verschlüsseltes Passwort
     * @return Passwort in Klartext
     */
    public String aesDecrypt(String strToDecrypt) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
}
