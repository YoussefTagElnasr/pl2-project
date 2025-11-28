package views.register;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import models.Customer;
import view_utils.Alerts;
import controllers.RegisterController;


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
            Alerts.showAlert( "password does not match" ,"password does not match");
            return;
        }
        
        try{
            Customer user = new Customer(email, password, name);
            RegisterController.handleRegister(user);
            
        } catch (IllegalArgumentException e){
            Alerts.showAlert(e.getMessage() , "error");
        }
    }

}
