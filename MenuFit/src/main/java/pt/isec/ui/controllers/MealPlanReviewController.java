package pt.isec.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import pt.isec.model.meals.*;
import pt.isec.model.users.BasicUser;
import pt.isec.model.users.UserInitializable;
import pt.isec.persistence.BDManager;
import pt.isec.builders.InstanceBuilder;

import java.util.*;

public class MealPlanReviewController implements UserInitializable {
    private BasicUser user;
    private BDManager bdManager;
    private SceneSwitcher sceneSwitcher;
    private Optional<MealPlan> mealPlan;

    @FXML
    private GridPane weekGridPane;

    private Map<String, VBox> dailyMealPanes = new HashMap<>();

    public MealPlanReviewController() {
        sceneSwitcher = new SceneSwitcher();
    }

    /**
     * Initializes the layout for each day of the week in a GridPane, arranging them in a 3x3 grid.
     * Each day gets a VBox with a label displaying the day name in bold, and the VBoxes are styled with a black border.
     * Sunday is placed in the center column.
     */
    @FXML
    public void initialize() {
        String[] daysOfWeek = {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"};
        for (int i = 0; i < daysOfWeek.length; i++) {
            String day = daysOfWeek[i];

            VBox dailyPane = new VBox();
            dailyPane.setSpacing(5);
            dailyPane.setPadding(new Insets(8));
            dailyPane.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
            dailyMealPanes.put(day, dailyPane);

            int column = i == 6 ? 1 : i % 3;
            int row = i / 3;
            weekGridPane.add(dailyPane, column, row);

            Label dayLabel = new Label(day);
            dayLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
            dailyPane.getChildren().add(dayLabel);
        }
    }


    @Override
    public void initializeUser(BasicUser user, BDManager bdManager) {
        this.user = user;
        this.bdManager = bdManager;
        InstanceBuilder instanceBuilder = new InstanceBuilder();
        String json = instanceBuilder.getSampleMealPlanJSON();
        mealPlan = instanceBuilder.fromJson(json, MealPlan.class);
        addMeals(mealPlan);

    }

    public void addMeals(Optional<MealPlan> mealPlan) {
        if (mealPlan.isPresent()) { // Verifica se o Optional contém um valor
            MealPlan plan = mealPlan.get(); // Obtém o valor do Optional

            for (Meal meal : plan.getMeals()) {
                VBox dailyPane = dailyMealPanes.get(meal.getDate().getDayOfWeek().toString());

                Text typeText = new Text(meal.getType().toString() + ": ");
                typeText.setStyle("-fx-font-size: 13; -fx-font-weight: bold;");

                Text recipeText = new Text(
                        meal.getRecipe().getName() + "\n" +
                                meal.getRecipe().getPrep().toMinutes() + " min, " +
                                meal.getRecipe().getCalories() + " kcal"
                );
                recipeText.setStyle("-fx-font-size: 13; -fx-padding: 2 0 2 0;");

                TextFlow mealTextFlow = new TextFlow(typeText, recipeText);
                dailyPane.getChildren().add(mealTextFlow);
            }
        } else {
            // Tratamento caso o Optional não contenha valor (opcional)
            System.out.println("MealPlan não está presente.");
        }
    }

    public void btnBackHandler(ActionEvent event) {
        sceneSwitcher.switchScene("fxml/TimeAndBudget.fxml", event, user);
    }

    public void btnAcceptHandler(ActionEvent event) {
        mealPlan.get().setIDUser(user.getIdUser());
        bdManager.saveMealPlan(mealPlan);

        user.setCurrentMealIndex(0);
        user.getHealthData().setDailyCalorieSum(0);
        sceneSwitcher.switchScene("fxml/MainMenu.fxml", event, user);
    }
}
