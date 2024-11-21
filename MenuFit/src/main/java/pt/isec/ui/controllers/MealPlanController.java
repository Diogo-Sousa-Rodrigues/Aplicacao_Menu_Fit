package pt.isec.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import pt.isec.model.meals.*;
import pt.isec.model.users.User;
import pt.isec.model.users.UserInitializable;
import pt.isec.persistence.BDManager;
import pt.isec.persistence.EphemeralStore;

import java.time.Duration;
import java.util.List;

public class MealPlanController implements UserInitializable {
    private User user;
    private SceneSwitcher sceneSwitcher;


    @FXML
    private VBox mealsContainer;

    public MealPlanController() {
        this.sceneSwitcher = new SceneSwitcher();
    }

    @Override
    public void initializeUser(User user, BDManager bdManager) {
        this.user = user;
        initializeDailyMealsPreview();
    }

    public void handleGoBackBtnAction(ActionEvent event) {
        sceneSwitcher.switchScene("fxml/MainMenu.fxml", event, user);
    }

    public void handleManageRecipesBtnAction(ActionEvent event) {
        sceneSwitcher.switchScene("fxml/RecipesList.fxml", event, user);
    }

    private void initializeDailyMealsPreview() {
        EphemeralStore store = EphemeralStore.getInstance();
        MealPlan mealPlan = store.getMealPlan(user).orElse(null);

        if (mealPlan != null) {
            List<Meal> meals = mealPlan.getMeals();

            // Agrupando refeições por dia
            String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
            for (String day : daysOfWeek) {
                // Adiciona um título para o dia da semana
                Label dayLabel = new Label(day);
                dayLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
                mealsContainer.getChildren().add(dayLabel);

                // VBox para o dia da semana
                VBox dayVBox = new VBox(5); // Espaçamento entre os elementos dentro do dia
                HBox dayMealsHBox = new HBox(10); // Espaçamento entre as refeições

                // Filtra as refeições para o dia específico
                List<Meal> dayMeals = meals.stream()
                        //.filter(meal -> meal.getDay().equalsIgnoreCase(day)) // Ajuste conforme o atributo do dia em Meal
                        .toList();

                // Se houver refeições, adiciona os painéis das refeições, senão mostra mensagem
                if (!dayMeals.isEmpty()) {
                    for (Meal meal : dayMeals) {
                        Pane mealPane = createMealPane(meal);
                        dayMealsHBox.getChildren().add(mealPane);  // Adiciona a refeição ao HBox
                    }
                } else {
                    Label noMealsLabel = new Label("No meals planned for " + day);
                    noMealsLabel.setStyle("-fx-font-size: 12; -fx-text-fill: grey;");
                    dayMealsHBox.getChildren().add(noMealsLabel);  // Exibe a mensagem se não houver refeições
                }

                // Adiciona o HBox ao VBox
                dayVBox.getChildren().add(dayMealsHBox);
                mealsContainer.getChildren().add(dayVBox);  // Adiciona o VBox ao container de refeições
            }
        } else {
            Label noMealPlanLabel = new Label("No meal plan available for the selected user.");
            mealsContainer.getChildren().add(noMealPlanLabel);
        }

    }

    private Pane createMealPane(Meal meal) {
        Pane mealPane = new Pane();
        mealPane.setStyle("-fx-border-color: grey; -fx-border-width: 2;");
        mealPane.setPrefSize(237, 117);

        Label mealTypeLabel = new Label(meal.getType().toString());
        mealTypeLabel.setLayoutX(14);
        mealTypeLabel.setLayoutY(6);

        Button newRecipeButton = new Button("New Recipe");
        newRecipeButton.setLayoutX(100);
        newRecipeButton.setLayoutY(5);
        newRecipeButton.setFont(new Font(9));
        //newRecipeButton.setOnAction(event -> handleNewRecipeBtn(meal, event));

        CheckBox selectCheckBox = new CheckBox();
        selectCheckBox.setLayoutX(170);
        selectCheckBox.setLayoutY(6);
        if(user.getCurrentMealIndex() == meal.getMealIndex()){
            selectCheckBox.setDisable(false);
        }else{
            selectCheckBox.setDisable(true);
        }
        selectCheckBox.setOnAction(event -> handleCheckMealDone(meal, selectCheckBox, event));

        Recipe recipe = meal.getRecipe();
        if (recipe != null) {
            Label recipeNameLabel = new Label(recipe.name());
            recipeNameLabel.setLayoutX(8);
            recipeNameLabel.setLayoutY(41);

            Label caloriesLabel = new Label("- " + recipe.calories() + " cal");
            caloriesLabel.setLayoutX(8);
            caloriesLabel.setLayoutY(58);
            caloriesLabel.setFont(new Font(9));

            Label timeLabel = new Label("- " + recipe.prep().toMinutes() + " min");
            timeLabel.setLayoutX(8);
            timeLabel.setLayoutY(75);
            timeLabel.setFont(new Font(9));

            mealPane.getChildren().addAll(mealTypeLabel, newRecipeButton, selectCheckBox, recipeNameLabel, caloriesLabel, timeLabel);
        } else {
            Label noRecipeLabel = new Label("No recipe available");
            noRecipeLabel.setLayoutX(8);
            noRecipeLabel.setLayoutY(41);
            mealPane.getChildren().addAll(mealTypeLabel, newRecipeButton, selectCheckBox, noRecipeLabel);
        }

        if(meal.getMealIndex() < user.getCurrentMealIndex()){
            mealPane.setDisable(true);
        }
        return mealPane;
    }

