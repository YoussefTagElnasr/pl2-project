package services;
import java.util.List;
import java.util.*;
import  java.io.*;
import java.io.IOException;
import models.CurrentUser;
import models.Request;

public class CustomerServices {
    private static final String FILE_PATH = "files/requests.txt";
    public static void bookEvent (int eventNum){

            String eventName;

            switch (eventNum) {
                case 1:
                    eventName = "Atlantic Ocean Concert";
                    break;
                case 2:
                    eventName = "Eminem Concert";
                    break;
                case 3:
                    eventName = "El Clasico Match";
                    break;
                default:
                    System.out.println("Invalid event number");
                    return;
            }

            String name = models.CurrentUser.getInstance().getName();
            String email = models.CurrentUser.getInstance().getEmail();

            String requestLine = name + "|"
                    + email + "|"
                    + eventName + "|pending||";

            try (BufferedWriter bw = new BufferedWriter(new FileWriter("files/requests.txt", true))) {
                bw.newLine();
                bw.write(requestLine);
            } catch (IOException e) {
                e.printStackTrace();
            }


    }
    public static List<Request> getMyTickets(String user) {
        List<Request> requiredRequests = new ArrayList<>();
        String filepath = "files/requests.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] parts = line.split("\\|",-1);
                if (parts.length != 6) {
                    continue;
                }
                String name = parts[0];
                String email = parts[1];
                String details = parts[2];
                String status = parts[3];
                String price = parts[4];
                String readyDate = parts[5];

                if(name.equals(user)) {
                    Request request = new Request(name, email, details, status, price, readyDate);
                    requiredRequests.add(request);
                }
                else
                    continue;
            }

        } catch (IOException e) {
            System.out.println("Error reading requests file :"+e.getMessage());
        }
        return requiredRequests;



    } public static List<Request> cancelTicket(Request targetRequest) {
        List<Request> updatedRequests = new ArrayList<>();
        String filepath = "files/requests.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] parts = line.split("\\|",-1);
                if (parts.length != 6) {
                    continue;
                }

                String name = parts[0];
                String email = parts[1];
                String details = parts[2];
                String status = parts[3];
                String price = parts[4];
                String readyDate = parts[5];

                if(name.equals(targetRequest.getName())&&email.equals(targetRequest.getEmail())&&details.equals(targetRequest.getDetails())) {
                    continue;
                }
                Request request = new Request(name, email, details, status, price, readyDate);
                updatedRequests.add(request);

            }

        } catch (IOException e) {
            System.out.println("Error reading requests file :"+e.getMessage());
        }
        saveUpdatedRequests(updatedRequests);
        return updatedRequests;
    }
    public static void saveUpdatedRequests(List<Request> updatedRequests) {
        String filepath = "files/requests.txt";
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))){
            for(Request r : updatedRequests){
                writer.write(r.getName()+"|"+r.getEmail()+"|"+r.getDetails()+"|"+r.getStatus()+"|"+r.getPrice()+"|"+r.getReadyDate());
                writer.newLine();
            }

        }catch (IOException e) {
            System.out.println("Error writing requests file :"+e.getMessage());
        }
    }

}

