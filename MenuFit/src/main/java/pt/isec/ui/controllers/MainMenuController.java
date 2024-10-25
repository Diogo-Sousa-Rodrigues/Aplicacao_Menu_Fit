package pt.isec.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import pt.isec.model.users.User;

import java.io.IOException;

public class MainMenuController {
    public Label mealTypeLabel;
    public CheckBox mealCheckBox;
    public Label recipeNameLabel;
    public Label recipeCaloriesLabel;
    public Label recipeTimeLabel;
    public ImageView recipeImage;

    private User user;
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

    /*@FXML
    public void initialize() {
        //TODO para mostrar logo a receita suposta quando se entra nesta scene
    }*/

    public void mealPreviewChangeHandler(ActionEvent event) {
        //TODO mudar a receita em display quando a checkbox é selecionada
    }

    public void initializeUser(User user) {
        this.user = user;
    }
}