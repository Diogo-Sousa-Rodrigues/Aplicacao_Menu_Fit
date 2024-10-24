package pt.isec.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {
    SceneSwitcher sceneSwitcher;
    public MainMenuController(){
        this.sceneSwitcher = new SceneSwitcher();
    }
    @FXML
    private void handleButtonAction(ActionEvent event) {
        /*try {

            Parent healthAndDietaryPage = FXMLLoader.load(getClass().getResource("/UI/fxml/HealthAndDietaryRestrictions_1.fxml"));
            Scene healthAndDietaryScene = new Scene(healthAndDietaryPage);


            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();


            appStage.setScene(healthAndDietaryScene);
            appStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        sceneSwitcher.switchScene("fxml/HealthAndDietaryRestrictions_1.fxml", event);
    }


}