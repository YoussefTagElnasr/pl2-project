package services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import models.User;

public class UsersServices {
    String filePath = "files/users.txt";

    public ArrayList<User> loadAllUsers() {
        ArrayList<User> users = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split("\\|");

                if (parts.length == 4) {
                    String name = parts[0];
                    String email = parts[1];
                    String password = parts[2];
                    String role = parts[3];

                    User user = new User(email, password, name , role);
                    users.add(user);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading users.txt: " + e.getMessage());
        }
        return users;
    }


    public boolean deleteUser(String emailToDelete) {
        ArrayList<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String line;
            String header = br.readLine();
            lines.add(header);

            boolean deleted = false;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");

                if (parts.length == 4) {
                    String email = parts[1];

                    if (email.equalsIgnoreCase(emailToDelete)) {
                        deleted = true;
                        continue;
                    }
                }

                lines.add(line);
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter("users.txt"))) {
                for (String l : lines) {
                    bw.write(l);
                    bw.newLine();
                }
            }
            return deleted;
        } catch (IOException e) {
            System.out.println("Error deleting user: " + e.getMessage());
            return false;
        }
    }
}