    private void handleCheckMealDone(Meal meal, CheckBox selectCheckBox, ActionEvent event) {
        if(selectCheckBox.isSelected()){
            int sum = user.getHealthData().getDailyCalorieSum();
            user.getHealthData().setDailyCalorieSum(sum + meal.getRecipe().calories());
            user.setCurrentMealIndex(meal.getMealIndex() + 1);
            sceneSwitcher.switchScene("fxml/MealPlan.fxml", event, user);
        }
    }

//    private void handleNewRecipeBtn(Meal meal, ActionEvent event) {
//        // Gera a nova Meal
//        //Meal newMeal = generateNewMealPrompt();
//        Recipe dinnerRecipe = new Recipe(
//                "Frango com Legumes",
//                "1. Tempere o frango com sal e pimenta.\n2. Grelhe o frango até dourar.\n3. Cozinhe os brócolis no vapor.\n4. Sirva o frango acompanhado dos brócolis.",
//                1,
//                400,
//                Duration.ofMinutes(20),
//                List.of(new Reminder("Grelhe o frango."), new Reminder("Cozinhe os legumes no vapor.")),
//                List.of(
//                        new Ingredient("Frango", "Peito de frango grelhado", 150, "g", 200, new Macros(30, 0, 5), List.of()),
//                        new Ingredient("Brócolis", "Brócolis no vapor", 100, "g", 40, new Macros(4, 7, 0), List.of())
//                )
//        );
//        // Obtém o MealPlan e a lista de Meals associada ao usuário
//        EphemeralStore store = EphemeralStore.getInstance();
//        MealPlan mealPlan = store.getMealPlan(user).orElse(null);
//
//        if (mealPlan != null) {
//            List<Meal> meals = mealPlan.getMeals();
//
//            // Encontra o índice da Meal original e substitui pela nova
//            int mealIndex = meals.indexOf(meal);
////            if (mealIndex != -1) {
////                meals.set(mealIndex, newMeal);
////            }
//            for(Meal meal1: meals){
//                if(meal1.getMealIndex() == mealIndex){
//                    meal1.setRecipe(dinnerRecipe);
//                }
//            }
//
//            mealPlan.putMeals(meals);
//
//            // Opcional: Atualiza a visualização da Meal substituída, se necessário
//            sceneSwitcher.switchScene("fxml/MealPlan.fxml", event, user);
//        }
//    }


//    private Meal generateNewMealPrompt() {
//        // Lógica para gerar uma nova Meal (substitua com a implementação necessária)
//        //return new Meal();
//
//
//        //temporary solution
//        Recipe dinnerRecipe = new Recipe(
//                "Frango com Legumes",
//                "1. Tempere o frango com sal e pimenta.\n2. Grelhe o frango até dourar.\n3. Cozinhe os brócolis no vapor.\n4. Sirva o frango acompanhado dos brócolis.",
//                1,
//                400,
//                Duration.ofMinutes(20),
//                List.of(new Reminder("Grelhe o frango."), new Reminder("Cozinhe os legumes no vapor.")),
//                List.of(
//                        new Ingredient("Frango", "Peito de frango grelhado", 150, "g", 200, new Macros(30, 0, 5), List.of()),
//                        new Ingredient("Brócolis", "Brócolis no vapor", 100, "g", 40, new Macros(4, 7, 0), List.of())
//                )
//        );
//        Meal dinner = new Meal(dinnerRecipe);
//        dinner.setType(MealType.Dinner);
//
//
//        return dinner;
//    }

}
