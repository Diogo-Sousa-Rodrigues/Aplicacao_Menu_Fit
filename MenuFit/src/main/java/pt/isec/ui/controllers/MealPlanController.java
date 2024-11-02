package pt.isec.ui.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import pt.isec.model.users.User;
import pt.isec.model.users.UserInitializable;

public class MealPlanController implements UserInitializable {
    private User user;
    SceneSwitcher sceneSwitcher;
    public Button manageRecipesBtn;
    public Button goBackBtn;

    public MealPlanController(){
        this.sceneSwitcher = new SceneSwitcher();
    }

    @Override
    public void initializeUser(User user) {
        this.user = user;
    }

    public void handleGoBackBtnAction(ActionEvent event) {
        sceneSwitcher.switchScene("fxml/MainMenu.fxml", event, user);
    }

    public void handleManageRecipesBtnAction(ActionEvent event) {
        sceneSwitcher.switchScene("fxml/RecipesList.fxml", event, user);
    }
}
