package views.login;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
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
}
