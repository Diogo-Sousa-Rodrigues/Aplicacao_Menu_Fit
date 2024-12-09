package pt.isec.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import pt.isec.model.meals.ExtraMeal;
import pt.isec.model.meals.Meal;
import pt.isec.model.users.BasicUser;
import pt.isec.model.users.User;
import pt.isec.model.users.UserInitializable;
import pt.isec.persistence.BDManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ExtraMealController implements UserInitializable {
    private BasicUser user;
    SceneSwitcher sceneSwitcher;

    @FXML
    TextField inputMealName;
    @FXML
    TextField inputCalories;

    public ExtraMealController(){
        this.sceneSwitcher = new SceneSwitcher();
    }

    @FXML
    void handlePrevious(ActionEvent event) throws IOException {
        sceneSwitcher.switchScene("fxml/MainMenu.fxml", event, null, null);
    }

    @FXML
    void handleAdd(ActionEvent event) throws IOException {
        String mealName = inputMealName.getText();
        int calories = Integer.parseInt(inputCalories.getText());

        ExtraMeal extraMeal = new ExtraMeal(mealName, calories);
        this.user.getMealPlan().putExtraMeal(extraMeal);

        sceneSwitcher.switchScene("fxml/MainMenu.fxml", event, user);
    }

    @Override
    public void initializeUser(BasicUser user, BDManager bdManager) {
        this.user = user;
    }
}
