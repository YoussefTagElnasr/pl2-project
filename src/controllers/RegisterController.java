package controllers;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class RegisterController {
    @FXML
    private Button loginButton;

    @FXML
    private TextField usernameField;

    public void GreetUser(){
        String username  = usernameField.getText();
        System.out.println("hallo world");
    }
}