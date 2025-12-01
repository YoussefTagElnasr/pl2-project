package services;
//Reads requests from requests text file//
//3 operation :approve(pm)/ready(sp)/sendbill(pm)
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import models.Request;

public class RequestServices {
    public static List<Request> getPendingRequests() {
        List<Request> pendingRequests = new ArrayList<>();
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

                if(status.equals("pending")) {
                    Request request = new Request(name, email, details, status, price, readyDate);
                    pendingRequests.add(request);
                }
                else
                    continue;
            }

        } catch (IOException e) {
            System.out.println("Error reading requests file :"+e.getMessage());
        }
    return pendingRequests;
    }
    public static List<Request> getReadyRequests() {
        List<Request> readyRequests = new ArrayList<>();
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

                if(status.equals("ready")) {
                    Request request = new Request(name, email, details, status, price, readyDate);
                    readyRequests.add(request);
                }
                else
                    continue;
            }

        } catch (IOException e) {
            System.out.println("Error reading requests file :"+e.getMessage());
        }
    return readyRequests;
    }
    public static List<Request> approveRequest(Request targetRequest) {
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
                    status = "approved";
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
