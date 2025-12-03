package controllers;
import java.util.ArrayList;
import models.AdminRequest;
import services.RequestServices;
import services.UsersServices;
import models.User;


public class AdminController {
    public static ArrayList<AdminRequest> getAdminRequests(){
        ArrayList<AdminRequest> requests = RequestServices.getAllRequests();
        return requests;
    }

    public static ArrayList<User> getAllUsers(){
        ArrayList<User> users = UsersServices.loadAllUsers();
        return users;
    }
}
