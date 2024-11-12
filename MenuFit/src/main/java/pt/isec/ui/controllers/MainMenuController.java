package pt.isec.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import pt.isec.model.meals.Meal;
import pt.isec.model.meals.MealPlan;
import pt.isec.model.meals.Recipe;
import pt.isec.model.users.User;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import pt.isec.model.users.UserInitializable;
import pt.isec.persistence.EphemeralStore;

import java.util.List;
import java.util.Optional;


public class MainMenuController implements UserInitializable {
    public CheckBox mealCheckBox;
    public ImageView recipeImage;
    public ImageView profile;
    public Button seeMealPlanBtn;
    public Label nextMealTypeLabel;
    public Label nextMealNameLabel;
    public Label nextMealCaloriesLabel;
    public Label nextMealPrepTimeLabel;
    public Button nextMealDoneButton;

    @FXML
    private Label breakfastRecipeLabel;
    @FXML
    private Label lunchRecipeLabel;
    @FXML
    private Label dinnerRecipeLabel;

    @FXML
    private Label caloriesConsumedLabel; // Label para calorias consumidas
    @FXML
    private Label caloriesRemainingLabel; // Label para calorias restantes

    @FXML
    private Button myRecipeButton;


    //Propriedades calorias
    private IntegerProperty caloriesConsumed = new SimpleIntegerProperty(0); // valor inicial
    private IntegerProperty caloriesRemaining = new SimpleIntegerProperty(2000); // valor inicial

    private User user;
    SceneSwitcher sceneSwitcher;

    public MainMenuController(){
        this.sceneSwitcher = new SceneSwitcher();
    }

    @FXML
    public void initialize() {

    }

    @FXML
    private void handleProfileClick(MouseEvent event) {
        ActionEvent actionEvent = new ActionEvent(event.getSource(), event.getTarget());
        sceneSwitcher.switchScene("fxml/ProfileBasicInformation.fxml", actionEvent, user);
    }

    @FXML
    private void handleProfileMouseEntered(MouseEvent event) {
        profile.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");
        profile.setScaleX(1.1);
        profile.setScaleY(1.1);
    }

    @FXML
    private void handleProfileMouseExited(MouseEvent event) {
        profile.setStyle("");
        profile.setScaleX(1.0);
        profile.setScaleY(1.0);
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        sceneSwitcher.switchScene("fxml/HealthAndDietaryRestrictions_1.fxml", event, user);
    }

    @FXML
    private void handleSeeMealPlanBtnAction(ActionEvent event) {
        sceneSwitcher.switchScene("fxml/MealPlan.fxml", event, user);
    }

    @FXML
    private void handleMyRecipesBtnAction(ActionEvent event) {
        sceneSwitcher.switchScene("fxml/RecipesList.fxml", event, user);
    }

    @FXML
    public void mealPreviewChangeHandler(ActionEvent event) {
        boolean mealFound = false;
        user.setCurrentMeal(user.getCurrentMeal()+1);
        int index = user.getCurrentMeal();
        EphemeralStore store = EphemeralStore.getInstance();
        Optional<MealPlan> mealPlan = store.getMealPlan(user);
        if(mealPlan.isPresent()) {
            Optional<List<Meal>> meals = store.getMeals(mealPlan.get());
            if (meals.isPresent()) {
                for (Meal meal : meals.get()) {
                    if (meal.getMealIndex() == index) {
                        updateNextMealPreview(meal);
                        mealFound = true;
                        break;
                    }
                }
                if(!mealFound) {
                    nextMealNameLabel.setText("End of meals");
                    nextMealTypeLabel.setVisible(false);
                    nextMealCaloriesLabel.setVisible(false);
                    nextMealPrepTimeLabel.setVisible(false);
                    nextMealDoneButton.setVisible(false);
                    recipeImage.setVisible(false);
                }
            }
        }
    }

    private MealPlan mealPlan;

    @Override
    public void initializeUser(User user) {
        this.user = user;
        initializeNextMealPreview();
        initializeDailyMealsPreview();
        if (user.getHealthData()!=null) {
            caloriesConsumedLabel.textProperty().bind(caloriesConsumed.asString().concat("/" + user.getHealthData().getDailyCalorieCount()));
            caloriesRemainingLabel.textProperty().bind(caloriesRemaining.asString().concat("/" + user.getHealthData().getDailyCalorieCount()));
        }
    }

    private void initializeNextMealPreview() {
        int index = user.getCurrentMeal();
        EphemeralStore store = EphemeralStore.getInstance();
        Optional<MealPlan> mealPlan = store.getMealPlan(user);
        if(mealPlan.isPresent()) {
            nextMealTypeLabel.setVisible(true);
            nextMealNameLabel.setVisible(true);
            nextMealCaloriesLabel.setVisible(true);
            nextMealPrepTimeLabel.setVisible(true);
            nextMealDoneButton.setVisible(true);
            recipeImage.setVisible(true);
            Optional<List<Meal>> meals = store.getMeals(mealPlan.get());
            if(meals.isPresent()) {
                for(Meal meal : meals.get()){
                    if(meal.getMealIndex() == index){
                        updateNextMealPreview(meal);
                        break;
                    }
                }
            }
        }else {
            nextMealTypeLabel.setVisible(false);
            nextMealNameLabel.setText("No meal plan generated yet");
            nextMealCaloriesLabel.setVisible(false);
            nextMealPrepTimeLabel.setVisible(false);
            nextMealDoneButton.setVisible(false);
            recipeImage.setVisible(false);
        }
    }

    private void updateNextMealPreview(Meal meal) {
        Recipe recipe = meal.getRecipe();
        nextMealTypeLabel.setText(meal.getType().name());
        nextMealNameLabel.setText(recipe.name());
        nextMealCaloriesLabel.setText("- " + recipe.calories() + "cal");
        long minutes = recipe.prep().toMinutes();
        nextMealPrepTimeLabel.setText("- " + minutes + "m");
    }

    private void initializeDailyMealsPreview() {
        EphemeralStore store = EphemeralStore.getInstance();
        Optional<MealPlan> optionalMealPlan = store.getMealPlan(user);

        if (optionalMealPlan.isPresent()) {
            Optional<List<Meal>> optionalMeals = store.getMeals(optionalMealPlan.get());

            if (optionalMeals.isPresent()) {
                List<Meal> meals = optionalMeals.get();

                for (Meal meal : meals) {
                    Recipe recipe = meal.getRecipe();
                    switch (meal.getType()) {
                        case Breakfast:
                            breakfastRecipeLabel.setText(recipe.name());
                            break;
                        case Lunch:
                            lunchRecipeLabel.setText(recipe.name());
                            break;
                        case Dinner:
                            dinnerRecipeLabel.setText(recipe.name());
                            break;
                        default:
                            break;
                    }
                }
            }
        } else {
            breakfastRecipeLabel.setText("No breakfast planned");
            lunchRecipeLabel.setText("No lunch planned");
            dinnerRecipeLabel.setText("No dinner planned");
        }
    }



    // Métodos para atualizar os valores das calorias
   /* public void setCaloriesConsumed(int calories) {
        caloriesConsumed.set(calories);
    }

    public void setCaloriesRemaining(int calories) {
        caloriesRemaining.set(calories);
    }*/
}