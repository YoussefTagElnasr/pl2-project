package views.login;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import controllers.LoginController;

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
            showAlert();
        }
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Failed");
        alert.setHeaderText(null);
        alert.setContentText("The username or password may not be correct!");
        alert.showAndWait();
    }
}
