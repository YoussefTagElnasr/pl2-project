import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import views.PM.Pm_messages;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        try {
            // Load FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/PM/pm_messages.fxml"));
            // The controller is automatically picked from fx:controller in FXML
            Parent root = loader.load();

            // Create scene
            Scene scene = new Scene(root);
            stage.setScene(scene);

            // Stage settings
            stage.setTitle("Project Manager - Messages");
            stage.setMaximized(true);
            stage.setResizable(true);

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
