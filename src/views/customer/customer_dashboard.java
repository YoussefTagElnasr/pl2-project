package views.customer;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.*;
import view_utils.SwitchScenes;

import java.io.IOException;
import java.util.List;


public class customer_dashboard {
    @FXML
    private TextField nameField;
    @FXML
    private TextField mailField;
    @FXML
    private Button homeBtn;


    @FXML
    private TableView<Request> myTickets;
    @FXML
    private TableColumn<Request,String> eventCol;
    @FXML
    private TableColumn<Request,String> locCol;
    @FXML
    private TableColumn<Request,String> dateCol;
    @FXML
    private TableColumn<Request,String> statusCol;
    @FXML
    private TableColumn<Request,Void> actionCol;

    private ObservableList<Request> myList;


    public void initialize() {
        nameField.setText(models.CurrentUser.getInstance().getName());
        mailField.setText(models.CurrentUser.getInstance().getEmail());
        setupColumns();
        loadTables();
        myTickets.setStyle("-fx-focus-color: transparent;-fx-faint-focus-color: transparent;");
    }
    private void setupColumns() {
        eventCol.setCellValueFactory(data-> new SimpleStringProperty(data.getValue().getName()));
        locCol.setCellValueFactory(data-> new SimpleStringProperty(data.getValue().getEmail()));
        dateCol.setCellValueFactory(data-> new SimpleStringProperty(data.getValue().getDetails()));
        statusCol.setCellValueFactory(data-> new SimpleStringProperty(data.getValue().getStatus()));
        actionCol.setCellFactory(data->new TableCell<Request, Void>() {
            private final Button cancelButton = new Button("Cancel");
            {
                cancelButton.setStyle("-fx-background-color:#EF4444; -fx-text-fill: white; -fx-padding: 6 20;");
            }
            {
                cancelButton.setOnAction(event -> {
                    Request cancelledTicket = getTableView().getItems().get(getIndex());

                    services.CustomerServices.cancelTicket(cancelledTicket);

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
                    setGraphic(cancelButton);
                }

            }

        });

        eventCol.setStyle("-fx-alignment: CENTER;");
        locCol.setStyle("-fx-alignment: CENTER;");
        dateCol.setStyle("-fx-alignment: CENTER;");
        statusCol.setStyle("-fx-alignment: CENTER;");
        actionCol.setStyle("-fx-alignment: CENTER;");

    }
    private void reloadTable() {
        myTickets.getItems().setAll(services.CustomerServices.getMyTickets(models.CurrentUser.getInstance().getName()));
    }
    private void loadTables() {
        List<Request> tickets = services.CustomerServices.getMyTickets(models.CurrentUser.getInstance().getName());

        myList = FXCollections.observableList(tickets);

        myTickets.setItems(myList);

    }
    @FXML
    private void onHomeClicked() {
        String dashPath ="/views/customer/customer_home.fxml";
        Stage stage = (Stage) nameField.getScene().getWindow();
        try{
            new SwitchScenes().changeScene(dashPath, stage);
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}
