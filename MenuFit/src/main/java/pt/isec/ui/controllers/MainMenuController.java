package pt.isec.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import pt.isec.model.meals.*;
import pt.isec.model.users.User;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import pt.isec.model.users.UserInitializable;
import pt.isec.persistence.BDManager;
import pt.isec.persistence.EphemeralStore;

import java.util.*;


public class MainMenuController implements UserInitializable {
    public ImageView recipeImage;
    public ImageView profile;
    public Button seeMealPlanBtn;
    public Label nextMealTypeLabel;
    public Label nextMealNameLabel;
    public Label nextMealCaloriesLabel;
    public Label nextMealPrepTimeLabel;
    public Button nextMealDoneButton;
    public VBox vboxReminders;

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
        user.setCurrentMealIndex(user.getCurrentMealIndex()+1);
        int index = user.getCurrentMealIndex();
        EphemeralStore store = EphemeralStore.getInstance();
        Optional<MealPlan> mealPlanResult = store.getMealPlan(user);
        if(mealPlanResult.isPresent()) {
            MealPlan mealPlan = mealPlanResult.get();
            List<Meal> meals = mealPlan.getMeals();
            for (Meal meal : meals) {
                if (meal.getMealIndex() == index) {
                    updateNextMealPreview(meal);
                    user.setCurrentMeal(meal);
                    initializeDailyReminders();
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
                user.setCurrentMeal(null);
                initializeDailyReminders();
            }
        }
    }

    @Override
    public void initializeUser(User user, BDManager bdManager) {
        this.user = user;
        initializeNextMealPreview();
        initializeDailyMealsPreview();
        initializeDailyReminders();
        if (user.getHealthData()!=null) {
            caloriesConsumedLabel.textProperty().bind(caloriesConsumed.asString().concat("/" + user.getHealthData().getDailyCalorieCount()));
            caloriesRemainingLabel.textProperty().bind(caloriesRemaining.asString().concat("/" + user.getHealthData().getDailyCalorieCount()));
        }
    }

    private void initializeDailyReminders() {
        if(user.getCurrentMeal() == null){
            vboxReminders.getChildren().clear();
            return;
        }
        Map<Reminder, MealType> remindersWithMealTypeMap = new HashMap<>();
        EphemeralStore store = EphemeralStore.getInstance();
        Optional<MealPlan> mealPlanResult = store.getMealPlan(user);

        if (mealPlanResult.isPresent()) {
            MealPlan mealPlan = mealPlanResult.get();
            for (Meal meal : mealPlan.getMeals()) {
                if (meal.getDate().equals(user.getCurrentMeal().getDate())) {
                    for (Reminder reminder : meal.getRecipe().reminders()) {
                        MealType mealType = meal.getType();
                        remindersWithMealTypeMap.put(reminder, mealType);
                    }
                }
            }
        }

        vboxReminders.setLayoutX(5);
        vboxReminders.setLayoutY(5);
        vboxReminders.getChildren().clear();

        // Ordenar as entradas do mapa por MealType
        List<Map.Entry<Reminder, MealType>> sortedEntries = new ArrayList<>(remindersWithMealTypeMap.entrySet());
        sortedEntries.sort((entry1, entry2) -> {
            MealType mealType1 = entry1.getValue();
            MealType mealType2 = entry2.getValue();
            // Ordenar os MealTypes de acordo com a ordem desejada (Breakfast, Lunch, Dinner, Snack)
            return Integer.compare(mealType1.ordinal(), mealType2.ordinal());
        });

        for (Map.Entry<Reminder, MealType> entry : sortedEntries) {
            Reminder reminder = entry.getKey();  // Reminder
            MealType mealType = entry.getValue();

            CheckBox checkBox = new CheckBox();
            checkBox.setSelected(reminder.getCheck());

            // Criar o Label para o texto com wrap
            Label text = new Label(mealType.toString() + ": " + reminder.getData());
            text.setWrapText(true); // Ativa o wrap no texto

            // Garantir que o Label pode se expandir dentro do layout
            text.setMaxWidth(Double.MAX_VALUE); // Permite que o Label ocupe toda a largura disponível

            HBox hBox = new HBox(10);
            hBox.getChildren().addAll(checkBox, text);

            checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> reminder.setCheck(newValue));

            vboxReminders.getChildren().add(hBox);
        }
    }

    private void initializeNextMealPreview() {
        int index = user.getCurrentMealIndex();
        EphemeralStore store = EphemeralStore.getInstance();
        Optional<MealPlan> mealPlanResult = store.getMealPlan(user);
        if(mealPlanResult.isPresent()) {
            MealPlan mealPlan = mealPlanResult.get();
            nextMealTypeLabel.setVisible(true);
            nextMealNameLabel.setVisible(true);
            nextMealCaloriesLabel.setVisible(true);
            nextMealPrepTimeLabel.setVisible(true);
            nextMealDoneButton.setVisible(true);
            recipeImage.setVisible(true);
            List<Meal> meals = mealPlan.getMeals();
            for(Meal meal : meals){
                if(meal.getMealIndex() == index){
                    user.setCurrentMeal(meal);
                    updateNextMealPreview(meal);
                    break;
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
            MealPlan mealPlan = optionalMealPlan.get();
            List<Meal> meals = mealPlan.getMeals();

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