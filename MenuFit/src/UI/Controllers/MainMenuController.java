package UI.Controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainMenuController {

    @FXML
    private void handleButtonAction(ActionEvent event) {
        try {
            // Carrega o layout FXML
            Parent healthAndDietaryPage = FXMLLoader.load(getClass().getResource("/UI/fxml/HealthAndDietaryRestrictions_1.fxml"));
            Scene healthAndDietaryScene = new Scene(healthAndDietaryPage);

            // Pega a Stage atual a partir do evento
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Define a nova cena no palco
            appStage.setScene(healthAndDietaryScene);
            appStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}