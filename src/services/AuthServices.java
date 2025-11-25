package services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AuthServices {
    public static String getUserRole(String email, String password) {
    String filePath = "files/users.txt";

    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        String line;

        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) continue;

            String[] parts = line.split("\\|");
            if (parts.length != 5) continue;

            String fileEmail = parts[2];
            String filePassword = parts[3];
            String role = parts[4];

            if (fileEmail.equals(email) && filePassword.equals(password)) {
                return role;
            }
        }

        } catch (IOException e) {
            System.out.println(e);
        }
        return null;
    }
}
