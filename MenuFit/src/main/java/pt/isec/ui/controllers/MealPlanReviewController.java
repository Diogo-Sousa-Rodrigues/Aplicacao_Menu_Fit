package pt.isec.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import pt.isec.model.meals.MealType;
import pt.isec.model.users.User;
import pt.isec.model.users.UserInitializable;
import java.util.HashMap;
import java.util.Map;

public class MealPlanReviewController implements UserInitializable {
    private User user;
    private SceneSwitcher sceneSwitcher;

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
        String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
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
    public void initializeUser(User user) {
        this.user = user;

        // Exemplo de como adicionar refeições para testar
        addMeal("Monday", MealType.Breakfast, "Omelette", "10 min", "200 cal");
        addMeal("Monday", MealType.Dinner, "Pasta", "20 min", "500 cal");
        addMeal("Tuesday", MealType.Breakfast, "Omelette", "10 min", "200 cal");
        addMeal("Tuesday", MealType.Lunch, "Grilled Chicken Salad", "15 min", "350 cal");
        addMeal("Tuesday", MealType.Dinner, "Grilled Chicken Salad", "15 min", "350 cal");
        addMeal("Tuesday", MealType.Snack, "Grilled Chicken Salad", "15 min", "350 cal");
        addMeal("Tuesday", MealType.Snack, "Grilled Chicken Salad", "15 min", "350 cal");

    }

    /**
     * Adds a meal to the specified day, displaying the meal type in bold and other details in normal font.
     * The meal is added to the corresponding day's VBox in the GridPane.
     *
     * @param day The day to add the meal to.
     * @param type The type of meal.
     * @param recipeName The name of the meal.
     * @param time The preparation time for the meal.
     * @param calories The calorie count of the meal.
     */
    public void addMeal(String day, MealType type, String recipeName, String time, String calories) {
        if (!dailyMealPanes.containsKey(day)) return;

        VBox dailyPane = dailyMealPanes.get(day);

        Text typeText = new Text(type + ": ");
        typeText.setStyle("-fx-font-size: 13; -fx-font-weight: bold;");

        Text recipeText = new Text(recipeName + "\n" + time + ", " + calories);
        recipeText.setStyle("-fx-font-size: 13; -fx-padding: 2 0 2 0;");

        TextFlow mealTextFlow = new TextFlow(typeText, recipeText);

        dailyPane.getChildren().add(mealTextFlow);
    }

    public void btnBackHandler(ActionEvent event) {
        sceneSwitcher.switchScene("fxml/TimeAndBudget.fxml", event, user);
    }

    public void btnAcceptHandler(ActionEvent event) {
        sceneSwitcher.switchScene("fxml/MainMenu.fxml", event, user);
    }
}
