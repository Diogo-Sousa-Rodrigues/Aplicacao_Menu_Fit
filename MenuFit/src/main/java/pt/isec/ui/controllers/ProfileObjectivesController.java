package pt.isec.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import pt.isec.model.users.User;
import pt.isec.model.users.UserInitializable;

public class ProfileObjectivesController implements UserInitializable {

    @FXML
    private AnchorPane objectivesPane;
    @FXML
    private Label objectivesLabel;
    @FXML
    private ListView<String> objectivesListView;
    @FXML
    private Button editObjectivesButton;
    @FXML
    private Button basicInformationButton;

    private User user;
    private SceneSwitcher sceneSwitcher;

    public ProfileObjectivesController() {
        this.sceneSwitcher = new SceneSwitcher();
    }

    @FXML
    public void initialize() {
        //loadObjectives();
    }

    @Override
    public void initializeUser(User user) {
        this.user = user;
        //loadObjectives();
    }
/*
    private void loadObjectives() {
        objectivesListView.getItems().clear();
        List<String> objectives = user.getObjectives();
        if (objectives != null) {
            objectivesListView.getItems().addAll(objectives);
        }
    }
*/
    @FXML
    private void handleEditObjectivesButton(ActionEvent event) {

    }

    @FXML
    private void handleRestrictionsButton(ActionEvent event) {
        sceneSwitcher.switchScene("fxml/ProfileRestrictions.fxml", event, user);
    }

    @FXML
    private void handleBasicInformationButton(ActionEvent event) {
        sceneSwitcher.switchScene("fxml/ProfileBasicInformation.fxml", event, user);
    }

    @FXML
    public void handleLogOutButton(ActionEvent event) {
        sceneSwitcher.switchScene("fxml/Login.fxml", event, null);
    }
}
