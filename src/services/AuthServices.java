package services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import models.UserAuthData;

public class AuthServices {
    public static UserAuthData authenticate(String username, String password) {
        String filePath = "files/users.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split("\\|");
                if (parts.length != 4) continue;

                String name = parts[0];
                String email = parts[1];
                String filePassword = parts[2];
                String role = parts[3];

                if (email.equals(username) && filePassword.equals(password)) {
                    return new UserAuthData(name, email, role);
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return null;
    }

}
