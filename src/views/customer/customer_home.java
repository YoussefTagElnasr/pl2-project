package views.customer;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import services.CustomerServices;
import models.CurrentUser;
import view_utils.SwitchScenes;

import javax.swing.text.html.ImageView;
import java.io.IOException;


public class customer_home {
    @FXML
    private Button dashBtn ;
    @FXML
    private Button contactBtn ;
    @FXML
    private Button soutBtn ;
    @FXML
    private Button event1Btn ;
    @FXML
    private Button event2Btn ;
    @FXML
    private Button event3Btn ;
    @FXML
    private Label custName ;
    @FXML
    private ImageView event1img  ;

    @FXML
    private void initialize(){
        custName.setText(CurrentUser.getInstance().getName());
    }

    @FXML
    private void onDashClicked() {
        String dashPath ="/views/customer/customer_dashboard.fxml";
        Stage stage = (Stage) custName.getScene().getWindow();
        try{
            new SwitchScenes().changeScene(dashPath, stage);
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void onContactClicked() {
        String dashPath ="/views/customer/customer_message.fxml";
        Stage stage = (Stage) custName.getScene().getWindow();
        try{
            new SwitchScenes().changeScene(dashPath, stage);
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void onSignOutClicked() {
        String dashPath ="/views/login/Login.fxml";
        Stage stage = (Stage) custName.getScene().getWindow();
        try{
            new SwitchScenes().changeScene(dashPath, stage);
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void onEvent1Clicked() {
        services.CustomerServices.bookEvent(1);
    }

    @FXML
    private void onEvent2Clicked() {
        services.CustomerServices.bookEvent(2);
    }

    @FXML
    private void onEvent3Clicked() {
        services.CustomerServices.bookEvent(3);
    }



}
