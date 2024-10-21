package UI.Controllers;

import javafx.event.ActionEvent;
import model.HealthData;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import model.users.User;
import persistence.LoggedInUserStore;

public class HealthAndDietaryRestrictions_1Controller {

    private SceneSwitcher sceneSwitcher;

    @FXML
    private Button goBack;
    @FXML
    private Button next;

    @FXML
    private TextField weightTextField;

    @FXML
    private TextField heightTextField;

    @FXML
    private TextField objectiveTextField;

    @FXML
    private ComboBox<String> levelOfFitnessComboBox;

    @FXML
    private TextField desiredWeightTextField;

    @FXML
    private TextField dailyCalorieCountTextField;

    @FXML
    private RadioButton desiredWeightYesRadioButton;

    @FXML
    private RadioButton desiredWeightNoRadioButton;

    @FXML
    private RadioButton dailyCalorieYesCountRadioButton;

    @FXML
    private RadioButton dailyCalorieNoCountRadioButton;

    @FXML
    private RadioButton kgRadioButton;

    @FXML
    private RadioButton lbsRadioButton;

    @FXML
    private Label BMI;

    @FXML
    private ToggleGroup weightUnitToggleGroup;

    @FXML
    private void initialize() {

        levelOfFitnessComboBox.getItems().addAll("Beginner", "Intermediate", "Advanced");

        desiredWeightYesRadioButton.setOnAction(e -> {
            toggleDesiredWeightTextField();
            validateForm();
        });

        desiredWeightNoRadioButton.setOnAction(e -> {
            toggleDesiredWeightTextField();
            validateForm();
        });

        dailyCalorieYesCountRadioButton.setOnAction(e -> {
            toggleDailyCalorieCountTextField();
            validateForm();
        });

        dailyCalorieNoCountRadioButton.setOnAction(e -> {
            toggleDailyCalorieCountTextField();
            validateForm();
        });

        kgRadioButton.setOnAction(e -> validateForm());
        lbsRadioButton.setOnAction(e -> validateForm());

        weightTextField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
        heightTextField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
        objectiveTextField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
        levelOfFitnessComboBox.valueProperty().addListener((observable, oldValue, newValue) -> validateForm());

        toggleDesiredWeightTextField();
        toggleDailyCalorieCountTextField();

        next.setDisable(true);
    }

    private void validateForm() {
        boolean weightNotEmpty = !weightTextField.getText().trim().isEmpty();
        boolean heightNotEmpty = !heightTextField.getText().trim().isEmpty();
        boolean objectiveNotEmpty = !objectiveTextField.getText().trim().isEmpty();
        boolean levelOfFitnessSelected = levelOfFitnessComboBox.getValue() != null;
        boolean desiredWeightSelected = desiredWeightYesRadioButton.isSelected() || desiredWeightNoRadioButton.isSelected();
        boolean dailyCalorieSelected = dailyCalorieYesCountRadioButton.isSelected() || dailyCalorieNoCountRadioButton.isSelected();
        boolean weightUnitSelected = kgRadioButton.isSelected() || lbsRadioButton.isSelected();

        next.setDisable(!(weightNotEmpty && heightNotEmpty && objectiveNotEmpty && levelOfFitnessSelected
                && desiredWeightSelected && dailyCalorieSelected && weightUnitSelected));
    }

    @FXML
    public void previousHandler(ActionEvent event) {
        sceneSwitcher.switchScene("../fxml/MainMenu.fxml", event);
    }

    public HealthAndDietaryRestrictions_1Controller(){
        this.sceneSwitcher = new SceneSwitcher();
    }

    @FXML
    public void finishHandler(ActionEvent event) {
        String weight = weightTextField.getText();
        String height = heightTextField.getText();
        String objective = objectiveTextField.getText();
        String levelOfFitness = levelOfFitnessComboBox.getValue();
        String desiredWeight = desiredWeightYesRadioButton.isSelected() ? desiredWeightTextField.getText() : "None";
        String dailyCalorieCount = dailyCalorieYesCountRadioButton.isSelected() ? dailyCalorieCountTextField.getText() : "None";

        if (weight.isEmpty() || height.isEmpty() || objective.isEmpty() || levelOfFitness == null ||
                (!desiredWeightYesRadioButton.isSelected() && !desiredWeightNoRadioButton.isSelected()) ||
                (!dailyCalorieYesCountRadioButton.isSelected() && !dailyCalorieNoCountRadioButton.isSelected()) ||
                (!kgRadioButton.isSelected() && !lbsRadioButton.isSelected())) {

            showAlert("Attention", "Please fill in all required fields and make selections for the radio buttons.");
            return;
        }

        if (desiredWeightYesRadioButton.isSelected() && desiredWeightTextField.getText().isEmpty()) {
            showAlert("Attention", "Please specify your desired weight.");
            return;
        }

        if (dailyCalorieYesCountRadioButton.isSelected() && dailyCalorieCountTextField.getText().isEmpty()) {
            showAlert("Attention", "Please specify your daily calorie count.");
            return;
        }

        HealthData healthData = new HealthData(
                weight,
                height,
                objective,
                levelOfFitness,
                desiredWeight,
                dailyCalorieCount
        );

        LoggedInUserStore userStore = LoggedInUserStore.getInstance();
        User currentUser = userStore.getCurrentUser();
        currentUser.setHealthData(healthData);

        System.out.println("Health Data collected: " + healthData);

        sceneSwitcher.switchScene("../fxml/HealthAndDietaryRestrictions_2.fxml", event);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void calculateBMI() {
        try {
            double weight = Double.parseDouble(weightTextField.getText());
            double height = Double.parseDouble(heightTextField.getText()) / 100;
            double bmi = weight / (height * height);
            BMI.setText(String.format("Your BMI is %.2f", bmi));
        } catch (NumberFormatException e) {
            BMI.setText("Invalid weight or height.");
        }
    }

    private void toggleDesiredWeightTextField() {
        if (desiredWeightYesRadioButton.isSelected()) {
            desiredWeightTextField.setDisable(false);
        } else {
            desiredWeightTextField.setDisable(true);
            desiredWeightTextField.clear();
        }
    }

    private void toggleDailyCalorieCountTextField() {
        if (dailyCalorieYesCountRadioButton.isSelected()) {
            dailyCalorieCountTextField.setDisable(false);
        } else {
            dailyCalorieCountTextField.setDisable(true);
            dailyCalorieCountTextField.clear();
        }
    }
}