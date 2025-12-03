package views.admin;
import controllers.AdminController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.AdminRequest;
import models.CurrentUser;
import models.User;

import java.util.ArrayList;
import javafx.scene.control.Label;



public class Admin {
    @FXML
    private TableView<AdminRequest> requstsTable;
    @FXML
    private TableColumn<AdminRequest, String> customerColumn;
    @FXML
    private TableColumn<AdminRequest, String> emailColumn;
    @FXML
    private TableColumn<AdminRequest, String> detailColumn;
    @FXML
    private TableColumn<AdminRequest, String> statusColumn;
    @FXML
    private TableColumn<AdminRequest, Integer> priceColumn;
    @FXML
    private TableColumn<AdminRequest, String> readyDateColumn;
    @FXML
    private TableView<User> usersTable;
    @FXML
    private TableColumn<User, String> nameColumn;
    @FXML
    private TableColumn<User, String> userEmailColumn;
    @FXML
    private TableColumn<User, String> passwordColumn;
    @FXML
    private TableColumn<User, String> roleColumn;
    @FXML
    private Label adminName;

    @FXML
    public void initialize(){
        customerColumn.setCellValueFactory(new PropertyValueFactory<>("customer"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        detailColumn.setCellValueFactory(new PropertyValueFactory<>("detail"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        readyDateColumn.setCellValueFactory(new PropertyValueFactory<>("readyDate"));

        ArrayList<AdminRequest> requestsList = AdminController.getAdminRequests();
        ObservableList<AdminRequest> observableRequests = FXCollections.observableArrayList(requestsList);

        requstsTable.setItems(observableRequests);


        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        userEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        ArrayList<User> usersList = AdminController.getAllUsers();
        usersTable.setItems(FXCollections.observableArrayList(usersList));

        if (CurrentUser.getInstance() != null) {
        adminName.setText(CurrentUser.getInstance().getName());
        }
    }
}
