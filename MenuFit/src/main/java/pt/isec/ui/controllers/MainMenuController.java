package pt.isec.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.isec.model.meals.*;
import pt.isec.model.users.BasicUser;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import pt.isec.model.users.UserInitializable;
import pt.isec.persistence.BDManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public Label greetingLabel;
    public VBox dailyMealsContainer;

    @FXML
    private Label caloriesConsumedLabel; // Label para calorias consumidas
    @FXML
    private Label caloriesRemainingLabel; // Label para calorias restantes


    //Propriedades calorias
    private IntegerProperty caloriesConsumed = new SimpleIntegerProperty(); // valor inicial
    private IntegerProperty caloriesRemaining = new SimpleIntegerProperty(); // valor inicial

    private BasicUser user;
    private MealPlan mealPlan;
    SceneSwitcher sceneSwitcher;
    BDManager bdManager;

    public MainMenuController(){
        this.sceneSwitcher = new SceneSwitcher();
    }

    @Override
    public void initializeUser(BasicUser user, BDManager bdManager) {
        this.user = user;
        this.bdManager = bdManager;
        if(user.getMealPlan() != null && !user.getMealPlan().getEndDate().isBefore(LocalDateTime.now())){
            mealPlan = user.getMealPlan();
        }else{
            mealPlan = bdManager.getMealPlan(user.getIdUser());
            user.setMealPlan(mealPlan);
        }
        initializeCalorieCounter();
        initializeNextMealPreview();
        initializeDailyMealsPreview();
        initializeDailyReminders();
        greetingLabel.setText("Bem vindo/a, " + user.getFirstName());
    }

    private void initializeCalorieCounter() {
        if(mealPlan != null){
            int counterDayTotal = 0;
            int counterConsumed = 0;
            for (Meal meal : mealPlan.getMeals()) {
                if (meal.getDate().toLocalDate().equals(LocalDate.now())) {
                    counterDayTotal += meal.getRecipe().getCalories();
                    if(meal.getCheck()){
                        counterConsumed += meal.getRecipe().getCalories();
                    }
                }
            }
            caloriesConsumed.set(counterConsumed);
            caloriesRemaining.set(counterDayTotal - counterConsumed);
            caloriesConsumedLabel.textProperty().bind(caloriesConsumed.asString().concat("/" + counterDayTotal));
            caloriesRemainingLabel.textProperty().bind(caloriesRemaining.asString().concat("/" + counterDayTotal));
        }else {
            caloriesConsumedLabel.setText("----/----");
            caloriesRemainingLabel.setText("----/----");
        }

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
        List<Meal> meals = mealPlan.getMeals();
        boolean mealFound = false;

        for (Meal meal : meals) {
            if (meal.getDate().toLocalDate().equals(LocalDate.now()) && !meal.getCheck()) {
                if (!mealFound) {
                    // Marca a primeira meal como "checked"
                    if (bdManager.checkMeal(meal)) {
                        meal.setCheck(true);
                        caloriesConsumed.set(caloriesConsumed.get() + meal.getRecipe().getCalories());
                        caloriesRemaining.set(caloriesRemaining.get() - meal.getRecipe().getCalories());
                        mealFound = true;
                    }
                } else {
                    // Atualiza a próxima meal após a marcada
                    updateNextMealPreview(meal);
                    initializeDailyReminders();
                    user.setCurrentMeal(meal);
                    return; // Termina o método, já que a próxima meal foi encontrada
                }
            }
        }

        // Caso não haja mais refeições disponíveis
        nextMealNameLabel.setText("End of meals");
        nextMealTypeLabel.setVisible(false);
        nextMealCaloriesLabel.setVisible(false);
        nextMealPrepTimeLabel.setVisible(false);
        nextMealDoneButton.setVisible(false);
        recipeImage.setVisible(false);
        user.setCurrentMeal(null);
        initializeDailyReminders();
    }

    private void initializeDailyReminders() {
        if(mealPlan == null) return;
        Map<Reminder, MealType> remindersWithMealTypeMap = new HashMap<>();

        boolean mealFound = false;
        for (Meal meal : mealPlan.getMeals()) {
            if (meal.getDate().toLocalDate().equals(LocalDate.now()) && !meal.getCheck()) {
                for (Reminder reminder : meal.getRecipe().getReminders()) {
                    MealType mealType = meal.getType();
                    remindersWithMealTypeMap.put(reminder, mealType);
                    mealFound = true;
                }
            }
        }
        if(!mealFound){
            vboxReminders.getChildren().clear();
            return;
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

            checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {

                        if(bdManager.checkReminder(reminder))
                            reminder.setCheck(newValue);
                    });

            vboxReminders.getChildren().add(hBox);
        }
    }

    private void initializeNextMealPreview() {
        if (mealPlan != null) {
            setNextMealElementsVisibility(true);

            List<Meal> meals = mealPlan.getMeals();
            boolean mealFound = false;

            for (Meal meal : meals) {
                if (meal.getDate().toLocalDate().equals(LocalDate.now()) && !meal.getCheck()) {
                    updateNextMealPreview(meal);
                    mealFound = true;
                    break;
                }
            }

            if (!mealFound) {
                nextMealNameLabel.setText("No meals left for today");
                setNextMealElementsVisibility(false);
            }

        } else {
            setNextMealElementsVisibility(false);
            nextMealNameLabel.setText("No meal plan generated yet");
        }
    }

    private void setNextMealElementsVisibility(boolean isVisible) {
        nextMealTypeLabel.setVisible(isVisible);
        nextMealCaloriesLabel.setVisible(isVisible);
        nextMealPrepTimeLabel.setVisible(isVisible);
        nextMealDoneButton.setVisible(isVisible);
        recipeImage.setVisible(isVisible);
    }


    private void updateNextMealPreview(Meal meal) {
        Recipe recipe = meal.getRecipe();
        nextMealTypeLabel.setText(meal.getType().name());
        nextMealNameLabel.setText(recipe.getName());
        nextMealCaloriesLabel.setText("- " + recipe.getCalories() + " cal");
        long minutes = recipe.getPrep().toMinutes();
        nextMealPrepTimeLabel.setText("- " + minutes + " m");
    }

    private void initializeDailyMealsPreview() {
        // Limpa o contêiner antes de adicionar novos rótulos
        dailyMealsContainer.getChildren().clear();

        if (mealPlan != null) {
            List<Meal> meals = mealPlan.getMeals();

            // Configura espaçamento global no VBox
            dailyMealsContainer.setSpacing(20);

            for (Meal meal : meals) {
                if (meal.getDate().toLocalDate().equals(LocalDate.now())) {
                    MealType type = meal.getType();
                    Recipe recipe = meal.getRecipe();

                    // Cria um Label para o tipo da refeição
                    Label mealTypeLabel = new Label(type.toString());
                    mealTypeLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

                    // Cria um Label para o nome da receita
                    Label recipeLabel = new Label(recipe.getName());
                    recipeLabel.setStyle("-fx-font-size: 18px;");

                    // Adiciona ambos os Labels ao contêiner
                    dailyMealsContainer.getChildren().addAll(mealTypeLabel, recipeLabel);
                }
            }
        } else {
            // Exibe uma mensagem genérica se não houver mealPlan
            Label noMealsLabel = new Label("No meals planned for today");
            noMealsLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: grey;");
            dailyMealsContainer.getChildren().add(noMealsLabel);
        }
    }
}