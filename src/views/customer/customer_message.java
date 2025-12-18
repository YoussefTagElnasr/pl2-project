package views.customer;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.*;
import view_utils.SwitchScenes;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class customer_message {
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField msgField;
    @FXML
    private Button submitBtn;
    @FXML
    private void initialize() {
        nameField.setText(models.CurrentUser.getInstance().getName());
        emailField.setText(models.CurrentUser.getInstance().getEmail());
    }
    @FXML
    private void onSubmitClicked() {
        String name = nameField.getText();
        String email = emailField.getText();
        String msg = msgField.getText();

        String messageLine = name + "|"
                + email + "|"
                + msg + "||false";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("files/messages.txt", true))) {
            bw.newLine();
            bw.write(messageLine);
            System.out.println(messageLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String dashPath ="/views/customer/customer_home.fxml";
        Stage stage = (Stage) nameField.getScene().getWindow();
        try{
            new SwitchScenes().changeScene(dashPath, stage);
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
        view_utils.Alerts.showSuccessAlert("Message Sent","");
    }
    @FXML
    private void onOldMsgsClicked() {
        String dashPath ="/views/customer/customer_oldMsgs.fxml";
        Stage stage = (Stage) emailField.getScene().getWindow();
        try{
            new SwitchScenes().changeScene(dashPath, stage);
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
}
