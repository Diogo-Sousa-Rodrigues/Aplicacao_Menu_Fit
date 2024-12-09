package pt.isec.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import pt.isec.model.users.BasicUser;
import pt.isec.model.users.HealthData;
import pt.isec.model.users.UserInitializable;
import pt.isec.persistence.BDManager;

import java.io.FileWriter;
import java.io.IOException;

public class ProfileObjectivesController implements UserInitializable {

    public Button saveBtn;
    public Button cancelBtn;
    public TextArea objectivesEdit;
    public TextField weightGoalEdit;
    public RadioButton kgRadioButton;
    public RadioButton lbsRadioButtion;
    public TextField calorieGoalEdit;
    public Label calorieGoal;
    public Label weightGoal;
    public Label warningLabel;
    public Button generateMealPlanBtn;
    public Label units;
    @FXML
    private Label userObjectiveLabel;

    @FXML
    private Button editObjectivesButton;
    @FXML
    private Button basicInformationButton;

    private BasicUser user;
    private SceneSwitcher sceneSwitcher;
    private BDManager bdManager;
    private static final String REMEMBER_ME_FILE = System.getProperty("user.home") + "/remember_me.txt";
    public ProfileObjectivesController() {
        this.sceneSwitcher = new SceneSwitcher();
    }


    @Override
    public void initializeUser(BasicUser user, BDManager bdManager) {
        this.user = user;
        this.bdManager = bdManager;
        loadObjectives();
    }

    private void loadObjectives() {
        HealthData healthData = user.getHealthData();

        if (healthData != null) {
            userObjectiveLabel.setText(healthData.getObjective());
            weightGoal.setText(healthData.getDesiredWeight());
            calorieGoal.setText(healthData.getDailyCalorieCount());
            units.setText(user.getPreferedWeightUnit());
        }
        disableFields(user.getHealthData()==null);
    }
    private void disableFields(boolean value) {
        warningLabel.setVisible(value);
        generateMealPlanBtn.setVisible(value);
        editObjectivesButton.setDisable(value);
    }
    @FXML
    private void handleEditObjectivesButton(ActionEvent event) {
        changeEditVisibilities(true);
        objectivesEdit.setText(userObjectiveLabel.getText());
        weightGoalEdit.setText(weightGoal.getText());
        calorieGoalEdit.setText(calorieGoal.getText());
    }

    void changeEditVisibilities(boolean isVisible) {
        saveBtn.setVisible(isVisible);
        cancelBtn.setVisible(isVisible);
        objectivesEdit.setVisible(isVisible);
        weightGoalEdit.setVisible(isVisible);
        calorieGoalEdit.setVisible(isVisible);
        kgRadioButton.setVisible(isVisible);
        lbsRadioButtion.setVisible(isVisible);
        if(user.getPreferedWeightUnit() != null){
            if(user.getPreferedWeightUnit().equals("kg")){
                kgRadioButton.setSelected(true);
            }else
                lbsRadioButtion.setSelected(true);
        }
        userObjectiveLabel.setVisible(!isVisible);
        editObjectivesButton.setVisible(!isVisible);
        editObjectivesButton.setDisable(user.getHealthData()==null);
        weightGoal.setVisible(!isVisible);
        calorieGoal.setVisible(!isVisible);
        units.setVisible(!isVisible);
    }

    @FXML
    private void handleRestrictionsButton(ActionEvent event) {
        sceneSwitcher.switchScene("fxml/ProfileRestrictions.fxml", event, user, bdManager);
    }

    @FXML
    private void handleBasicInformationButton(ActionEvent event) {
        sceneSwitcher.switchScene("fxml/ProfileBasicInformation.fxml", event, user, bdManager);
    }

    @FXML
    public void handleLogOutButton(ActionEvent event) {
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
        if(!weightGoalEdit.getText().isEmpty() && !kgRadioButton.isSelected() && !lbsRadioButtion.isSelected()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Unit Error");
            alert.setContentText("Please specify a weight unit.");
            alert.showAndWait();
            return;
        }
        if(bdManager.updateObjective(user.getIdUser(), objectivesEdit.getText(), weightGoalEdit.getText(), calorieGoalEdit.getText())){
            if(kgRadioButton.isSelected()){
                user.setPreferedWeightUnit("kg");
                units.setText("kg");
            }else{
                user.setPreferedWeightUnit("lbs");
                units.setText("lbs");
            }
            userObjectiveLabel.setText(objectivesEdit.getText());
            user.getHealthData().setObjective(userObjectiveLabel.getText());
            weightGoal.setText(weightGoalEdit.getText());
            user.getHealthData().setDesiredWeight(weightGoalEdit.getText());
            calorieGoal.setText(calorieGoalEdit.getText());
            user.getHealthData().setDailyCalorieCount(calorieGoalEdit.getText());
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Save Error");
            alert.setContentText("Could not update database. Please try again later.");
            alert.showAndWait();
        }
        changeEditVisibilities(false);
    }

    public void handleCancelButton(ActionEvent event) {
        changeEditVisibilities(false);
    }
    public void goGenerateMealPlan(ActionEvent event) {
        sceneSwitcher.switchScene("fxml/HealthAndDietaryRestrictions_1.fxml", event, user);
    }
}
