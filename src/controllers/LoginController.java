package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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

        if (role != null) {
            CurrentUser.setInstance(username, role);
            //TODO right here we need to switch the scene for diffrent roles
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("The username or password may not be correct!");
            alert.showAndWait();
        }
    }
}