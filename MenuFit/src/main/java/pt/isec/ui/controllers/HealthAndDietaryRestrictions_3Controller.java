package pt.isec.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import pt.isec.model.users.BasicUser;
import pt.isec.model.users.User;
import pt.isec.model.users.UserInitializable;
import pt.isec.persistence.BDManager;


public class HealthAndDietaryRestrictions_3Controller implements UserInitializable {
    private BasicUser user;
    private SceneSwitcher sceneSwitcher;

    @FXML
    private RadioButton vitaminOrMineralYesRadioButton;

    @FXML
    private RadioButton vitaminOrMineralNoRadioButton;

    @FXML
    private TextField vitaminOrMineralTextField;

    @FXML
    private RadioButton fatigueOrLowEnergyYesRadioButton;

    @FXML
    private RadioButton fatigueOrLowEnergyNoRadioButton;

    @FXML
    private RadioButton foodRestrictionsYesRadioButton;

    @FXML
    private RadioButton foodRestrictionsNoRadioButton;

    @FXML
    private TextField foodRestrictionsTextField;

    @FXML
    private RadioButton medicationsYesRadioButton;

    @FXML
    private RadioButton medicationsNoRadioButton;

    @FXML
    private TextField medicationsTextField;

    @FXML
    private Button goBack;

    @FXML
    private Button next;

    @FXML
    private ImageView logo;

    private ToggleGroup vitaminOrMineralGroup;
    private ToggleGroup fatigueOrLowEnergyGroup;
    private ToggleGroup foodRestrictionsGroup;
    private ToggleGroup medicationsGroup;

    public HealthAndDietaryRestrictions_3Controller(){
        this.sceneSwitcher = new SceneSwitcher();
    }

    @FXML
    private void initialize() {

        vitaminOrMineralGroup = new ToggleGroup();
        vitaminOrMineralYesRadioButton.setToggleGroup(vitaminOrMineralGroup);
        vitaminOrMineralNoRadioButton.setToggleGroup(vitaminOrMineralGroup);

        fatigueOrLowEnergyGroup = new ToggleGroup();
        fatigueOrLowEnergyYesRadioButton.setToggleGroup(fatigueOrLowEnergyGroup);
        fatigueOrLowEnergyNoRadioButton.setToggleGroup(fatigueOrLowEnergyGroup);

        foodRestrictionsGroup = new ToggleGroup();
        foodRestrictionsYesRadioButton.setToggleGroup(foodRestrictionsGroup);
        foodRestrictionsNoRadioButton.setToggleGroup(foodRestrictionsGroup);

        medicationsGroup = new ToggleGroup();
        medicationsYesRadioButton.setToggleGroup(medicationsGroup);
        medicationsNoRadioButton.setToggleGroup(medicationsGroup);

        vitaminOrMineralGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            toggleVitaminOrMineralTextField();
            validateForm();
        });

        fatigueOrLowEnergyGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> validateForm());

        foodRestrictionsGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            toggleFoodRestrictionsTextField();
            validateForm();
        });

        medicationsGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            toggleMedicationsTextField();
            validateForm();
        });

        vitaminOrMineralTextField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
        foodRestrictionsTextField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
        medicationsTextField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());

        toggleVitaminOrMineralTextField();
        toggleFoodRestrictionsTextField();
        toggleMedicationsTextField();

        next.setDisable(true);
    }

    @FXML
    public void finishHandler(ActionEvent event) {
        if (vitaminOrMineralYesRadioButton.isSelected()) {
            String deficiencies = vitaminOrMineralTextField.getText().trim();
            if (deficiencies.isEmpty()) {
                showAlert("Attention", "Please enter any vitamin or mineral deficiencies you have.");
                return;
            }
            System.out.println("Vitamin/Mineral Deficiencies: " + deficiencies);
        }

        if (fatigueOrLowEnergyYesRadioButton.isSelected()) {
            System.out.println("User experiences fatigue or low energy.");
        }

        if (foodRestrictionsYesRadioButton.isSelected()) {
            String restrictions = foodRestrictionsTextField.getText().trim();
            if (restrictions.isEmpty()) {
                showAlert("Attention", "Please enter your food preferences or restrictions.");
                return;
            }
            System.out.println("Food Preferences/Restrictions: " + restrictions);
        }

        if (medicationsYesRadioButton.isSelected()) {
            String medications = medicationsTextField.getText().trim();
            if (medications.isEmpty()) {
                showAlert("Attention", "Please enter the medications you are taking.");
                return;
            }
            System.out.println("Medications: " + medications);
        }

        System.out.println("Proceeding to the next step...");

        sceneSwitcher.switchScene("fxml/TimeAndBudget.fxml", event, user);
    }

    @FXML
    public void previousHandler(ActionEvent event) {
        sceneSwitcher.switchScene("fxml/HealthAndDietaryRestrictions_2.fxml", event, user);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void toggleVitaminOrMineralTextField() {
        if (vitaminOrMineralYesRadioButton.isSelected()) {
            vitaminOrMineralTextField.setDisable(false);
        } else {
            vitaminOrMineralTextField.setDisable(true);
            vitaminOrMineralTextField.clear();
        }
    }

    private void toggleFoodRestrictionsTextField() {
        if (foodRestrictionsYesRadioButton.isSelected()) {
            foodRestrictionsTextField.setDisable(false);
        } else {
            foodRestrictionsTextField.setDisable(true);
            foodRestrictionsTextField.clear();
        }
    }

    private void toggleMedicationsTextField() {
        if (medicationsYesRadioButton.isSelected()) {
            medicationsTextField.setDisable(false);
        } else {
            medicationsTextField.setDisable(true);
            medicationsTextField.clear();
        }
    }

    private void validateForm() {
        boolean vitaminOrMineralSelected = vitaminOrMineralGroup.getSelectedToggle() != null;
        boolean fatigueOrLowEnergySelected = fatigueOrLowEnergyGroup.getSelectedToggle() != null;
        boolean foodRestrictionsSelected = foodRestrictionsGroup.getSelectedToggle() != null;
        boolean medicationsSelected = medicationsGroup.getSelectedToggle() != null;

        boolean vitaminOrMineralFilled = !vitaminOrMineralYesRadioButton.isSelected() || !vitaminOrMineralTextField.getText().trim().isEmpty();
        boolean foodRestrictionsFilled = !foodRestrictionsYesRadioButton.isSelected() || !foodRestrictionsTextField.getText().trim().isEmpty();
        boolean medicationsFilled = !medicationsYesRadioButton.isSelected() || !medicationsTextField.getText().trim().isEmpty();

        next.setDisable(!(vitaminOrMineralSelected && fatigueOrLowEnergySelected && foodRestrictionsSelected && medicationsSelected
                && vitaminOrMineralFilled && foodRestrictionsFilled && medicationsFilled));
    }

    @Override
    public void initializeUser(BasicUser user, BDManager bdManager) {
        this.user = user;
    }
}
