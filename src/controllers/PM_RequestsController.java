package controllers;

import models.Request;
import services.RequestServices;

import java.util.List;

//receive data from frontend controller & execute services operations accordingly//
public class PM_RequestsController {
    public static List<Request> getRequests(String ReqStatus) {
        List<Request> requiredRequests = RequestServices.getRequests(ReqStatus);
        return requiredRequests;
    }
    public static List<Request> processRequest(Request targetRequest,String newStatus) {
        List<Request> updatedRequests = RequestServices.processRequest(targetRequest,newStatus);
        return updatedRequests;
    }

}
