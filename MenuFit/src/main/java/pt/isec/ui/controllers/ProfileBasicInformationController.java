package pt.isec.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import pt.isec.model.users.HealthData;
import pt.isec.model.users.User;
import pt.isec.model.users.UserInitializable;
import pt.isec.persistence.BDManager;

public class ProfileBasicInformationController implements UserInitializable {

    @FXML
    private AnchorPane editButton;
    @FXML
    private Pane optionsPane;
    @FXML
    private ImageView logoImageView;
    @FXML
    private Label menuFitLabel;
    @FXML
    private Button basicInformationButton;
    @FXML
    private Button restrictionsButton;
    @FXML
    private Button objectivesButton;
    @FXML
    private Button logOutButton;
    @FXML
    private Label nameLabel;
    @FXML
    private Pane userNamePane;
    @FXML
    private Label userNameLabel;
    @FXML
    private Label birthdateLabel;
    @FXML
    private Pane userBirthdatePane1;
    @FXML
    private Label userBirthdateLabel;
    @FXML
    private Label genderLabel;
    @FXML
    private Pane genderPane;
    @FXML
    private Label userGenderLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Pane emailPane;
    @FXML
    private Label userEmailLabel;
    @FXML
    private Label weightLabel;
    @FXML
    private Pane weightPane;
    @FXML
    private Label userWeightLabel;
    @FXML
    private Label heightLabel;
    @FXML
    private Pane heightPane;
    @FXML
    private Label userHeightLabel;
    @FXML
    private Label levelOfFitnessLabel;
    @FXML
    private Pane levelOfFitnessPane;
    @FXML
    private Label userLevelOfFitnessLabel;
    @FXML
    private Button changePasswordButton;
    @FXML
    private Button editBasicInformationButton;

    private User user;
    private SceneSwitcher sceneSwitcher;

    public ProfileBasicInformationController() {
        this.sceneSwitcher = new SceneSwitcher();
    }

    @FXML
    public void initialize() {

    }

    @Override
    public void initializeUser(User user, BDManager bdManager) {
        this.user = user;
        updateUserInfo();
    }

    private void updateUserInfo() {
        userNameLabel.setText(user.getFirstName() + " " + user.getLastName());
        userBirthdateLabel.setText(user.getBirthdate().toString());
        userGenderLabel.setText(user.getGender().toString());
        userEmailLabel.setText(user.getEmail());

        HealthData healthData = user.getHealthData();

        if (healthData != null) {
            userWeightLabel.setText(healthData.getWeight() + " kg");
            userHeightLabel.setText(healthData.getHeight() + " cm");
            userLevelOfFitnessLabel.setText(healthData.getLevelOfFitness());
        }
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
        sceneSwitcher.switchScene("fxml/Login.fxml", event, null, null);
    }

    @FXML
    public void handleGoBackButton(ActionEvent event) {
        sceneSwitcher.switchScene("fxml/MainMenu.fxml", event, user);
    }


    /*
    @FXML
    private void handleChangePasswordButton(ActionEvent event) {
        sceneSwitcher.switchScene("fxml/ChangePassword.fxml", event, user);
    }
*/
/*
    @FXML
    private void handleEditBasicInformationButton(ActionEvent event) {
        sceneSwitcher.switchScene("fxml/EditBasicInformation.fxml", event, user);
    }
}
*/
}

