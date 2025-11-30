package controllers;
import java.util.ArrayList;
import models.AdminRequest;
import services.RequestServices;


public class AdminController {
    public static ArrayList<AdminRequest> getAdminRequests(){
        ArrayList<AdminRequest> requests = RequestServices.getAllRequests();
        return requests;
    }
}
