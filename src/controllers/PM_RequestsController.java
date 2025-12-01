package controllers;

import models.Request;
import services.RequestServices;

import java.util.List;

//receive data from frontend controller & execute services operations accordingly//
public class PM_RequestsController {
    public static List<Request> getPendingRequests() {
        List<Request> requests = RequestServices.getPendingRequests();
        return requests;
    }
    public static List<Request> getReadyRequests() {
        List<Request> requests = RequestServices.getReadyRequests();
        return requests;
    }
    public static List<Request> approveRequest(Request targetRequest) {
        List<Request> requests = RequestServices.approveRequest(targetRequest);
        return requests;
    }

}
