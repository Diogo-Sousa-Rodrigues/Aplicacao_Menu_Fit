package pt.isec.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import pt.isec.model.users.User;
import pt.isec.model.users.UserInitializable;

public class RecipeController implements UserInitializable {
    private User user;
    SceneSwitcher sceneSwitcher;

    @FXML
    public Button goBackBtn;

    @FXML
    public Label recipeNameLabel;

    @FXML
    public Label recipeTextLabel;

    public RecipeController(){
        this.sceneSwitcher = new SceneSwitcher();
    }

    public void handleGoBackBtnAction(ActionEvent event) {
        sceneSwitcher.switchScene("fxml/RecipesList.fxml", event, user);
    }

    @Override
    public void initializeUser(User user) {
        this.user = user;
    }
}
