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
import pt.isec.model.users.BasicUser;
import pt.isec.model.users.UserInitializable;
import pt.isec.persistence.BDManager;
import pt.isec.builders.InstanceBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MealPlanController implements UserInitializable {
    private BasicUser user;
    private SceneSwitcher sceneSwitcher;
    private MealPlan mealPlan;
    private BDManager bdManager;


    @FXML
    private VBox mealsContainer;

    public MealPlanController() {
        this.sceneSwitcher = new SceneSwitcher();
    }

    @Override
    public void initializeUser(BasicUser user, BDManager bdManager) {
        this.user = user;
        this.bdManager = bdManager;
        if(user.getMealPlan() != null){
            mealPlan = user.getMealPlan();
        }else{
            mealPlan = bdManager.getMealPlan(user.getIdUser());
            user.setMealPlan(mealPlan);
        }
        initializeDailyMealsPreview();
    }

    public void handleGoBackBtnAction(ActionEvent event) {
        sceneSwitcher.switchScene("fxml/MainMenu.fxml", event, user);
    }

    public void handleManageRecipesBtnAction(ActionEvent event) {
        sceneSwitcher.switchScene("fxml/RecipesList.fxml", event, user);
    }

    private void initializeDailyMealsPreview() {
        if (mealPlan != null) {
            List<Meal> meals = mealPlan.getMeals();

            // Determina o dia inicial para ordenar os dias da semana
            String startDay = mealPlan.getBeginDate() != null
                    ? mealPlan.getBeginDate().getDayOfWeek().toString()
                    : LocalDate.now().getDayOfWeek().toString();

            List<String> orderedDays = getOrderedDays(startDay);

            for (String day : orderedDays) {
                // Adiciona um título para o dia da semana
                Label dayLabel = new Label(day);
                dayLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
                mealsContainer.getChildren().add(dayLabel);

                // VBox para o dia da semana
                VBox dayVBox = new VBox(5); // Espaçamento entre os elementos dentro do dia
                HBox dayMealsHBox = new HBox(10); // Espaçamento entre as refeições

                // Filtra as refeições para o dia específico
                List<Meal> dayMeals = meals.stream()
                        .filter(meal -> meal.getDate().getDayOfWeek().toString().equalsIgnoreCase(day))
                        .toList();

                // Se houver refeições, adiciona os painéis das refeições, senão mostra mensagem
                if (!dayMeals.isEmpty()) {
                    for (Meal meal : dayMeals) {
                        Pane mealPane = createMealPane(meal);
                        dayMealsHBox.getChildren().add(mealPane);
                    }
                } else {
                    Label noMealsLabel = new Label("No meals planned for " + day);
                    noMealsLabel.setStyle("-fx-font-size: 12; -fx-text-fill: grey;");
                    dayMealsHBox.getChildren().add(noMealsLabel);
                }

                // Adiciona o HBox ao VBox
                dayVBox.getChildren().add(dayMealsHBox);
                mealsContainer.getChildren().add(dayVBox);
            }
        } else {
            Label noMealPlanLabel = new Label("No meal plan available for the selected user.");
            mealsContainer.getChildren().add(noMealPlanLabel);
        }
    }

    /**
     * Gera a ordem dos dias da semana começando por um dia específico.
     *
     * @param startDay O dia de início, como "MONDAY".
     * @return Uma lista dos dias na ordem reorganizada.
     */
    private List<String> getOrderedDays(String startDay) {
        List<String> daysOfWeek = Arrays.asList("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY");
        int startIndex = daysOfWeek.indexOf(startDay.toUpperCase());
        if (startIndex == -1) return daysOfWeek; // Caso inválido, retorna a ordem padrão.

        List<String> orderedDays = new ArrayList<>();
        orderedDays.addAll(daysOfWeek.subList(startIndex, daysOfWeek.size())); // Parte após o início.
        orderedDays.addAll(daysOfWeek.subList(0, startIndex)); // Parte antes do início.
        return orderedDays;
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
        newRecipeButton.setOnAction(event -> handleNewRecipeBtn(meal, event));

        CheckBox selectCheckBox = new CheckBox();
        selectCheckBox.setLayoutX(170);
        selectCheckBox.setLayoutY(6);
        selectCheckBox.setDisable(!meal.getDate().toLocalDate().equals(LocalDate.now()));
        selectCheckBox.setOnAction(event -> handleCheckMealDone(meal, selectCheckBox, event, mealPane));

        Recipe recipe = meal.getRecipe();
        if (recipe != null) {
            Label recipeNameLabel = new Label(recipe.getName());
            recipeNameLabel.setLayoutX(8);
            recipeNameLabel.setLayoutY(41);

            Label caloriesLabel = new Label("- " + recipe.getCalories() + " cal");
            caloriesLabel.setLayoutX(8);
            caloriesLabel.setLayoutY(58);
            caloriesLabel.setFont(new Font(9));

            Label timeLabel = new Label("- " + recipe.getPrep().toMinutes() + " min");
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

        if(meal.getCheck()){
            mealPane.setDisable(true);
        }
        return mealPane;
    }

    private void handleCheckMealDone(Meal meal, CheckBox selectCheckBox, ActionEvent event, Pane mealPane) {
        if(selectCheckBox.isSelected()){
            //int sum = user.getHealthData().getDailyCalorieSum();
            //user.getHealthData().setDailyCalorieSum(sum + meal.getRecipe().getCalories());
            //user.setCurrentMealIndex(meal.getMealIndex() + 1);
            bdManager.checkMeal(meal);
            mealPlan = bdManager.getMealPlan(user.getIdUser());
            user.setMealPlan(mealPlan);
            mealPane.setDisable(true);
            sceneSwitcher.switchScene("fxml/MealPlan.fxml", event, user);
        }
    }

    private void handleNewRecipeBtn(Meal meal, ActionEvent event) {
        Optional<Recipe> newRecipe = generateNewMealPrompt();
        if (mealPlan != null) {
            //List<Meal> meals = mealPlan.getMeals();

            if (newRecipe.isPresent()) {
                if (bdManager.updateMealRecipe(meal, newRecipe)) {
                    meal.setRecipe(newRecipe.get()); // Atualiza a receita da meal com a nova
                    List<Meal> meals = user.getMealPlan().getMeals(); // Obtém a lista de refeições
                    for (int i = 0; i < meals.size(); i++) {
                        Meal mealExistente = meals.get(i); // Obtém cada meal existente na lista
                        if (mealExistente.getMealID().equals(meal.getMealID())) {
                            // Substitui diretamente a meal na lista
                            meals.set(i, meal);
                            break; // Encerra o loop assim que encontrar a meal
                        }
                    }
                }
            }
            // Opcional: Atualiza a visualização da Meal substituída, se necessário
            sceneSwitcher.switchScene("fxml/MealPlan.fxml", event, user);
        }
    }


    private Optional<Recipe> generateNewMealPrompt() {
        // Simulando o conteúdo da receita em formato JSON (com os novos campos já ajustados)
        String json = "{\"name\":\"Nova receita\",\"description\":\"Bell peppers filled with quinoa, beans, and cheese.\",\"servings\":2,\"prep\":40,\"reminders\":[{\"data\":\"Bake at 375°F for 30 minutes.\"}],\"ingredients\":[{\"name\":\"Bell Peppers\",\"description\":\"Edible containers for stuffing.\",\"quantity\":2,\"units\":\"units\",\"calories\":50,\"macros\":{\"proteins\":1.0,\"carbs\":10.0,\"fats\":0.5},\"allergens\":[]},{\"name\":\"Quinoa\",\"description\":\"Protein-rich stuffing.\",\"quantity\":100,\"units\":\"grams\",\"calories\":120,\"macros\":{\"proteins\":4.0,\"carbs\":21.0,\"fats\":1.9},\"allergens\":[]}]}";
        // Usando InstanceBuilder para deserializar
        InstanceBuilder instanceBuilder = new InstanceBuilder();
        Optional<Recipe> optionalRecipe = instanceBuilder.fromJson(json, Recipe.class);

        // Verificar se o resultado é válido
        if (optionalRecipe.isPresent()) {
            return optionalRecipe;
        } else {
            System.out.println("Erro na deserialização do JSON para Recipe");
            return Optional.empty();
        }
    }
}
