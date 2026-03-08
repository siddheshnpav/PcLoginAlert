package pcloginalert;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;
import java.util.Base64;
import java.security.MessageDigest;

public class CryptoUtil {

    private static final String SECRET = "ShreeNidhi@0809";
    private static final String PREFIX = "ENC:";

    public static String EncyptDecryptString(String input) {

        try {

            byte[] key = MessageDigest.getInstance("SHA-256")
                    .digest(SECRET.getBytes("UTF-8"));

            SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            // ---------- DECRYPT ----------
            if (input.startsWith(PREFIX)) {

                String data = input.substring(PREFIX.length());
                byte[] decoded = Base64.getDecoder().decode(data);

                byte[] iv = new byte[16];
                byte[] encrypted = new byte[decoded.length - 16];

                System.arraycopy(decoded, 0, iv, 0, 16);
                System.arraycopy(decoded, 16, encrypted, 0, encrypted.length);

                cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));

                byte[] decrypted = cipher.doFinal(encrypted);

                return new String(decrypted, "UTF-8");
            }

            // ---------- ENCRYPT ----------
            byte[] iv = new byte[16];
            SecureRandom random = new SecureRandom();
            random.nextBytes(iv);

            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));

            byte[] encrypted = cipher.doFinal(input.getBytes("UTF-8"));

            byte[] combined = new byte[iv.length + encrypted.length];

            System.arraycopy(iv, 0, combined, 0, iv.length);
            System.arraycopy(encrypted, 0, combined, iv.length, encrypted.length);

            return PREFIX + Base64.getEncoder().encodeToString(combined);

        } catch (Exception e) {
            throw new RuntimeException("Crypto failure", e);
        }
    }

}