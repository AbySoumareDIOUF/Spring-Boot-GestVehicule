package com.tp.vehicule.EncryptDecrypt;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
        import java.util.Base64;

public class EncryptorDecryptor {

    private static final String ALGO = "AES";
    private static final String KEY_FILE = "secret.key"; // Fichier pour stocker la clé
    private static SecretKey secretKey;

    static {
        try {
            File keyFile = new File(KEY_FILE);

            if (keyFile.exists()) {
                // 🔵 Charger la clé existante
                try (FileInputStream fis = new FileInputStream(keyFile)) {
                    byte[] keyBytes = fis.readAllBytes();
                    secretKey = new SecretKeySpec(keyBytes, ALGO);
                    System.out.println("[EncryptorDecryptor] 🔑 Clé AES chargée depuis secret.key");
                }
            } else {
                // 🟢 Générer une nouvelle clé
                KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGO);
                keyGenerator.init(128); // 128 bits AES
                secretKey = keyGenerator.generateKey();

                // 📝 Sauvegarder la clé
                try (FileOutputStream fos = new FileOutputStream(keyFile)) {
                    fos.write(secretKey.getEncoded());
                    System.out.println("[EncryptorDecryptor] 🆕 Nouvelle clé AES générée et sauvegardée dans secret.key");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'initialisation du chiffrement", e);
        }
    }

    // 🔒 Chiffrement d'une chaîne
    public static String encrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGO);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // 🔓 Déchiffrement d'une chaîne
    public static String decrypt(String encryptedData) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGO);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
        return new String(cipher.doFinal(decodedBytes));
    }
}
