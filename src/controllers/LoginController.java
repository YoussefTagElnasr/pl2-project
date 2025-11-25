package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import services.AuthServices;
import models.CurrentUser;

public class LoginController {

    @FXML
    private Button loginButton;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String role = AuthServices.getUserRole(username, password);

        CurrentUser user = new CurrentUser(username, role);

        System.out.println(user.getUsername());
    }
}