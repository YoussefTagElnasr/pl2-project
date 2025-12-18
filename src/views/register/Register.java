package views.register;

import javafx.fxml.FXML;
import javafx.stage.Stage;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import models.Customer;
import view_utils.Alerts;
import view_utils.SwitchScenes;
import controllers.RegisterController;

import java.io.IOException;


public class Register {

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button registerButton;

    @FXML
    private void registerButtonClick(){
        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassord = confirmPasswordField.getText();

        if (!password.equals(confirmPassord)){
            Alerts.showErrorAlert( "password does not match" ,"password does not match");
            return;
        }

        try{
            Customer user = new Customer(email, password, name);
            RegisterController.handleRegister(user);

        } catch (IllegalArgumentException e){
            Alerts.showErrorAlert(e.getMessage() , "error");
        }
    }

    @FXML
    public void goBackToLogin(){
        try{
            String filePath = "/views/login/login.fxml";
            Stage stage = (Stage) nameField.getScene().getWindow();
            new SwitchScenes().changeScene(filePath, stage);
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
}