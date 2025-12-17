package controllers;

import models.Request;

import java.io.IOException;
import java.util.List;
import services.RequestServices;

public class ServiceProviderController {
    public static List<Request> loadSpRequests(){
        List<Request> requests = RequestServices.getRequests("sent");
        return requests;
    }

    public static void UpdateRequsetPrice(String price , String Date , String email){
        try{
            RequestServices.updateRequest(email, price, Date);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updateStatus(Request request){
        RequestServices.processRequest(request, "ready");
    }

}
