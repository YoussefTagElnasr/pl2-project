package services;

import models.AdminRequest;
import java.io.*;
import java.util.ArrayList;

public class RequestServices {

    private static final String FILE_PATH = "files/requests.txt";

    public static ArrayList<AdminRequest> getAllRequests() {
        ArrayList<AdminRequest> requests = new ArrayList<>();

        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                System.out.println("File does not exist: " + FILE_PATH);
                return requests;
            }

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            line = br.readLine(); 

            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");

                if (parts.length != 6) {
                    System.out.println("Skipping invalid line: " + line);
                    continue;
                }

                String customer = parts[0];
                String email = parts[1];
                String detail = parts[2];
                String status = parts[3];
                int price = parts[4].equals("-") ? 0 : Integer.parseInt(parts[4]);
                String readyDate = parts[5].equals("-") ? "" : parts[5];

                AdminRequest request = new AdminRequest(customer, email, detail, status, price, readyDate);
                requests.add(request);
            }

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return requests;
    }
}