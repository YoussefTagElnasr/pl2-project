package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import models.Request;
import services.RequestServices;

import java.time.LocalDate;
import java.util.List;

public class ServiceProviderController {

    @FXML
    private Button btnSubmit;

    @FXML
    private TableColumn<Request, String> colCustomer;

    @FXML
    private TableColumn<Request, String> colDescription;

    @FXML
    private TableColumn<Request, String> colRequestID;

    @FXML
    private TableColumn<Request, String> colStatus;

    @FXML
    private DatePicker dateReady;

    @FXML
    private TableView<Request> tableRequests;

    @FXML
    private TextField txtPrice;

    private ObservableList<Request> tableList;


    @FXML
    public void initialize() {
        setupTable();
        loadTable();
    }


    private void setupTable() {
        colCustomer.setCellValueFactory(data -> 
            new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));

        colDescription.setCellValueFactory(data -> 
            new javafx.beans.property.SimpleStringProperty(data.getValue().getDetails()));

        colStatus.setCellValueFactory(data -> 
            new javafx.beans.property.SimpleStringProperty(data.getValue().getStatus()));

        colRequestID.setCellValueFactory(data -> 
            new javafx.beans.property.SimpleStringProperty(data.getValue().getEmail()));
            // مفيش RequestID في الكلاس بتاعك فهعرض الايميل
    }


    private void loadTable() {
        List<Request> list = RequestServices.getRequests("approved");
        tableList = FXCollections.observableArrayList(list);
        tableRequests.setItems(tableList);
    }

}
