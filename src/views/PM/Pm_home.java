package views.PM;
import controllers.PM_RequestsController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.property.SimpleStringProperty;
import models.Request;

import java.util.List;

///send & recieve data from backend controller//


public class Pm_home {
    @FXML
    private Button messagesButton;
    @FXML
    private TableView<Request> pendingTable;
    @FXML
    private TableColumn<Request,String> customerColumn1;
    @FXML
    private TableColumn<Request,String> emailColumn1;
    @FXML
    private TableColumn<Request,String> detailsColumn1;
    @FXML
    private TableColumn<Request,String> statusColumn1;
    @FXML
    private TableColumn<Request,String> priceColumn1;
    @FXML
    private TableColumn<Request,String> dateColumn1;
    @FXML
    private TableColumn<Request,Void> actionColumn1;

    @FXML
    private TableView<Request> readyTable;
    @FXML
    private TableColumn<Request,String> customerColumn2;
    @FXML
    private TableColumn<Request,String> emailColumn2;
    @FXML
    private TableColumn<Request,String> detailsColumn2;
    @FXML
    private TableColumn<Request,String> statusColumn2;
    @FXML
    private TableColumn<Request,String> priceColumn2;
    @FXML
    private TableColumn<Request,String> dateColumn2;
    @FXML
    private TableColumn<Request,Void> actionColumn2;

    private ObservableList<Request> pendingList;
    private ObservableList<Request> readyList;

    public void initialize() {
        setupPendingColumn();
        setupReadyColumns();
        //addApproveButtonToColumns();
        loadTables();
    }
    private void setupPendingColumn() {
        customerColumn1.setCellValueFactory(data-> new SimpleStringProperty(data.getValue().getName()));
        emailColumn1.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));
        detailsColumn1.setCellValueFactory(data-> new SimpleStringProperty(data.getValue().getDetails()));
        statusColumn1.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getStatus()));
        priceColumn1.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getPrice()));
        dateColumn1.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getReadyDate()));

        customerColumn1.setStyle("-fx-alignment: CENTER;");
        emailColumn1.setStyle("-fx-alignment: CENTER;");
        detailsColumn1.setStyle("-fx-alignment: CENTER;");
        statusColumn1.setStyle("-fx-alignment: CENTER;");
        priceColumn1.setStyle("-fx-alignment: CENTER;");
        dateColumn1.setStyle("-fx-alignment: CENTER;");
    }
    private void setupReadyColumns() {
        customerColumn2.setCellValueFactory(data-> new SimpleStringProperty(data.getValue().getName()));
        emailColumn2.setCellValueFactory(data-> new SimpleStringProperty(data.getValue().getEmail()));
        detailsColumn2.setCellValueFactory(data-> new SimpleStringProperty(data.getValue().getDetails()));
        statusColumn2.setCellValueFactory(data-> new SimpleStringProperty(data.getValue().getStatus()));
        priceColumn2.setCellValueFactory(data-> new SimpleStringProperty(data.getValue().getPrice()));
        dateColumn2.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getReadyDate()));

        customerColumn2.setStyle("-fx-alignment: CENTER;");
        emailColumn2.setStyle("-fx-alignment: CENTER;");
        detailsColumn2.setStyle("-fx-alignment: CENTER;");
        statusColumn2.setStyle("-fx-alignment: CENTER;");
        priceColumn2.setStyle("-fx-alignment: CENTER;");
        dateColumn2.setStyle("-fx-alignment: CENTER;");
    }
    private void loadTables() {
        List<Request> pending = PM_RequestsController.getPendingRequests();
        List<Request> ready = PM_RequestsController.getReadyRequests();

        pendingList = FXCollections.observableList(pending);
        readyList = FXCollections.observableList(ready);

        pendingTable.setItems(pendingList);
        readyTable.setItems(readyList);
    }
    @FXML
    private void onMessagesClicked() {
        // Temporary: just to avoid crash while we test tables
        System.out.println("Messages clicked (stub)");
    }

}
