package controllers;

import models.Request;

import java.util.List;
import services.RequestServices;

public class ServiceProviderController {
    public static List<Request> loadSpRequests(){
        List<Request> requests = RequestServices.getRequests("sent");
        return requests;
    }
}
