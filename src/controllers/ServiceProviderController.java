package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.time.LocalDate;

public class ServiceProviderController {

    @FXML private TableView<Request> tableRequests;
    @FXML private TableColumn<Request, String> colRequestID;
    @FXML private TableColumn<Request, String> colCustomer;
    @FXML private TableColumn<Request, String> colDescription;

    @FXML private TextField txtPrice;
    @FXML private DatePicker dateReady;
    @FXML private Button btnSubmit;

    private final String REQUEST_FILE = "requests.txt";
    private final String RESPONSE_FILE = "sp_responses.txt";

    private ObservableList<Request> requestList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colRequestID.setCellValueFactory(data -> data.getValue().requestIDProperty());
        colCustomer.setCellValueFactory(data -> data.getValue().customerProperty());
        colDescription.setCellValueFactory(data -> data.getValue().descriptionProperty());

        loadRequests();

        btnSubmit.setOnAction(e -> submitResponse());
    }

    private void loadRequests() {
        try (BufferedReader br = new BufferedReader(new FileReader(REQUEST_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                // FORMAT: requestID,customer,description
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    requestList.add(new Request(parts[0], parts[1], parts[2]));
                }
            }
            tableRequests.setItems(requestList);

        } catch (IOException e) {
            System.out.println("Error reading request file.");
        }
    }

    private void submitResponse() {

        Request selected = tableRequests.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Please select a request");
            return;
        }

        String price = txtPrice.getText();
        LocalDate readyDate = dateReady.getValue();

        if (price.isEmpty() || readyDate == null) {
            showAlert("Please enter price and ready date.");
            return;
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(RESPONSE_FILE, true))) {
            pw.println(selected.getRequestID() + "," + price + "," + readyDate);
            showAlert("Response saved successfully!");
        } catch (IOException e) {
            showAlert("Error writing to response file.");
        }

        txtPrice.clear();
        dateReady.setValue(null);
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.show();
    }
}
