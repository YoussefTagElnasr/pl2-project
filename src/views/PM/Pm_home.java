package views.PM;
import controllers.LoginController;
import controllers.PM_RequestsController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.HBox;
import models.Request;
import models.CurrentUser;
import view_utils.Alerts;
import view_utils.SwitchScenes;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

///send & recieve data from backend controller//


public class Pm_home {
    @FXML
    private Label nameLabel;
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
    @FXML
    private Button signOutButton;

    private ObservableList<Request> pendingList;
    private ObservableList<Request> readyList;

    public void initialize() {
        //displayName();
        setupPendingColumn();
        setupReadyColumns();
        loadTables();
        pendingTable.setStyle("-fx-focus-color: transparent;-fx-faint-focus-color: transparent;");
        readyTable.setStyle("-fx-focus-color: transparent;-fx-faint-focus-color: transparent;");
        nameLabel.setText(CurrentUser.getInstance().getName());
        signOutButton.setOnAction(e -> signOutButtonClick());

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
                approveButton.setStyle("-fx-background-color: #10B981; -fx-text-fill: white; -fx-padding: 6 ;");
            }

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
                rejectButton.setStyle("-fx-background-color: #EF4444; -fx-text-fill: white; -fx-padding: 6 10;");
            }
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
                FinalizeButton.setStyle("-fx-background-color:#009688; -fx-text-fill: white; -fx-padding: 6 20;");
            }
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
    private void displayName() {
        models.CurrentUser instance = models.CurrentUser.getInstance();
        if (instance==null) {
            nameLabel.setText("Project Manager");
        }
        else {
            String name = instance.getName();
            if (name == null || name.isEmpty()) {
                nameLabel.setText("Project Manager");
            } else {
                nameLabel.setText(name);
            }
        }
    }
    @FXML
    private void onMessagesClicked() {
        String filePath = "/views/PM/pm_messages.fxml";
        Stage stage = (Stage) pendingTable.getScene().getWindow();
        try{
            new SwitchScenes().changeScene(filePath, stage);
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    @FXML
    private void onReloadClicked() {
        reloadTable();
    }

    @FXML
    private void signOutButtonClick(){
        Stage stage = (Stage) signOutButton.getScene().getWindow();
        try{
            new SwitchScenes().changeScene("/views/login/login.fxml", stage);
        } catch (IOException e){
            System.out.println(e);
        }
    }

}
