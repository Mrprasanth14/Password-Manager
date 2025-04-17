import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.util.Base64;

public class PasswordManager {

    private static final String SECRET_KEY = "1234567890123456"; // 16-char AES key

    public static void main(String[] args) {
        File inputFile = new File("passwords_input.txt");
        File outputFile = new File("passwords_encrypted.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, true))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 3);
                if (parts.length == 3) {
                    String site = parts[0];
                    String user = parts[1];
                    String plainPass = parts[2];
                    String encryptedPass = encrypt(plainPass);
                    writer.write(site + "," + user + "," + encryptedPass + "\n");
                }
            }

            System.out.println("Passwords encrypted and saved successfully.");
        } catch (IOException e) {
            System.out.println("Error processing files: " + e.getMessage());
        }
    }

    private static String encrypt(String strToEncrypt) {
        try {
            SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes()));
        } catch (Exception e) {
            return null;
        }
    }
}
