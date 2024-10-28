package pt.isec.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import pt.isec.model.users.User;
import pt.isec.persistence.EphemeralStore;

import java.io.IOException;
import java.util.Optional;


public class LogInController {
    SceneSwitcher sceneSwitcher;
    @FXML
    public TextField emailTextField;
    @FXML
    public TextField passwordTextField;
    @FXML
    public PasswordField hiddenPasswordField;
    @FXML
    public CheckBox showPassword;
    @FXML
    public Label invalidLogin;

    public LogInController(){
        this.sceneSwitcher = new SceneSwitcher();
    }

    @FXML
    void changeVisibility(ActionEvent event){
        if(showPassword.isSelected()){
            passwordTextField.setText(hiddenPasswordField.getText());
            passwordTextField.setVisible(true);
            hiddenPasswordField.setVisible(false);
            return;
        }
        hiddenPasswordField.setText(passwordTextField.getText());
        hiddenPasswordField.setVisible(true);
        passwordTextField.setVisible(false);
    }

    @FXML
    void logInHandler(ActionEvent event) throws IOException {
        String email = emailTextField.getText();
        String password = getPassword();

        EphemeralStore store = EphemeralStore.getInstance();

        Optional<User> getResult= store.getUser(email, password);

        //sceneSwitcher.switchScene("fxml/MainMenu.fxml", event); //TEMPORARY FOR TESTING
        if(getResult.isEmpty()){
            invalidLogin.setVisible(true);
        }else{
            sceneSwitcher.switchScene("fxml/MainMenu.fxml", event, getResult.get());
        }
    }

    @FXML
    void registerNowHandler(ActionEvent event) throws IOException {
        sceneSwitcher.switchScene("fxml/Register.fxml", event);
    }

    @FXML
    void rememberMeHandler(ActionEvent event){
        //TODO
    }

    private String getPassword(){
        if(passwordTextField.isVisible()){
            return passwordTextField.getText();
        }else{
            return hiddenPasswordField.getText();
        }
    }

    //test

}
