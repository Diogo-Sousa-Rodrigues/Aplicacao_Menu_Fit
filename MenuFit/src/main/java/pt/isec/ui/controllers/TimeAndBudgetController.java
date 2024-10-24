package pt.isec.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TimeAndBudgetController implements Initializable {
    SceneSwitcher sceneSwitcher;
    Integer budget, time;
    @FXML
    Slider timeSlider;
    @FXML
    Slider budgetSlider;
    @FXML
    Label timeLabel;
    @FXML
    Label budgetLabel;
    @FXML
    CheckBox budgetOptionCheckBox;
    public TimeAndBudgetController(){
        this.sceneSwitcher = new SceneSwitcher();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        budget = (int) budgetSlider.getValue();
        budgetLabel.setText(budget + "€");

        time = (int) timeSlider.getValue();
        timeLabel.setText(time + "min");

        budgetSlider.valueProperty().addListener((observableValue, number, t1) -> {
            budget = (int) budgetSlider.getValue();
            budgetLabel.setText(budget + "€");
        });
        timeSlider.valueProperty().addListener((observableValue, number, t1) -> {
            time = (int) timeSlider.getValue();
            timeLabel.setText(time + "min");
        });
    }

    @FXML
    void previousHandler(ActionEvent event) throws IOException {
        sceneSwitcher.switchScene("fxml/HealthAndDietaryRestrictions_3.fxml", event);
    }
    @FXML
    void finishHandler(ActionEvent event) throws IOException{
        if(budgetOptionCheckBox.isSelected()){
            budget = null;
        }
        //TODO guardar os dados
        //sceneSwitcher.switchScene("../fxml/xxxxxxxxx.fxml", event);
    }
}
