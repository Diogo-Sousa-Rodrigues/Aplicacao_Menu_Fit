package pt.isec.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import pt.isec.model.meals.*;
import pt.isec.model.users.User;
import pt.isec.model.users.UserInitializable;
import pt.isec.persistence.EphemeralStore;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

public class RecipeController implements UserInitializable {
    public ListView ingredientsListView;
    public Label servingsLabel;
    public Label prepTimeLabel;
    public Label caloriesLabel;
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

        initialize();
    }


    public void initialize(){
        EphemeralStore store = EphemeralStore.getInstance();
        Optional<MealPlan> mealPlan = store.getMealPlan(user);
        if(mealPlan.isPresent()){
            Optional<List<Meal>> meals = store.getMeals(mealPlan.get());
            if(meals.isPresent()){
                for(Meal meal : meals.get()){
                    if(meal.getRecipe().name().equals(user.getCurrentRecipe())){
                        showRecipe(meal.getRecipe());
                    }
                }
            }
        }
    }

    public void showRecipe(Recipe recipe){
        recipeNameLabel.setText(recipe.name());
        recipeTextLabel.setText(recipe.description());
        servingsLabel.setText(String.valueOf(recipe.servings()));
        long minutes = recipe.prep().toMinutes();
        prepTimeLabel.setText(minutes + "m");
        caloriesLabel.setText(String.valueOf(recipe.calories()));
        ingredientsListView.getItems().clear();
        for (Ingredient ingredient : recipe.ingredients()) {
            ingredientsListView.getItems().add(ingredient.toSimpleString());
        }
    }
}