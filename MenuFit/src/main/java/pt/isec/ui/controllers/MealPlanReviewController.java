package pt.isec.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import pt.isec.ai.CommonLLM;
import pt.isec.ai.GroqLLM;
import pt.isec.builders.MealPlanBuilder;
import pt.isec.model.meals.*;
import pt.isec.model.users.BasicUser;
import pt.isec.model.users.GenericUser;
import pt.isec.model.users.UserInitializable;
import pt.isec.persistence.BDManager;
import pt.isec.builders.InstanceBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class MealPlanReviewController implements UserInitializable {
    private BasicUser user;
    private BDManager bdManager;
    private SceneSwitcher sceneSwitcher;
    //private Optional<MealPlan> mealPlan;
    private Optional<MealPlan> mealPlanOpt;
    @FXML
    private GridPane weekGridPane;

    private Map<String, VBox> dailyMealPanes = new HashMap<>();

    public MealPlanReviewController() {
        sceneSwitcher = new SceneSwitcher();
    }

    /**
     * Gera a ordem dos dias da semana começando por um dia específico.
     *
     * @param startDay O dia de início, como "MONDAY".
     * @return Uma lista dos dias na ordem reorganizada.
     */
    private List<String> getOrderedDays(String startDay) {
        List<String> daysOfWeek = Arrays.asList("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY");
        int startIndex = daysOfWeek.indexOf(startDay);
        if (startIndex == -1) return daysOfWeek; // Caso inválido, retorna a ordem padrão.

        List<String> orderedDays = new ArrayList<>();
        orderedDays.addAll(daysOfWeek.subList(startIndex, daysOfWeek.size())); // Parte após o início.
        orderedDays.addAll(daysOfWeek.subList(0, startIndex)); // Parte antes do início.
        return orderedDays;
    }

    /**
     * Initializes the layout for each day of the week in a GridPane, arranging them in a 3x3 grid.
     * Each day gets a VBox with a label displaying the day name in bold, and the VBoxes are styled with a black border.
     * Sunday is placed in the center column.
     */
    @FXML
    public void initialize() {
        String startDay = LocalDate.now().getDayOfWeek().toString(); // Dia atual como início.
        List<String> orderedDays = getOrderedDays(startDay);

        for (int i = 0; i < orderedDays.size(); i++) {
            String day = orderedDays.get(i);

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

        CommonLLM llm = GroqLLM.getInstance();
        llm.setApiKey("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");

        LocalDate date = LocalDateTime.now().toLocalDate();

        LocalDate begin = date.plusDays(0);
        LocalDate end = date.plusDays(7);

        MealPlanBuilder mealPlanBuilder = MealPlanBuilder.getInstance();
        mealPlanOpt = mealPlanBuilder.getMealPlan(user, begin, end, llm);

        if (mealPlanOpt.isPresent()) {
            System.out.println("Meal Plan generated successfully.");

            var mealPlan = mealPlanOpt.get();
            addMeals(mealPlan);
        } else {
            System.out.println("Unable to generate Meal Plan.");
        }
    }

    public void addMeals(MealPlan mealPlan) {
        for (Meal meal : mealPlan.getMeals()) {
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
    }

    public void btnBackHandler(ActionEvent event) {
        sceneSwitcher.switchScene("fxml/TimeAndBudget.fxml", event, user);
    }

    public void btnAcceptHandler(ActionEvent event) {
        mealPlanOpt.get().setIDUser(user.getIdUser());
        bdManager.saveMealPlan(mealPlanOpt);
        bdManager.saveDietaryRestrictions(user.getHealthData(), user.getIdUser());

        user.getHealthData().setDailyCalorieSum(0);
        sceneSwitcher.switchScene("fxml/MainMenu.fxml", event, user);
    }
}
