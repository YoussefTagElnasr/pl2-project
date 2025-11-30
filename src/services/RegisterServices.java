package services;

import models.Customer;
import java.io.*;
import java.util.*;

public class RegisterServices {

    private static final String FILE_PATH = "files/users.txt";

    public static boolean register(Customer customer) {
        try {
            File file = new File(FILE_PATH);

            if (!file.exists()) {
                PrintWriter writer = new PrintWriter(new FileWriter(file));
                writer.println("name|email|password|role");
                writer.close();
            } else {
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

            if (emailExists(customer.getEmail())) {
                return false;
            }

            try (FileWriter fw = new FileWriter(FILE_PATH, true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {

                out.println(customer.getName() + "|" 
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
                if (parts.length >= 2 && parts[1].equalsIgnoreCase(email)) {
                    return true;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
