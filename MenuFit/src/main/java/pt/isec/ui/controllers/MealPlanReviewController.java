package pt.isec.ui.controllers;

import javafx.event.ActionEvent;
import pt.isec.model.users.User;
import pt.isec.model.users.UserInitializable;

public class MealPlanReviewController implements UserInitializable {
    private User user;
    SceneSwitcher sceneSwitcher;
    public MealPlanReviewController(){
        sceneSwitcher = new SceneSwitcher();
    }

    public void btnBackHandler(ActionEvent event) {
        sceneSwitcher.switchScene("fxml/TimeAndBudget.fxml", event, user);
    }

    public void btnAcceptHandler(ActionEvent event) {
        sceneSwitcher.switchScene("fxml/MainMenu.fxml", event, user);
    }

    @Override
    public void initializeUser(User user) {
        this.user = user;
    }
}
