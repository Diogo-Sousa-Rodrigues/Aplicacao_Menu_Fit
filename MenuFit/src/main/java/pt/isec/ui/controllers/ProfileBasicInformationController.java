package pt.isec.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pt.isec.model.users.BasicUser;
import pt.isec.model.users.Gender;
import pt.isec.model.users.HealthData;
import pt.isec.model.users.UserInitializable;
import pt.isec.persistence.BDManager;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class ProfileBasicInformationController implements UserInitializable {
    public TextField nameEdit;
    public DatePicker birthdateEdit;
    public ComboBox genderEdit;
    public TextField emailEdit;
    public TextField weightEdit;
    public TextField heightEdit;
    public ComboBox fitnessLevelEdit;
    public Button SaveBtn;
    public Button cancelBtn;
    public Label changePasswordLabel;
    public PasswordField oldPasswordEdit;
    public PasswordField newPasswordEdit;
    public PasswordField confirmNewPasswordEdit;
    public Label warningLabel;
    public Button generateMealPlanBtn;
    @FXML
    private Label userNameLabel;

    @FXML
    private Label userBirthdateLabel;

    @FXML
    private Label userGenderLabel;

    @FXML
    private Label userEmailLabel;

    @FXML
    private Label userWeightLabel;

    @FXML
    private Label userHeightLabel;

    @FXML
    private Label userLevelOfFitnessLabel;

    @FXML
    private Button editBasicInformationButton;

    private BasicUser user;
    private SceneSwitcher sceneSwitcher;
    private BDManager bdManager;

    private static final String REMEMBER_ME_FILE = System.getProperty("user.home") + "/remember_me.txt";

    public ProfileBasicInformationController() {
        this.sceneSwitcher = new SceneSwitcher();
    }

    @Override
    public void initializeUser(BasicUser user, BDManager bdManager) {
        this.user = user;
        this.bdManager = bdManager;
        HealthData healthData = bdManager.loadHealthAndDietaryRestrictions(user.getIdUser());
        changeEditVisibilities(false);
        if (healthData != null) {
            this.user.setHealthData(healthData);
        }
        disableFields(user.getHealthData()==null);
        updateUserInfo();

    }

    private void disableFields(boolean value) {
        editBasicInformationButton.setDisable(value);
        warningLabel.setVisible(value);
        generateMealPlanBtn.setVisible(value);
    }

    private void changeEditVisibilities(boolean value) {
        userNameLabel.setVisible(!value);
        userBirthdateLabel.setVisible(!value);
        userEmailLabel.setVisible(!value);
        userGenderLabel.setVisible(!value);
        userHeightLabel.setVisible(!value);
        userWeightLabel.setVisible(!value);
        userLevelOfFitnessLabel.setVisible(!value);
        editBasicInformationButton.setVisible(!value);
        //editBasicInformationButton.setDisable(user.getHealthData()==null);
        nameEdit.setVisible(value);
        birthdateEdit.setVisible(value);
        genderEdit.setVisible(value);
        emailEdit.setVisible(value);
        weightEdit.setVisible(value);
        heightEdit.setVisible(value);
        fitnessLevelEdit.setVisible(value);
        SaveBtn.setVisible(value);
        cancelBtn.setVisible(value);
        changePasswordLabel.setVisible(value);
        oldPasswordEdit.setVisible(value);
        newPasswordEdit.setVisible(value);
        confirmNewPasswordEdit.setVisible(value);
    }
    private void updateUserInfo() {
        userNameLabel.setText(user.getFirstName() + " " + user.getLastName());
        userBirthdateLabel.setText(user.getBirthdate().toString());
        userGenderLabel.setText(user.getGender().toString());
        userEmailLabel.setText(user.getEmail());

        HealthData healthData = user.getHealthData();

        if (healthData != null) {
            userWeightLabel.setText(healthData.getWeight());
            userHeightLabel.setText(healthData.getHeight());
            userLevelOfFitnessLabel.setText(healthData.getLevelOfFitness());
        }
        genderEdit.getItems().addAll("Male", "Female", "Other");
        fitnessLevelEdit.getItems().addAll("Beginner", "Intermediate", "Advanced");
    }

    @FXML
    private void handleRestrictionsButton(ActionEvent event) {
        sceneSwitcher.switchScene("fxml/ProfileRestrictions.fxml", event, user);
    }

    @FXML
    public void handleObjectivesButton(ActionEvent event) {
        sceneSwitcher.switchScene("fxml/ProfileObjectives.fxml", event, user);
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
        sceneSwitcher.switchScene("fxml/MainMenu.fxml", event, user);
    }

    public void handleEditButton(ActionEvent event) {
        changeEditVisibilities(true);
        //por os valores nos edits
        nameEdit.setText(user.getFirstName()+" "+user.getLastName());
        birthdateEdit.setValue(user.getBirthdate());
        genderEdit.setValue(user.getGender().toString());
        emailEdit.setText(user.getEmail());
        heightEdit.setText(userHeightLabel.getText());
        weightEdit.setText(userWeightLabel.getText());
        fitnessLevelEdit.setValue(userLevelOfFitnessLabel.getText());
        oldPasswordEdit.clear();
        newPasswordEdit.clear();
        confirmNewPasswordEdit.clear();
    }

    public void handleSaveButton(ActionEvent event) {
        Border redBorder = new Border(new BorderStroke(
                Color.RED,
                BorderStrokeStyle.SOLID,
                new CornerRadii(3),
                new BorderWidths(2)
        ));

        boolean hasEmptyField = false;

        TextField[] fields = {nameEdit, emailEdit, heightEdit, weightEdit};

        for (TextField field : fields) {
            if (field.getText().isEmpty()) {
                field.setBorder(redBorder);
                hasEmptyField = true;
            } else {
                field.setBorder(null); // Remove borda se o campo estiver preenchido
            }
        }

        if (hasEmptyField) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setContentText("Please fill all the required fields.");
            alert.showAndWait();
            return;
        }


        if(!oldPasswordEdit.getText().isEmpty() || !newPasswordEdit.getText().isEmpty() || !confirmNewPasswordEdit.getText().isEmpty()) {
            if(oldPasswordEdit.getText().isEmpty() || newPasswordEdit.getText().isEmpty() || confirmNewPasswordEdit.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Save Error");
                alert.setHeaderText("Missing password fields.");
                alert.setContentText("Please fill out all password fields. If you don't want to change it, clear all password fields.");
                oldPasswordEdit.setBorder(redBorder);
                newPasswordEdit.setBorder(redBorder);
                confirmNewPasswordEdit.setBorder(redBorder);
                return;
            }
            if (oldPasswordEdit.getText().equals(bdManager.getUserPassword(user.getIdUser()))) {
                if (newPasswordEdit.getText().equals(confirmNewPasswordEdit.getText())) {
                    if(bdManager.setNewPassword(user.getIdUser(), newPasswordEdit.getText())){
                        oldPasswordEdit.setBorder(null);
                        oldPasswordEdit.setBorder(null);
                        oldPasswordEdit.setBorder(null);
                    }else{
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Save Error");
                        alert.setContentText("Could not save new password. Please try again later.");
                        return;
                    }

                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Password Error");
                    alert.setContentText("New password fields don't match");
                    alert.showAndWait();
                    newPasswordEdit.setBorder(redBorder);
                    confirmNewPasswordEdit.setBorder(redBorder);
                    return;
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Password Error");
                alert.setContentText("Old password incorrect");
                alert.showAndWait();
                oldPasswordEdit.setBorder(redBorder);
                return;
            }
        }

        if(!nameEdit.getText().equals(user.getFirstName() + " " + user.getLastName()) || !emailEdit.getText().equals(user.getEmail()) || !genderEdit.getValue().toString().equals(user.getGender().toString()) || !birthdateEdit.getValue().toString().equals(user.getBirthdate().toString())){
            if(bdManager.updateUserInfo(user.getIdUser(), emailEdit.getText(), birthdateEdit.getValue().toString(), genderEdit.getValue().toString(), nameEdit.getText())){
                String[] nameParts = nameEdit.getText().trim().split("\\s+", 2);

                if (nameParts.length > 0) {
                    user.setFirstName(nameParts[0]); // Primeira palavra
                    user.setLastName(nameParts.length > 1 ? nameParts[1] : ""); // Restante ou vazio
                }
                userNameLabel.setText(nameEdit.getText());

                user.setEmail(emailEdit.getText());
                userEmailLabel.setText(emailEdit.getText());

                user.setGender(Gender.valueOf((String) genderEdit.getValue()));
                userGenderLabel.setText(genderEdit.getValue().toString());
                user.setBirthdate(birthdateEdit.getValue());
                userBirthdateLabel.setText(birthdateEdit.getValue().toString());
            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Save Error");
                alert.setContentText("Could not save user info. Please try again later.");
                alert.showAndWait();
                return;
            }
        }

        if(heightEdit.getText().matches("\\d+") && weightEdit.getText().matches("\\d+")){
            if(!heightEdit.getText().equals(user.getHealthData().getHeight()) || !weightEdit.getText().equals(user.getHealthData().getWeight())) {
                if(bdManager.updateUserHeightAndWeight(user.getIdUser(), heightEdit.getText(), weightEdit.getText())){
                    user.getHealthData().setHeight(heightEdit.getText());
                    userHeightLabel.setText(heightEdit.getText());
                    user.getHealthData().setWeight(weightEdit.getText());
                    userWeightLabel.setText(weightEdit.getText());
                }else{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Save Error");
                    alert.setContentText("Could not save user height or weight in database. Please try again later.");
                    alert.showAndWait();
                    return;
                }
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Input Error");
            alert.setContentText("Height and Weight should be numeric");
            alert.showAndWait();
            return;
        }

        changeEditVisibilities(false);
        updateRememberMeFile();
    }
    private void updateRememberMeFile() {
        try (FileWriter fileWriter = new FileWriter(REMEMBER_ME_FILE)) {
            String content = String.format(
                    "email:%s%npassword:%s%nfirstName:%s%nlastName:%s%ngender:%s%nbirthdate:%s",
                    user.getEmail(),
                    bdManager.getUserPassword(user.getIdUser()),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getGender().toString(),
                    new SimpleDateFormat("dd-MM-yyyy").format(user.getBirthdate())
            );
            fileWriter.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void handleCancelButton(ActionEvent event) {
        changeEditVisibilities(false);
    }

    public void goGenerateMealPlan(ActionEvent event) {
        sceneSwitcher.switchScene("fxml/HealthAndDietaryRestrictions_1.fxml", event, user);
    }
}

