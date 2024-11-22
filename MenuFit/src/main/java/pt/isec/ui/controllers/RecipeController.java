package pt.isec.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import pt.isec.model.meals.*;
import pt.isec.model.users.BasicUser;
import pt.isec.model.users.UserInitializable;
import pt.isec.persistence.BDManager;
import pt.isec.persistence.EphemeralStore;
import java.util.List;
import java.util.Optional;

public class RecipeController implements UserInitializable {
    public ListView ingredientsListView;
    public Label servingsLabel;
    public Label prepTimeLabel;
    public Label caloriesLabel;
    private BasicUser user;
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
    public void initializeUser(BasicUser user, BDManager bdManager) {
        this.user = user;

        initialize();
    }


    public void initialize(){
        EphemeralStore store = EphemeralStore.getInstance();
        Optional<MealPlan> mealPlanResult = store.getMealPlan(user);
        if(mealPlanResult.isPresent()){
            MealPlan mealPlan = mealPlanResult.get();
            List<Meal> meals = mealPlan.getMeals();
            for(Meal meal : meals){
                if(meal.getRecipe().getName().equals(user.getCurrentRecipe())){
                    showRecipe(meal.getRecipe());
                }
            }
        }
    }

    public void showRecipe(Recipe recipe){
        recipeNameLabel.setText(recipe.getName());
        recipeTextLabel.setText(recipe.getDescription());
        servingsLabel.setText(String.valueOf(recipe.getServings()));
        long minutes = recipe.getPrep().toMinutes();
        prepTimeLabel.setText(minutes + "m");
        caloriesLabel.setText(String.valueOf(recipe.getCalories()));
        ingredientsListView.getItems().clear();
        for (Ingredient ingredient : recipe.getIngredients()) {
            String ingredientsListViewRow = ingredient.quantity() + ingredient.units() +
                    " " + ingredient.name();

            ingredientsListView.getItems().add(ingredientsListViewRow);
        }
    }
}