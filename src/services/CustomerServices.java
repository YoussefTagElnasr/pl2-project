package services;
import java.util.List;
import java.util.*;
import  java.io.*;
import java.io.IOException;
import models.CurrentUser;

public class CustomerServices {
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

            String name = "Mohamed";
            String email = "email";

            String requestLine = name + "|"
                    + email + "|"
                    + eventName + "|pending||";

            try (BufferedWriter bw = new BufferedWriter(new FileWriter("files/requests.txt", true))) {
                bw.write(requestLine);
                bw.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }


    }
}
