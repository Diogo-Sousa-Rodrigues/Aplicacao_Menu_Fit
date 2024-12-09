package pt.isec.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import pt.isec.model.users.BasicUser;
import pt.isec.model.users.HealthData;
import pt.isec.model.users.UserInitializable;
import pt.isec.persistence.BDManager;

public class HealthAndDietaryRestrictions_2Controller implements UserInitializable {

    private SceneSwitcher sceneSwitcher;
    private BasicUser user;
    private BDManager bdManager;

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

        String allergiesOrIntolerances = allergiesOrIntolerancesYesRadioButton.isSelected()
                ? allergiesOrIntolerancesTextField.getText().trim()
                : null;

        String chronicHealth = chronicHealthYesRadioButton.isSelected()
                ? chronicHealthTextField.getText().trim()
                : null;

        String medicalReasons = medicalYesRadioButton.isSelected()
                ? medicalReasonsTextField.getText().trim()
                : null;

        String gastrointestinalIssues = gastrointestinalIssuesYesRadioButton.isSelected()
                ? gastrointestinalIssuesTextField.getText().trim()
                : null;


        HealthData healthData = new HealthData(
                user.getHealthData().getWeight(),
                user.getHealthData().getHeight(),
                user.getHealthData().getObjective(),
                user.getHealthData().getLevelOfFitness(),
                user.getHealthData().getDesiredWeight(),
                user.getHealthData().getDailyCalorieCount(),
                allergiesOrIntolerances,
                medicalReasons,
                chronicHealth,
                gastrointestinalIssues,
                null,
                null,
                null
        );


        this.user.setHealthData(healthData);

        sceneSwitcher.switchScene("fxml/HealthAndDietaryRestrictions_3.fxml", event, user, bdManager);
    }

    @FXML
    public void previousHandler(ActionEvent event) {
        System.out.println("Going back to the previous step...");
        sceneSwitcher.switchScene("fxml/HealthAndDietaryRestrictions_1.fxml", event, user, bdManager);
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
    public void initializeUser(BasicUser user, BDManager bdManager) {
        this.user = user;
        this.bdManager = bdManager;
    }
}
