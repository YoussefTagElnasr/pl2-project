package services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AuthServices {
    public static String getUserRole(String username, String password) {
        String filePath = "files/users.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split("\\|");
                if (parts.length != 5) continue;

                String fileName = parts[1];
                String filePassword = parts[3];
                String role = parts[4];

                if (fileName.equals(username) && filePassword.equals(password)) {
                    return role;
                }
            }

            } catch (IOException e) {
                System.out.println(e);
            }
            return null;
        }
}
