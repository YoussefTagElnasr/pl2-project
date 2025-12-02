package views.PM;
import controllers.PM_RequestsController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.HBox;
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
        loadTables();
        pendingTable.setStyle("-fx-focus-color: transparent;-fx-faint-focus-color: transparent;");
        readyTable.setStyle("-fx-focus-color: transparent;-fx-faint-focus-color: transparent;");

    }
    private  void reloadTables(){
        pendingTable.getItems().setAll(controllers.PM_RequestsController.getRequests("pending"));
        readyTable.getItems().setAll(controllers.PM_RequestsController.getRequests("ready"));
    }
    private void setupPendingColumn() {
        customerColumn1.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        emailColumn1.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));
        detailsColumn1.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDetails()));
        statusColumn1.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStatus()));
        priceColumn1.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPrice()));
        dateColumn1.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getReadyDate()));
        actionColumn1.setCellFactory(data -> new TableCell<Request, Void>() {


            private final Button approveButton = new Button("Approve");

            {
                approveButton.setOnAction(event -> {
                    Request approvedRequest = getTableView().getItems().get(getIndex());

                    controllers.PM_RequestsController.processRequest(approvedRequest,"approved");

                    readyTable.getItems().setAll(controllers.PM_RequestsController.getRequests("ready"));
                    pendingTable.getItems().setAll(controllers.PM_RequestsController.getRequests("pending"));
                });
            }

            private final Button rejectButton = new Button("Reject");

            {
                rejectButton.setOnAction(event -> {
                    Request rejectedRequest = getTableView().getItems().get(getIndex());

                    controllers.PM_RequestsController.processRequest(rejectedRequest,"rejected");

                    reloadTable();
                });
            }

            private final HBox container = new HBox(20,approveButton,rejectButton);
            {
                container.setAlignment(Pos.CENTER);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item,empty);
                if (empty) {
                    setGraphic(null);
                }
                else {
                    setGraphic(container);
                    }
                }


        });


        customerColumn1.setStyle("-fx-alignment: CENTER;");
        emailColumn1.setStyle("-fx-alignment: CENTER;");
        detailsColumn1.setStyle("-fx-alignment: CENTER;");
        statusColumn1.setStyle("-fx-alignment: CENTER;");
        priceColumn1.setStyle("-fx-alignment: CENTER;");
        dateColumn1.setStyle("-fx-alignment: CENTER;");
        actionColumn1.setStyle("-fx-alignment: CENTER;");

    }
    private void setupReadyColumns() {
        customerColumn2.setCellValueFactory(data-> new SimpleStringProperty(data.getValue().getName()));
        emailColumn2.setCellValueFactory(data-> new SimpleStringProperty(data.getValue().getEmail()));
        detailsColumn2.setCellValueFactory(data-> new SimpleStringProperty(data.getValue().getDetails()));
        statusColumn2.setCellValueFactory(data-> new SimpleStringProperty(data.getValue().getStatus()));
        priceColumn2.setCellValueFactory(data-> new SimpleStringProperty(data.getValue().getPrice()));
        dateColumn2.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getReadyDate()));
        actionColumn2.setCellFactory(data->new TableCell<Request, Void>() {
            private final Button FinalizeButton = new Button("Finalize");

            {
                FinalizeButton.setOnAction(event -> {
                    Request finalizedRequest = getTableView().getItems().get(getIndex());

                    controllers.PM_RequestsController.processRequest(finalizedRequest, "sent");

                    reloadTable();
                });
            }
               @Override
               protected void updateItem(Void item,boolean empty){
                    super.updateItem(item,empty);
                    if (empty) {
                        setGraphic(null);
                    }
                    else  {
                        setGraphic(FinalizeButton);
                    }

            }

        });

        customerColumn2.setStyle("-fx-alignment: CENTER;");
        emailColumn2.setStyle("-fx-alignment: CENTER;");
        detailsColumn2.setStyle("-fx-alignment: CENTER;");
        statusColumn2.setStyle("-fx-alignment: CENTER;");
        priceColumn2.setStyle("-fx-alignment: CENTER;");
        dateColumn2.setStyle("-fx-alignment: CENTER;");
        actionColumn2.setStyle("-fx-alignment: CENTER;");
    }
    private void loadTables() {
        List<Request> pending = PM_RequestsController.getRequests("pending");
        List<Request> ready = PM_RequestsController.getRequests("ready");

        pendingList = FXCollections.observableList(pending);
        readyList = FXCollections.observableList(ready);

        pendingTable.setItems(pendingList);
        readyTable.setItems(readyList);
    }
    private void reloadTable() {
        pendingTable.getItems().setAll(controllers.PM_RequestsController.getRequests("pending"));
        readyTable.getItems().setAll(controllers.PM_RequestsController.getRequests("ready"));
    }
    @FXML
    private void onMessagesClicked() {
        // Temporary: just to avoid crash while we test tables
        System.out.println("Messages clicked (stub)");
    }
    @FXML
    private void onReloadClicked() {
        reloadTable();
    }

}
