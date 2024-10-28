package pt.isec.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import pt.isec.model.users.User;
import pt.isec.model.users.UserInitializable;

public class ProfileRestrictionsController implements UserInitializable {

    private User user;
    private final SceneSwitcher sceneSwitcher;

    @FXML
    private Label userAllergy1Label;
    @FXML
    private Label userAllergy2Label;
    @FXML
    private Label userDietTypeLabel;
    @FXML
    private Button editRestrictionsButton;
    @FXML
    private Button basicInformationButton;
    @FXML
    private Button restrictionsButton;
    @FXML
    private Button objectivesButton;
    @FXML
    private Button logOutButton;

    public ProfileRestrictionsController() {
        this.sceneSwitcher = new SceneSwitcher();
    }

    @FXML
    private void initialize() {
        loadDietaryRestrictions();
        loadDietType();
    }

    private void loadDietaryRestrictions() {

    }

    private void loadDietType() {

    }

    @FXML
    public void editRestrictionsHandler(ActionEvent event) {

    }

    @FXML
    public void basicInformationHandler(ActionEvent event) {
        sceneSwitcher.switchScene("fxml/ProfileBasicInformation.fxml", event, user);
    }

    @FXML
    public void objectivesHandler(ActionEvent event) {
        sceneSwitcher.switchScene("fxml/ProfileObjectives.fxml", event, user);
    }

    @FXML
    public void logOutHandler(ActionEvent event) {
        sceneSwitcher.switchScene("fxml/Login.fxml", event, null);
    }

    @Override
    public void initializeUser(User user) {
        this.user = user;
        loadDietaryRestrictions();
        loadDietType();
    }

    @FXML
    public void handleGoBackButton(ActionEvent event) {
        sceneSwitcher.switchScene("fxml/MainMenu.fxml", event, user);
    }
}
