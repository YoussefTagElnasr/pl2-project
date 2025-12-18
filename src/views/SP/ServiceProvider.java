package views.SP;

import javafx.fxml.FXML;
import models.Request;
import view_utils.Alerts;

import java.util.List;
import java.time.LocalDate;
import controllers.ServiceProviderController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.format.DateTimeFormatter;


public class ServiceProvider {

    @FXML
    private Button btnSubmit;

    @FXML 
    private TextField txtPrice;

    @FXML
    private DatePicker dateReady;

    @FXML
    private TableView<Request> tableRequests;

    @FXML 
    private TableColumn<Request, String> colCustomer;

    @FXML 
    private TableColumn<Request, String> colCustomerEmail;

    @FXML 
    private TableColumn<Request, String> colDetails;

    @FXML 
    private TableColumn<Request, String> colStatus;

    @FXML 
    private TableColumn<Request, Integer> colPrice;

    @FXML 
    private TableColumn<Request, String> colReadyDate;

    public void initialize(){
        colCustomer.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCustomerEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colDetails.setCellValueFactory(new PropertyValueFactory<>("details"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colReadyDate.setCellValueFactory(new PropertyValueFactory<>("readyDate"));

        List<Request> requestsList = ServiceProviderController.loadSpRequests();
        ObservableList<Request> observableRequests = FXCollections.observableArrayList(requestsList);
        tableRequests.setItems(observableRequests);

        btnSubmit.setOnAction(e -> SubmitPriceAndDate());
    }


    @FXML
    private void SubmitPriceAndDate(){
        Request selected = tableRequests.getSelectionModel().getSelectedItem();

        if (selected == null) {
            Alerts.showErrorAlert("No user was selected", "Error");
            return;
        }

        String price = txtPrice.getText();
        LocalDate date = dateReady.getValue();
        String email = selected.getEmail();

        if(Integer.parseInt(price) < 0){
            Alerts.showErrorAlert("price cannot be negative", "Error");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateStr = date.format(formatter);

        selected.setPrice(price);
        selected.setReadyDate(dateStr);

        ServiceProviderController.UpdateRequsetPrice(price , dateStr , email);
        ServiceProviderController.updateStatus(selected);
        
        List<Request> requestsList = ServiceProviderController.loadSpRequests();
        tableRequests.setItems(FXCollections.observableArrayList(requestsList));

        tableRequests.refresh();

        txtPrice.clear();
        dateReady.setValue(null);

    }
}
