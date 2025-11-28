package services;

import models.Customer;
import java.io.*;
import java.util.*;

public class RegisterServices {

    private static final String FILE_PATH = "files/users.txt"; // Path must exist

    public static boolean register(Customer customer) {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                // If file doesn't exist, create it with header
                PrintWriter writer = new PrintWriter(new FileWriter(file));
                writer.println("id|name|email|password|role");
                writer.close();
            } else {
                // Ensure last line ends with a newline
                try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
                    long length = raf.length();
                    if (length > 0) {
                        raf.seek(length - 1);
                        byte lastByte = raf.readByte();
                        if (lastByte != '\n' && lastByte != '\r') {
                            raf.writeBytes(System.lineSeparator());
                        }
                    }
                }
            }

            // Check if email already exists
            if (emailExists(customer.getEmail())) {
                return false;
            }

            int nextId = getNextId();

            // Append new user
            try (FileWriter fw = new FileWriter(FILE_PATH, true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {

                out.println(nextId + "|" 
                        + customer.getName() + "|" 
                        + customer.getEmail() + "|" 
                        + customer.getPassword() + "|" 
                        + customer.getRole());
            }

            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean emailExists(String email) {
        try (Scanner scanner = new Scanner(new File(FILE_PATH))) {
            boolean firstLine = true;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] parts = line.split("\\|");
                if (parts.length >= 3 && parts[2].equalsIgnoreCase(email)) {
                    return true;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static int getNextId() {
        int maxId = 0;

        try (Scanner scanner = new Scanner(new File(FILE_PATH))) {
            boolean firstLine = true;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] parts = line.split("\\|");
                if (parts.length >= 1 && parts[0].matches("\\d+")) {
                    int id = Integer.parseInt(parts[0]);
                    if (id > maxId) maxId = id;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return maxId + 1;
    }
}
