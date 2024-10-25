package pt.isec.ui.controllers;

import javafx.event.ActionEvent;

public class MealPlanReviewController {
    SceneSwitcher sceneSwitcher;
    public MealPlanReviewController(){
        sceneSwitcher = new SceneSwitcher();
    }

    public void btnBackHandler(ActionEvent event) {
        sceneSwitcher.switchScene("fxml/TimeAndBudget.fxml", event);
    }

    public void btnAcceptHandler(ActionEvent event) {
        sceneSwitcher.switchScene("fxml/MainMenu.fxml", event);
    }
}
