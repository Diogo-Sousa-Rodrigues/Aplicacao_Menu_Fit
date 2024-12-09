package pt.isec.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import pt.isec.model.meals.*;
import pt.isec.model.users.BasicUser;
import pt.isec.model.users.Gender;
import pt.isec.model.users.User;
import pt.isec.persistence.BDManager;
import pt.isec.persistence.EphemeralStore;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
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
    BDManager bdManager;

    private static final String REMEMBER_ME_FILE = System.getProperty("user.home") + "/remember_me.txt";


    public LogInController(){
        this.sceneSwitcher = new SceneSwitcher();
    }

    public void setBDManager(BDManager bdManager){
        this.bdManager = bdManager;
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

    // Método temporário para salvar os dados do "Remember Me" em um ficheiro de texto
    private void saveRememberMeData(String email, String password, String firstName, String lastName, LocalDate birthdate, Gender gender) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(REMEMBER_ME_FILE))) {
            writer.write("email:" + email + "\n");
            writer.write("password:" + password + "\n");
            writer.write("firstName:" + firstName + "\n");
            writer.write("lastName:" + lastName + "\n");
            writer.write("gender: " + gender + "\n");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String birthDateFormatted = birthdate.format(formatter);
            writer.write("birthdate:" + birthDateFormatted + "\n");
        } catch (IOException e) {
            System.out.println("Erro ao salvar dados no arquivo.");
        }
    }

    @FXML
    void logInHandler(ActionEvent event) throws IOException {
        String email = emailTextField.getText();
        String password = getPassword();

        BasicUser userLoggedIn = bdManager.checkLogin(email, password);
        //EphemeralStore store = EphemeralStore.getInstance();

        //Optional<User> getResult = store.getUser(email, password);

        //sceneSwitcher.switchScene("fxml/MealPlanReview.fxml", event); //TEMPORARY FOR TESTING
        if(userLoggedIn == null){
            invalidLogin.setVisible(true);
        }else{
            //métod o temporário para poder ter um meal plan (incompleto) acessivel na base de dados
            //createTemporaryMealPlanForTesting(store, getResult.get());
            //getResult.get().setCurrentMealIndex(0);
            //sceneSwitcher.switchScene("fxml/MainMenu.fxml", event, userLoggedIn, bdManager);

            // Chamada do método para guardar os dados do "Remember Me" em um ficheiro de texto caso o Login seja bem sucedido
            saveRememberMeData(userLoggedIn.getEmail(), password, userLoggedIn.getFirstName(), userLoggedIn.getLastName(), userLoggedIn.getBirthdate(), userLoggedIn.getGender());
            sceneSwitcher.switchScene("fxml/MainMenu.fxml", event, userLoggedIn, bdManager);
        }
    }

    @FXML
    void registerNowHandler(ActionEvent event) throws IOException {
        sceneSwitcher.switchScene("fxml/Register.fxml", event, bdManager);
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

}
