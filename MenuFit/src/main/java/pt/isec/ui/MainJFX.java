package pt.isec.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MainJFX  extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try{
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/LogIn.fxml"));
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
