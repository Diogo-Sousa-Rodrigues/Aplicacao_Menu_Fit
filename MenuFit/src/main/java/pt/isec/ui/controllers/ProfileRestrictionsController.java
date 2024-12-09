package pt.isec.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import pt.isec.model.users.BasicUser;
import pt.isec.model.users.HealthData;
import pt.isec.model.users.UserInitializable;
import pt.isec.persistence.BDManager;

import java.io.FileWriter;
import java.io.IOException;

public class ProfileRestrictionsController implements UserInitializable {

    public Label userDietLabel;
    public Label userFoodPrefRestricLabel;
    public Label userVitaminsDeficienciesLabel;
    public Label userChronicHealthConditionsLabel;
    public Label userGastroIssuesLabel;
    public Button editRestrictionsButton;
    public Button saveBtn;
    public Button cancelBtn;
    public TextArea chronicHealthEdit;
    public TextArea gastroIssuesEdit;
    public TextArea medicationEdit;
    public TextArea dietTypeEdit;
    public TextArea allergiesEdit;
    public TextArea vitaminsEdit;
    public TextArea foodPrefRestricEdit;
    public Label warningLabel;
    public Button generateMealPlanBtn;
    private BasicUser user;
    private BDManager bdManager;
    private final SceneSwitcher sceneSwitcher;

    @FXML
    private Label userAllergiesLabel;
    @FXML
    private Label userMedicationsLabel;

    HealthData healthData;
    private static final String REMEMBER_ME_FILE = System.getProperty("user.home") + "/remember_me.txt";

    public ProfileRestrictionsController() {
        this.sceneSwitcher = new SceneSwitcher();
    }

    @Override
    public void initializeUser(BasicUser user, BDManager bdManager) {
        this.user = user;
        this.bdManager = bdManager;
        loadDietaryRestrictions();
        disableFields(user.getHealthData()==null);
    }

    private void disableFields(boolean value) {
        editRestrictionsButton.setDisable(value);
        warningLabel.setVisible(value);
        generateMealPlanBtn.setVisible(value);
    }

    private void loadDietaryRestrictions() {
        healthData = user.getHealthData();

        if (healthData != null) {
            userDietLabel.setText(healthData.getMedicalReasons());
            userAllergiesLabel.setText(healthData.getAllergiesOrIntolerances());
            userVitaminsDeficienciesLabel.setText(healthData.getVitaminDeficiencies());
            userFoodPrefRestricLabel.setText(healthData.getDietType());
            userChronicHealthConditionsLabel.setText(healthData.getChronicHealth());
            userGastroIssuesLabel.setText(healthData.getGastrointestinalIssues());
            userMedicationsLabel.setText(healthData.getMedications());
        }
        //editRestrictionsButton.setDisable(user.getHealthData()==null);

    }

    void changeEditVisibilities(boolean isVisible){
        allergiesEdit.setVisible(isVisible);
        chronicHealthEdit.setVisible(isVisible);
        vitaminsEdit.setVisible(isVisible);
        dietTypeEdit.setVisible(isVisible);
        foodPrefRestricEdit.setVisible(isVisible);
        gastroIssuesEdit.setVisible(isVisible);
        medicationEdit.setVisible(isVisible);
        saveBtn.setVisible(isVisible);
        cancelBtn.setVisible(isVisible);
        editRestrictionsButton.setVisible(!isVisible);
        editRestrictionsButton.setDisable(user.getHealthData()==null);
        userAllergiesLabel.setVisible(!isVisible);
        userDietLabel.setVisible(!isVisible);
        userGastroIssuesLabel.setVisible(!isVisible);
        userMedicationsLabel.setVisible(!isVisible);
        userChronicHealthConditionsLabel.setVisible(!isVisible);
        userFoodPrefRestricLabel.setVisible(!isVisible);
        userVitaminsDeficienciesLabel.setVisible(!isVisible);
    }

    @FXML
    public void editRestrictionsHandler(ActionEvent event) {
        changeEditVisibilities(true);
        dietTypeEdit.setText(userDietLabel.getText());
        allergiesEdit.setText(userAllergiesLabel.getText());
        vitaminsEdit.setText(userVitaminsDeficienciesLabel.getText());
        foodPrefRestricEdit.setText(userFoodPrefRestricLabel.getText());
        chronicHealthEdit.setText(userChronicHealthConditionsLabel.getText());
        gastroIssuesEdit.setText(userGastroIssuesLabel.getText());
        medicationEdit.setText(userMedicationsLabel.getText());
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
        deleteRememberMeFile();
        sceneSwitcher.switchScene("fxml/Login.fxml", event, bdManager);
    }

    // Método para apagar o ficheiro caso o utilizador dê Logout
    private void deleteRememberMeFile() {
        try {
            FileWriter fileWriter = new FileWriter(REMEMBER_ME_FILE);
            fileWriter.write("");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void handleGoBackButton(ActionEvent event) {
        sceneSwitcher.switchScene("fxml/MainMenu.fxml", event, user, bdManager);
    }

    public void handleSaveButton(ActionEvent event) {
        if((dietTypeEdit.getText()!=null && !dietTypeEdit.getText().equals(healthData.getMedicalReasons()))
                || (allergiesEdit.getText()!=null && !allergiesEdit.getText().equals(healthData.getAllergiesOrIntolerances()))
                || (vitaminsEdit.getText()!=null && !vitaminsEdit.getText().equals(healthData.getVitaminDeficiencies()))
                || (foodPrefRestricEdit.getText()!=null && !foodPrefRestricEdit.getText().equals(healthData.getDietType()))
                || (chronicHealthEdit.getText()!=null && !chronicHealthEdit.getText().equals(healthData.getChronicHealth()))
                || (gastroIssuesEdit.getText()!=null && !gastroIssuesEdit.getText().equals(healthData.getGastrointestinalIssues()))
                || (medicationEdit.getText()!=null && !medicationEdit.getText().equals(healthData.getMedications()))){
            if(bdManager.updateDietaryRestrictions(user.getIdUser(), dietTypeEdit.getText(), allergiesEdit.getText(), vitaminsEdit.getText(), foodPrefRestricEdit.getText(), chronicHealthEdit.getText(),gastroIssuesEdit.getText(), medicationEdit.getText())){
                healthData.setMedicalReasons(dietTypeEdit.getText());
                healthData.setAllergiesOrIntolerances(allergiesEdit.getText());
                healthData.setVitaminDeficiencies(vitaminsEdit.getText());
                healthData.setDietType(foodPrefRestricEdit.getText());
                healthData.setChronicHealth(chronicHealthEdit.getText());
                healthData.setGastrointestinalIssues(gastroIssuesEdit.getText());
                healthData.setMedications(medicationEdit.getText());
                loadDietaryRestrictions();
                changeEditVisibilities(false);
            }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Save Error");
                alert.setContentText("Could not update database. Please try again later.");
                alert.showAndWait();
                changeEditVisibilities(false);
            }
        }else changeEditVisibilities(false);
    }

    public void handleCancelButton(ActionEvent event) {
        changeEditVisibilities(false);
    }
    public void goGenerateMealPlan(ActionEvent event) {
        sceneSwitcher.switchScene("fxml/HealthAndDietaryRestrictions_1.fxml", event, user);
    }
}
