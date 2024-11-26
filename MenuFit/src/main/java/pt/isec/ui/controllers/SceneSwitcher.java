package pt.isec.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.isec.model.users.BasicUser;
import pt.isec.model.users.User;
import pt.isec.model.users.UserInitializable;
import pt.isec.persistence.BDManager;

import java.io.IOException;

public class SceneSwitcher {
    private Stage stage;
    private Scene scene;
    private Parent root;

    /**
     * Switches to a new scene based on the specified FXML file.
     * Loads the FXML file, sets up the new scene, and displays it on the current stage.
     *
     * @param fxmlFile The path to the FXML file for the new scene.
     * @param event The ActionEvent triggering the scene switch, used to obtain the current stage.
     */
    public void switchScene(String fxmlFile, ActionEvent event, BDManager bdManager) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlFile));
            Parent root = loader.load();

            // Obter o controlador e inicializar com argumentos
            Object controller = loader.getController();
            if (controller instanceof RegisterController) { // Substitua por outros controladores conforme necessário
                ((RegisterController) controller).setBDManager(bdManager);
            } else if (controller instanceof LogInController) {
                ((LogInController) controller).setBDManager(bdManager);
            }

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Switches to a new scene by loading the specified FXML file and setting it on the current stage.
     * If the controller associated with the FXML file implements {@link UserInitializable},
     * it initializes the controller with the provided {@link User} object.
     *
     * @param fxmlFile The path to the FXML file for the new scene.
     * @param event The event that triggered the scene switch, used to retrieve the current stage.
     * @param user The user data to pass to the controller, if it implements {@link UserInitializable}.
     * @throws IOException if an error occurs while loading the FXML file.
     */
    public void switchScene(String fxmlFile, ActionEvent event, BasicUser user, BDManager bdManager) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlFile));
            Parent root = loader.load();

            Object controller = loader.getController();
            if (controller instanceof UserInitializable) {
                ((UserInitializable) controller).initializeUser(user, bdManager);
            }
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void switchScene(String fxmlFile, ActionEvent event, BasicUser user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlFile));
            Parent root = loader.load();

            BDManager bdManager = new BDManager();
            Object controller = loader.getController();
            if (controller instanceof UserInitializable) {
                ((UserInitializable) controller).initializeUser(user, bdManager);
            }
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
