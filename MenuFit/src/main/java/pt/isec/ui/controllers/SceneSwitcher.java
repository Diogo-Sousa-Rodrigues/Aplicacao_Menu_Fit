package pt.isec.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneSwitcher {
    private Stage stage;
    private Scene scene;
    private Parent root;

    // Método para mudar de cena
    public void switchScene(String fxmlFile, ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource(fxmlFile));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
