package views.PM;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
    @FXML
    private Button markUnhandledButton;
    @FXML
    private Button backButton;
    private List<Message> messagesData = new ArrayList<>();
    private final String filePath = "files/messages.txt";
    private boolean showingHandled = false;
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
        populateFilteredList(false);
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
                if (m != null) {
                    setStyle(!m.read ? "-fx-font-weight:bold; -fx-text-fill:red;" : "");
                }
            }
        });

        messagesListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                Message m = getMessageByCustomer(newVal);
                if (m != null) {
                    showMessageDetails(m);
                    markUnhandledButton.setDisable(!m.read);
                }
            }
        });
        respondButton.setOnAction(e -> respondToMessage());
        markUnhandledButton.setOnAction(e -> markAsUnhandled());
        needResponseButton.setOnAction(e -> populateFilteredList(false));
        handledButton.setOnAction(e -> populateFilteredList(true));
        backButton.setOnAction(e -> goHome());
    }

    private void loadMessages() {
        messagesData.clear();
        File file = new File(filePath);

        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                customerNameLabel.setText("Error creating messages file");
                return;
            }
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length < 5) continue;
                messagesData.add(new Message(parts[0], parts[1], parts[2], parts[3], Boolean.parseBoolean(parts[4])));
            }
        } catch (IOException e) {
            e.printStackTrace();
            customerNameLabel.setText("Error reading messages file");
        }
    }

    private void updateCounters() {
        long need = messagesData.stream().filter(m -> !m.read).count();
        long handled = messagesData.stream().filter(m -> m.read).count();
        needResponseButton.setText("Need Response (" + need + ")");
        handledButton.setText("Handled (" + handled + ")");
    }

    private void populateFilteredList(boolean handled) {
        showingHandled = handled;
        messagesListView.getItems().clear();

        List<Message> filtered = new ArrayList<>();
        for (Message m : messagesData) {
            if (handled && m.read) filtered.add(m);
            if (!handled && !m.read) filtered.add(m);
        }
        if (handled) {
            filtered.sort((m1, m2) -> getLastResponseTime(m2).compareTo(getLastResponseTime(m1)));
        }
        for (Message m : filtered) {
            messagesListView.getItems().add(m.customer);
        }
        if (!messagesListView.getItems().isEmpty()) {
            messagesListView.getSelectionModel().selectFirst();
        }
    }

    private LocalDateTime getLastResponseTime(Message m) {
        if (m.response.trim().isEmpty()) return LocalDateTime.MIN;
        String[] lines = m.response.split("\n");
        String lastLine = lines[lines.length - 1];
        try {
            String timestampStr = lastLine.substring(1, 17);
            return LocalDateTime.parse(timestampStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } catch (Exception e) {
            return LocalDateTime.MIN;
        }
    }

    private Message getMessageByCustomer(String customer) {
        for (Message m : messagesData)
            if (m.customer.equals(customer)) return m;
        return null;
    }

    private void showMessageDetails(Message m) {
        customerNameLabel.setText("Customer Name: " + m.customer);
        emailLabel.setText(m.email);
        messageLabel.setText(m.content);
        responseLabel.setText(m.response);
        responseLabel.setStyle(!m.read ? "-fx-text-fill:gray;" : "-fx-text-fill:green;");

        if (!m.read) {
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

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            m.response = (m.response.trim().isEmpty() ? "" : m.response + "\n")
                    + "[" + timestamp + "] " + response;
            m.read = true;
            responseLabel.setText(m.response);
            responseLabel.setStyle("-fx-text-fill:green;");
            respondButton.setText("Add another response");
            respondButton.setPrefWidth(160);
            saveMessagesToFile();
            updateCounters();
            populateFilteredList(showingHandled);
            messagesListView.refresh();
        });
    }

    private void markAsUnhandled() {
        String selected = messagesListView.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        Message m = getMessageByCustomer(selected);
        if (m == null || !m.read) return;

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Mark as Unhandled");
        dialog.setHeaderText("Provide reason for moving this message back to Need Response");
        dialog.setContentText("Reason:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(reason -> {
            if (reason.trim().isEmpty()) return;

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            m.response = m.response + "\n[" + timestamp + "] Moved to Need Response for: " + reason;
            m.read = false;
            responseLabel.setText(m.response);
            responseLabel.setStyle("-fx-text-fill:gray;");
            saveMessagesToFile();
            updateCounters();
            populateFilteredList(showingHandled);
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
    private void goHome() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/PM/Pm_home.fxml"));
            javafx.stage.Stage stage = (javafx.stage.Stage) backButton.getScene().getWindow();
            double width = stage.getWidth();
            double height = stage.getHeight();
            javafx.scene.Scene newScene = new javafx.scene.Scene(root, width, height);
            stage.setScene(newScene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

