package pt.isec.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import pt.isec.model.users.BasicUser;
import pt.isec.model.users.HealthData;
import pt.isec.model.users.User;
import pt.isec.model.users.UserInitializable;
import pt.isec.persistence.BDManager;

public class ProfileObjectivesController implements UserInitializable {

    @FXML
    private AnchorPane objectivesPane;
    @FXML
    private Label userObjectiveLabel;
    @FXML
    private ListView<String> objectivesListView;
    @FXML
    private Button editObjectivesButton;
    @FXML
    private Button basicInformationButton;

    private BasicUser user;
    private SceneSwitcher sceneSwitcher;
    private BDManager bdManager;

    public ProfileObjectivesController() {
        this.sceneSwitcher = new SceneSwitcher();
    }

    @FXML
    public void initialize() {
        //loadObjectives();
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
        }
    }

    @FXML
    private void handleEditObjectivesButton(ActionEvent event) {

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
        sceneSwitcher.switchScene("fxml/Login.fxml", event, null, bdManager);
    }

    @FXML
    public void handleGoBackButton(ActionEvent event) {
        sceneSwitcher.switchScene("fxml/MainMenu.fxml", event, user, bdManager);
    }

}
