package views.login;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;

import java.io.IOException;

import controllers.LoginController;
import view_utils.Alerts;

public class Login {

    @FXML
    private Button loginButton;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void loginButtonClick() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            LoginController.handleLogin(username, password);
        } catch (SecurityException e) {
            Alerts.showAlert("the username or password may be wrong" , "login failed");
        }
    }

    @FXML
    public void goToRegister() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/register/register.fxml"));
        Stage stage = (Stage) usernameField.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}