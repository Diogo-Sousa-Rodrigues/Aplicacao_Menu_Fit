package pt.isec.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import pt.isec.model.users.BasicUser;
import pt.isec.model.users.HealthData;
import pt.isec.model.users.UserInitializable;
import pt.isec.persistence.BDManager;

public class ProfileRestrictionsController implements UserInitializable {

    private BasicUser user;
    private BDManager bdManager;
    private final SceneSwitcher sceneSwitcher;

    @FXML
    private Label userAllergiesLabel;
    @FXML
    private Label userHealthConditionsLabel;
    @FXML
    private Label userDietTypeLabel;
    @FXML
    private Label userMedicalReasonsLabel;
    @FXML
    private Label userMedicationsLabel;


    public ProfileRestrictionsController() {
        this.sceneSwitcher = new SceneSwitcher();
    }

    @FXML
    private void initialize() {

    }

    private void loadDietaryRestrictions() {
        HealthData healthData = user.getHealthData();

        if (healthData != null) {
            userAllergiesLabel.setText(healthData.getAllergiesOrIntolerances());
            userHealthConditionsLabel.setText(healthData.getChronicHealth());
        }
    }

    private void loadDietType() {
        HealthData healthData = user.getHealthData();

        if (healthData != null) {
            userDietTypeLabel.setText(healthData.getDietType());
            userMedicalReasonsLabel.setText(healthData.getMedicalReasons());
            userMedicationsLabel.setText(healthData.getMedications());
        }
    }

    @FXML
    public void editRestrictionsHandler(ActionEvent event) {

    }

    @FXML
    public void basicInformationHandler(ActionEvent event) {
        sceneSwitcher.switchScene("fxml/ProfileBasicInformation.fxml", event, user, bdManager);
    }

    @FXML
    public void objectivesHandler(ActionEvent event) {
        sceneSwitcher.switchScene("fxml/ProfileObjectives.fxml", event, user, bdManager);
    }

    @FXML
    public void logOutHandler(ActionEvent event) {
        sceneSwitcher.switchScene("fxml/Login.fxml", event, null, bdManager);
    }

    @Override
    public void initializeUser(BasicUser user, BDManager bdManager) {
        this.user = user;
        this.bdManager = bdManager;
        loadDietaryRestrictions();
        loadDietType();
    }

    @FXML
    public void handleGoBackButton(ActionEvent event) {
        sceneSwitcher.switchScene("fxml/MainMenu.fxml", event, user, bdManager);
    }
}
