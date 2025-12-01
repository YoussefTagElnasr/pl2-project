package controllers;
import services.AuthServices;
import models.CurrentUser;
import models.UserAuthData;

public class LoginController {
    public static void handleLogin(String username, String password) throws SecurityException {
        UserAuthData data = AuthServices.authenticate(username, password);
        if (data != null) {
            CurrentUser.setInstance(data.getName(), data.getEmail(), data.getRole());
        } else {
            throw new SecurityException("Invalid username or password");
        }
    }
}