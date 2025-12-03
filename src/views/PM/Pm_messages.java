package views.PM;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Pm_messages {

    @FXML
    private ListView<String> messagesListView;

    @FXML
    private Label customerNameLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private TextArea messageLabel;

    @FXML
    private TextArea responseLabel;

    @FXML
    private Button respondButton;

    @FXML
    private Button needResponseButton;

    @FXML
    private Button handledButton;

    private List<Message> messagesData = new ArrayList<>();
    private final String filePath = "C:\\Users\\Joee\\Documents\\GitHub\\pl2-project\\files\\messages.txt";

    // Inner class for message data
    private static class Message {
        String customer;
        String email;
        String content;
        String response;
        boolean read;

        Message(String customer, String email, String content, String response, boolean read) {
            this.customer = customer;
            this.email = email;
            this.content = content;
            this.response = response;
            this.read = read;
        }
    }

    public void initialize() {
        loadMessages();
        updateCounters();

        // Load ONLY unhandled (need response) messages by default
        populateFilteredList(false);

        // Cell factory
        messagesListView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                    return;
                }

                setText(item);
                Message m = getMessageByCustomer(item);

                if (m != null && m.response.trim().isEmpty()) {
                    setStyle("-fx-font-weight:bold; -fx-text-fill:red;");
                } else {
                    setStyle("");
                }
            }
        });

        // On selecting a message
        messagesListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                Message m = getMessageByCustomer(newVal);
                if (m != null) showMessageDetails(m);
            }
        });

        // Respond button
        respondButton.setOnAction(e -> respondToMessage());

        // Filter buttons
        needResponseButton.setOnAction(e -> populateFilteredList(false));
        handledButton.setOnAction(e -> populateFilteredList(true));
    }

    private void loadMessages() {
        messagesData.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Format: customer|email|message|response|read
                String[] parts = line.split("\\|");
                if (parts.length < 5) continue;

                messagesData.add(new Message(
                        parts[0],
                        parts[1],
                        parts[2],
                        parts[3],
                        Boolean.parseBoolean(parts[4])
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
            customerNameLabel.setText("Customer Name: (error reading file)");
        }
    }

    private void updateCounters() {
        long need = messagesData.stream()
                .filter(m -> m.response.trim().isEmpty())
                .count();

        long handled = messagesData.size() - need;

        needResponseButton.setText("Need Response (" + need + ")");
        handledButton.setText("Handled (" + handled + ")");
    }

    private void populateFilteredList(boolean handled) {
        messagesListView.getItems().clear();

        for (Message m : messagesData) {
            boolean hasResponse = !m.response.trim().isEmpty();

            if (handled && hasResponse) {
                messagesListView.getItems().add(m.customer);
            }
            if (!handled && !hasResponse) {
                messagesListView.getItems().add(m.customer);
            }
        }

        if (!messagesListView.getItems().isEmpty()) {
            messagesListView.getSelectionModel().selectFirst();
        }
    }

    private Message getMessageByCustomer(String customer) {
        for (Message m : messagesData) {
            if (m.customer.equals(customer)) return m;
        }
        return null;
    }

    private void showMessageDetails(Message m) {
        customerNameLabel.setText("Customer Name: " + m.customer);
        emailLabel.setText(m.email);
        messageLabel.setText(m.content);
        responseLabel.setText(m.response);

        responseLabel.setStyle(
                m.response.trim().isEmpty()
                        ? "-fx-text-fill:gray;"
                        : "-fx-text-fill:green;"
        );

        if (m.response.trim().isEmpty()) {
            respondButton.setText("Respond");
            respondButton.setPrefWidth(108);
        } else {
            respondButton.setText("Add another response");
            respondButton.setPrefWidth(160);
        }
    }

    private void respondToMessage() {
        String selected = messagesListView.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        Message m = getMessageByCustomer(selected);
        if (m == null) return;

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Respond to Message");
        dialog.setHeaderText("Write response for " + m.customer);
        dialog.setContentText("Response:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(response -> {
            if (response.trim().isEmpty()) return;

            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

            String newResponse = (m.response.trim().isEmpty() ? "" : m.response + "\n")
                    + "[" + timestamp + "] " + response;

            m.response = newResponse;
            m.read = true;

            responseLabel.setText(m.response);
            responseLabel.setStyle("-fx-text-fill:green;");
            respondButton.setText("Add another response");
            respondButton.setPrefWidth(160);

            saveMessagesToFile();
            updateCounters();
            populateFilteredList(false); // return to "Need Response"
            messagesListView.refresh();
        });
    }

    private void saveMessagesToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Message m : messagesData) {
                bw.write(String.join("|",
                        m.customer,
                        m.email,
                        m.content,
                        m.response,
                        String.valueOf(m.read)
                ));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
