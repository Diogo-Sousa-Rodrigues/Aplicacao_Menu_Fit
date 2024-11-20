package pt.isec.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import pt.isec.model.users.User;
import pt.isec.model.users.UserInitializable;
import pt.isec.persistence.BDManager;

public class HealthAndDietaryRestrictions_2Controller implements UserInitializable {

    private SceneSwitcher sceneSwitcher;
    private User user;

    @FXML
    private RadioButton allergiesOrIntolerancesYesRadioButton;

    @FXML
    private RadioButton allergiesOrIntolerancesNoRadioButton;

    @FXML
    private TextField allergiesOrIntolerancesTextField;

    @FXML
    private RadioButton chronicHealthYesRadioButton;

    @FXML
    private RadioButton chronicHealthNoRadioButton;

    @FXML
    private TextField chronicHealthTextField;

    @FXML
    private RadioButton medicalYesRadioButton;

    @FXML
    private RadioButton medicalNoRadioButton;

    @FXML
    private TextField medicalReasonsTextField;

    @FXML
    private RadioButton gastrointestinalIssuesYesRadioButton;

    @FXML
    private RadioButton gastrointestinalIssuesNoRadioButton;

    @FXML
    private TextField gastrointestinalIssuesTextField;

    @FXML
    private Button goBack;

    @FXML
    private Button next;

    @FXML
    private ImageView logo;

    public HealthAndDietaryRestrictions_2Controller(){
        this.sceneSwitcher = new SceneSwitcher();
    }

    @FXML
    private void initialize() {

        allergiesOrIntolerancesYesRadioButton.setOnAction(e -> {
            toggleAllergiesTextField();
            validateForm();
        });
        allergiesOrIntolerancesNoRadioButton.setOnAction(e -> {
            toggleAllergiesTextField();
            validateForm();
        });

        chronicHealthYesRadioButton.setOnAction(e -> {
            toggleChronicHealthTextField();
            validateForm();
        });
        chronicHealthNoRadioButton.setOnAction(e -> {
            toggleChronicHealthTextField();
            validateForm();
        });

        medicalYesRadioButton.setOnAction(e -> {
            toggleMedicalTextField();
            validateForm();
        });
        medicalNoRadioButton.setOnAction(e -> {
            toggleMedicalTextField();
            validateForm();
        });

        gastrointestinalIssuesYesRadioButton.setOnAction(e -> {
            toggleGastrointestinalIssuesTextField();
            validateForm();
        });
        gastrointestinalIssuesNoRadioButton.setOnAction(e -> {
            toggleGastrointestinalIssuesTextField();
            validateForm();
        });

        allergiesOrIntolerancesTextField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
        chronicHealthTextField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
        medicalReasonsTextField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
        gastrointestinalIssuesTextField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());

        toggleAllergiesTextField();
        toggleChronicHealthTextField();
        toggleMedicalTextField();
        toggleGastrointestinalIssuesTextField();

        next.setDisable(true);
    }

    @FXML
    public void finishHandler(ActionEvent event) {

        if (allergiesOrIntolerancesYesRadioButton.isSelected()) {
            String allergies = allergiesOrIntolerancesTextField.getText().trim();
            if (allergies.isEmpty()) {
                showAlert("Attention", "Please enter your allergies or intolerances.");
                return;
            }
            System.out.println("Allergies/Intolerances: " + allergies);
        }

        if (chronicHealthYesRadioButton.isSelected()) {
            String chronicCondition = chronicHealthTextField.getText().trim();
            if (chronicCondition.isEmpty()) {
                showAlert("Attention", "Please enter your chronic health conditions.");
                return;
            }
            System.out.println("Chronic Health Conditions: " + chronicCondition);
        }

        if (medicalYesRadioButton.isSelected()) {
            String medicalDiet = medicalReasonsTextField.getText().trim();
            if (medicalDiet.isEmpty()) {
                showAlert("Attention", "Please enter the type of medical diet you are following.");
                return;
            }
            System.out.println("Medical Diet: " + medicalDiet);
        }

        if (gastrointestinalIssuesYesRadioButton.isSelected()) {
            String gastrointestinalIssues = gastrointestinalIssuesTextField.getText().trim();
            if (gastrointestinalIssues.isEmpty()) {
                showAlert("Attention", "Please enter your gastrointestinal issues.");
                return;
            }
            System.out.println("Gastrointestinal Issues: " + gastrointestinalIssues);
        }

        System.out.println("Proceeding to the next step...");
        sceneSwitcher.switchScene("fxml/HealthAndDietaryRestrictions_3.fxml", event, user);
    }

    @FXML
    public void previousHandler(ActionEvent event) {
        System.out.println("Going back to the previous step...");
        sceneSwitcher.switchScene("fxml/HealthAndDietaryRestrictions_1.fxml", event, user);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void toggleAllergiesTextField() {
        if (allergiesOrIntolerancesYesRadioButton.isSelected()) {
            allergiesOrIntolerancesTextField.setDisable(false);
        } else {
            allergiesOrIntolerancesTextField.setDisable(true);
            allergiesOrIntolerancesTextField.clear();
        }
    }

    private void toggleChronicHealthTextField() {
        if (chronicHealthYesRadioButton.isSelected()) {
            chronicHealthTextField.setDisable(false);
        } else {
            chronicHealthTextField.setDisable(true);
            chronicHealthTextField.clear();
        }
    }

    private void toggleMedicalTextField() {
        if (medicalYesRadioButton.isSelected()) {
            medicalReasonsTextField.setDisable(false);
        } else {
            medicalReasonsTextField.setDisable(true);
            medicalReasonsTextField.clear();
        }
    }

    private void toggleGastrointestinalIssuesTextField() {
        if (gastrointestinalIssuesYesRadioButton.isSelected()) {
            gastrointestinalIssuesTextField.setDisable(false);
        } else {
            gastrointestinalIssuesTextField.setDisable(true);
            gastrointestinalIssuesTextField.clear();
        }
    }

    private void validateForm() {
        boolean allergiesSelected = allergiesOrIntolerancesYesRadioButton.isSelected() || allergiesOrIntolerancesNoRadioButton.isSelected();
        boolean chronicHealthSelected = chronicHealthYesRadioButton.isSelected() || chronicHealthNoRadioButton.isSelected();
        boolean medicalSelected = medicalYesRadioButton.isSelected() || medicalNoRadioButton.isSelected();
        boolean gastrointestinalIssuesSelected = gastrointestinalIssuesYesRadioButton.isSelected() || gastrointestinalIssuesNoRadioButton.isSelected();

        boolean allergiesFilled = !allergiesOrIntolerancesYesRadioButton.isSelected() || !allergiesOrIntolerancesTextField.getText().trim().isEmpty();
        boolean chronicHealthFilled = !chronicHealthYesRadioButton.isSelected() || !chronicHealthTextField.getText().trim().isEmpty();
        boolean medicalFilled = !medicalYesRadioButton.isSelected() || !medicalReasonsTextField.getText().trim().isEmpty();
        boolean gastrointestinalIssuesFilled = !gastrointestinalIssuesYesRadioButton.isSelected() || !gastrointestinalIssuesTextField.getText().trim().isEmpty();

        next.setDisable(!(allergiesSelected && chronicHealthSelected && medicalSelected && gastrointestinalIssuesSelected && allergiesFilled && chronicHealthFilled && medicalFilled && gastrointestinalIssuesFilled));
    }

    @Override
    public void initializeUser(User user, BDManager bdManager) {
        this.user = user;
    }
}
