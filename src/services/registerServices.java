package services;

import models.Customer;
import java.io.*;
import java.util.*;

public class registerServices {

    private static final String USERS_FILE = "users.txt";

    public static boolean register(Customer customer) {
        try {
            if (emailExists(customer.getEmail())) {
                return false;
            }

            int nextId = getNextId();

            FileWriter fw = new FileWriter(USERS_FILE, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);

            out.println(nextId + "|" 
                    + customer.getName() + "|" 
                    + customer.getEmail() + "|" 
                    + customer.getPassword() + "|" 
                    + customer.getRole());

            out.close();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean emailExists(String email) {
        try {
            File file = new File(USERS_FILE);
            if (!file.exists()) return false;

            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\|");

                if (parts.length >= 3) {
                    String existingEmail = parts[2];
                    if (existingEmail.equalsIgnoreCase(email)) {
                        scanner.close();
                        return true;
                    }
                }
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static int getNextId() {
        int maxId = 0;

        try {
            File file = new File(USERS_FILE);
            if (!file.exists()) return 1;

            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\|");

                int id = Integer.parseInt(parts[0]);
                if (id > maxId) {
                    maxId = id;
                }
            }

            scanner.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return maxId + 1;
    }
}
