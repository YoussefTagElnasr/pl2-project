package view_utils;

import javafx.scene.control.Alert;

public class Alerts {
    public static void showAlert(String message , String title) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
