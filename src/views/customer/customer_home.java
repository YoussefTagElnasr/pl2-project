package views.customer;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import services.CustomerServices;

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
    private void onDashClicked() {

    }

    @FXML
    private void onContactClicked() {

    }

    @FXML
    private void onSignOutClicked() {

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
