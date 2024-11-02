package pt.isec.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import pt.isec.model.meals.MealPlan;
import pt.isec.model.users.User;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import pt.isec.model.users.UserInitializable;


public class MainMenuController implements UserInitializable {
    public Label mealTypeLabel;
    public CheckBox mealCheckBox;
    public Label recipeNameLabel;
    public Label recipeCaloriesLabel;
    public Label recipeTimeLabel;
    public ImageView recipeImage;
    public ImageView profile;
    public Button seeMealPlanBtn;

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


    //Propriedades calorias
    private IntegerProperty caloriesConsumed = new SimpleIntegerProperty(100); // valor inicial
    private IntegerProperty caloriesRemaining = new SimpleIntegerProperty(1900); // valor inicial

    private User user;
    SceneSwitcher sceneSwitcher;

    public MainMenuController(){
        this.sceneSwitcher = new SceneSwitcher();
    }

    @FXML
    public void initialize() {
        // Vinculação dos Labels para mostrar os valores de calorias (Contador de calorias)
        caloriesConsumedLabel.textProperty().bind(caloriesConsumed.asString().concat("/2000"));
        caloriesRemainingLabel.textProperty().bind(caloriesRemaining.asString().concat("/2000"));
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

    /*@FXML
    public void initialize() {
        //TODO para mostrar logo a receita suposta quando se entra nesta scene
    }*/

    public void mealPreviewChangeHandler(ActionEvent event) {
        //TODO mudar a receita em display quando a checkbox é selecionada
    }

    private MealPlan mealPlan;

    public void initializeUser(User user) {
        this.user = user;
    }


    // Métodos para atualizar os valores das calorias
   /* public void setCaloriesConsumed(int calories) {
        caloriesConsumed.set(calories);
    }

    public void setCaloriesRemaining(int calories) {
        caloriesRemaining.set(calories);
    }*/
}