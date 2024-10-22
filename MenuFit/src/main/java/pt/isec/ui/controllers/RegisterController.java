package pt.isec.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import pt.isec.model.users.BasicUser;
import pt.isec.model.users.Gender;
import pt.isec.persistence.EphemeralStore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegisterController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private DatePicker birthDatePicker;

    @FXML
    private ComboBox genderComboBox;

    @FXML
    private void handleRegister(ActionEvent event) {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String birthDateString = birthDatePicker.getValue().toString();

        Date birthDate = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            birthDate = formatter.parse(birthDateString);
        } catch (ParseException e) {
            showAlert("Invalid Date", "Please enter a valid date in the format dd-MM-yyyy");
            return;
        }

        // alterar a logica do genero, segundo o novo codigo do julio
        Gender gender = Gender.Male;

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || birthDate == null /*|| gender por enquanto é sempre null*/) {
            showAlert("Validation Error", "Please enter all details");
            return;
        }

        EphemeralStore store = EphemeralStore.getInstance();
        BasicUser newUser = new BasicUser(firstName, lastName, email, birthDate, gender);
        store.putUser(newUser, password);

        showAlert("Registration Successful", "User Registered Successfully!");

        SceneSwitcher sceneSwitcher = new SceneSwitcher();
        sceneSwitcher.switchScene("/UI/fxml/LogIn.fxml", event);
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        // Clear all fields
        firstNameField.clear();
        lastNameField.clear();
        emailField.clear();
        passwordField.clear();
        birthDatePicker.setValue(null);
        genderComboBox.setValue(null);

        // Switch back to login scene if desired
        SceneSwitcher sceneSwitcher = new SceneSwitcher();
        sceneSwitcher.switchScene("/UI/fxml/LogIn.fxml", event);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}