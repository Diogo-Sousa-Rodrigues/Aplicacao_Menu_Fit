package pt.isec.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import pt.isec.persistence.BDManager;
import pt.isec.ui.controllers.LogInController;

import java.io.IOException;

public class MainJFX  extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        BDManager bdManager = new BDManager();
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/LogIn.fxml"));
            Parent root = loader.load();

            // Obter o controlador e inicializar com argumentos
            LogInController controller = loader.getController();
            controller.setBDManager(bdManager);
            Scene scene = new Scene(root);
            stage.setTitle("MenuFit");
            Image icon = new Image("images/logo.png");
            stage.getIcons().add(icon);
            stage.setScene(scene);
            stage.show();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
