package views.customer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import models.CurrentUser;
import view_utils.SwitchScenes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class customer_oldMsgs {
        @FXML
        private Label title;
        @FXML
        private ListView<String> messagesList;
         ObservableList<String> items = FXCollections.observableArrayList();

        @FXML
        private void initialize() {
            loadMessages();
        }

    private void loadMessages() {
        String currentName = CurrentUser.getInstance().getName();
        List<String> userMessages = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("files/messages.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {

                String[] parts = line.split("\\|", -1);

                if (parts.length >= 4 && parts[0].equals(currentName)) {

                    String messageText = "You: " + parts[2];
                    String responseText = "Response: " +
                            (parts[3].isBlank() ? "—" : parts[3]);

                    userMessages.add(messageText + "\n" + responseText);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        messagesList.setItems(FXCollections.observableArrayList(userMessages));
    }


        @FXML
        private void onHomeClicked() {
            String dashPath ="/views/customer/customer_home.fxml";
            Stage stage = (Stage) title.getScene().getWindow();
            try{
                new SwitchScenes().changeScene(dashPath, stage);
            } catch(IOException e){
                System.out.println(e.getMessage());
            }
        }
}


