package views.PM;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseEvent;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Pm_messages {

    @FXML
    private ListView<String> messagesList;

    @FXML
    private Label customerEmailLabel;

    @FXML
    private TextArea messageContentArea;

    @FXML
    private Button respondButton;

    // To store message objects
    private ArrayList<Message> allMessages = new ArrayList<>();

    // Inner class to store message info
    private static class Message {
        String name;
        String email;
        String content;

        Message(String name, String email, String content) {
            this.name = name;
            this.email = email;
            this.content = content;
        }

        @Override
        public String toString() {
            return name + " (" + email + ")";
        }
    }

    // Initialize method called automatically by JavaFX
    @FXML
    public void initialize() {
        loadMessagesFromFile("../../../files/messages.txt");

        ObservableList<String> items = FXCollections.observableArrayList();
        for (Message msg : allMessages) {
            items.add(msg.toString());  // Display name + email in ListView
        }
        messagesList.setItems(items);

        // When user clicks a message, show details
        messagesList.setOnMouseClicked((MouseEvent event) -> {
            int selectedIndex = messagesList.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                Message selectedMessage = allMessages.get(selectedIndex);
                customerEmailLabel.setText(selectedMessage.email);
                messageContentArea.setText(selectedMessage.content);
            }
        });
    }

    private void loadMessagesFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Assuming each line format: name,email,message
                String[] parts = line.split(",", 3);
                if (parts.length == 3) {
                    allMessages.add(new Message(parts[0], parts[1], parts[2]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
