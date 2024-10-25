package pt.isec.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import pt.isec.model.users.HealthData;
import pt.isec.model.users.User;

public class HealthAndDietaryRestrictions_1Controller {
    /*public HealthAndDietaryRestrictions_1Controller(User user){
        this.user = user;
        this.sceneSwitcher = new SceneSwitcher();
    }*/
    public HealthAndDietaryRestrictions_1Controller(){
        sceneSwitcher = new SceneSwitcher();
    }

    @FXML
    public void previousHandler(ActionEvent event) {
        sceneSwitcher.switchScene("fxml/MainMenu.fxml", event);
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

        //this.user.setHealthData(healthData);

        System.out.println("Health Data collected: " + healthData);

        sceneSwitcher.switchScene("fxml/HealthAndDietaryRestrictions_2.fxml", event);
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


    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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

    //private final User user;

    private final SceneSwitcher sceneSwitcher;

    @FXML
    private Button goBack, next;

    @FXML
    private TextField weightTextField, heightTextField, objectiveTextField,
            desiredWeightTextField, dailyCalorieCountTextField;

    @FXML
    private RadioButton desiredWeightYesRadioButton, desiredWeightNoRadioButton,
            dailyCalorieYesCountRadioButton, dailyCalorieNoCountRadioButton,
            kgRadioButton, lbsRadioButton;

    @FXML
    private Label BMI;

    @FXML
    private ToggleGroup weightUnitToggleGroup;

    @FXML
    private ComboBox<String> levelOfFitnessComboBox;
}