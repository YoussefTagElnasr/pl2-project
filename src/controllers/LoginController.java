package controllers;
import services.AuthServices;
import models.CurrentUser;

public class LoginController {
    public static void handleLogin(String username ,String password) throws SecurityException {
        String role = AuthServices.getUserRole(username, password);
        if (role != null) {
            CurrentUser.setInstance(username, role);
        } else {
            throw new SecurityException("role was null");
        }
    }
}