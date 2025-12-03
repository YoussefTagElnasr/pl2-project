package views.admin;
import controllers.AdminController;
import controllers.RegisterController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import models.AdminRequest;
import models.CurrentUser;
import models.Customer;
import models.User;
import view_utils.Alerts;

import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;



public class Admin {
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    @FXML
    private ChoiceBox<String> myChoiceBox;
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
    private Button deleteButton;
    @FXML 
    private Button addButton;

    @FXML
    public void initialize(){
        myChoiceBox.getItems().addAll("sp", "pm", "admin", "customer");
        myChoiceBox.setValue("customer");
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

        deleteButton.setOnAction(e -> deleteSelectedUser());
        addButton.setOnAction(e -> addClickButton());
    }

    @FXML
    private void deleteSelectedUser() {
        User selectedUser = usersTable.getSelectionModel().getSelectedItem();

        if (selectedUser == null) {
            Alerts.showErrorAlert("no user was selected" , "Error");
            return;
        }
        boolean deleted = AdminController.deleteUser(selectedUser.getEmail());

        if (deleted) {
            usersTable.getItems().remove(selectedUser);
            Alerts.showSuccessAlert("User deleted successfully", "Success");
        }
    }

    @FXML
    private void addClickButton(){
        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String selectedRole = myChoiceBox.getValue();
        
        try{
            Customer user = new Customer(email, password, name);
            user.setRole(selectedRole);
            RegisterController.handleRegister(user);
            ArrayList<User> allUsers = AdminController.getAllUsers();
            usersTable.setItems(FXCollections.observableArrayList(allUsers));
            nameField.clear();
            emailField.clear();
            passwordField.clear();
            myChoiceBox.setValue("customer");
        } catch (IllegalArgumentException e){
            Alerts.showErrorAlert(e.getMessage() , "error");
        }
    }
}
