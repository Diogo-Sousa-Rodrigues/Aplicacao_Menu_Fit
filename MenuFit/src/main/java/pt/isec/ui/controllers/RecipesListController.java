package pt.isec.ui.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import pt.isec.model.users.User;
import pt.isec.model.users.UserInitializable;

public class RecipesListController implements UserInitializable {
    private User user;
    SceneSwitcher sceneSwitcher;
    public Button goBackBtn;
    public Button viewRecipeBtn;

    public RecipesListController(){
        this.sceneSwitcher = new SceneSwitcher();
    }

    public void handleGoBackBtnAction(ActionEvent event) {
        sceneSwitcher.switchScene("fxml/MealPlan.fxml", event, user);
    }

    public void handleViewRecipeBtn(ActionEvent event) {
        sceneSwitcher.switchScene("fxml/Recipe.fxml", event, user);
    }

    @Override
    public void initializeUser(User user) {
        this.user = user;
    }
}
