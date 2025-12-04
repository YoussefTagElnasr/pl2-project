package views.login;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;

import java.io.IOException;

import controllers.LoginController;
import view_utils.Alerts;
import view_utils.SwitchScenes;
import models.CurrentUser;

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
        String adminPath = "/views/admin/admin.fxml";
        String pmPath = "/views/PM/Pm_home.fxml";
        Stage stage = (Stage) usernameField.getScene().getWindow();

        try {
            CurrentUser user = LoginController.handleLogin(username, password);
            if (user.getRole().equals("admin")){
                try{
                    new SwitchScenes().changeScene(adminPath, stage);
                } catch(IOException e){
                    System.out.println(e.getMessage());
                }

            }
            else if (user.getRole().equals("pm")){
                try{
                    new SwitchScenes().changeScene(pmPath, stage);
                } catch(IOException e){
                    System.out.println(e.getMessage());
                }
            }
        } catch (SecurityException e) {
            Alerts.showErrorAlert(e.getMessage() , "login failed");
        }
    }


    @FXML
    public void goToRegister(){
        try{
            String filePath = "/views/register/register.fxml";
            Stage stage = (Stage) usernameField.getScene().getWindow();
            new SwitchScenes().changeScene(filePath, stage);
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
}