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

import pt.isec.model.users.BasicUser;
import pt.isec.model.users.Gender;
import pt.isec.model.users.User;
import pt.isec.persistence.EphemeralStore;
import pt.isec.ui.controllers.MainMenuController;
import pt.isec.ui.controllers.SceneSwitcher;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class MainJFX extends Application {
    private static final String REMEMBER_ME_FILE = System.getProperty("user.home") + "/remember_me.txt";
    private SceneSwitcher sceneSwitcher;
    private BDManager bdManager = new BDManager();

    public MainJFX() {
        this.sceneSwitcher = new SceneSwitcher();
    }

    @Override
    public void start(Stage stage) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/LogIn.fxml"));
            Parent root = loader.load();

            // Obter o controlador e inicializar com argumentos
            LogInController controller = loader.getController();
            controller.setBDManager(bdManager);
            // Primeiro verifica se o utilizador já fez login anteriormente com o "Remember Me" ativo
            if (checkRememberMe(stage)) {
                return;
            }

            // Caso contrário, apresenta a tela de login
            //Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/LogIn.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("MenuFit");
            Image icon = new Image("images/logo.png");
            stage.getIcons().add(icon);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkRememberMe(Stage stage) {
        try (BufferedReader reader = new BufferedReader(new FileReader(REMEMBER_ME_FILE))) {
            String line;
            String email = null;
            String password = null;
            String firstName = null;
            String lastName = null;
            Date birthDate = null;
            String genderString = null;
            Gender gender = null;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":", 2); // Divide apenas na primeira ocorrência de ":"

                switch (parts[0].trim()) {
                    case "email":
                        email = parts[1].trim();
                        break;
                    case "password":
                        password = parts[1].trim();
                        break;
                    case "firstName":
                        firstName = parts[1].trim();
                        break;
                    case "lastName":
                        lastName = parts[1].trim();
                        break;
                    case "gender":
                        genderString = parts[1].trim();
                        break;
                    case "birthdate":
                        try {
                            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                            birthDate = formatter.parse(parts[1].trim());
                        } catch (ParseException e) {
                            System.out.println("Invalid date in rememberMe.txt: " + line);
                            return false;
                        }
                        break;


                    default:
                        System.out.println("Invalid line in rememberMe.txt: " + line);
                        return false;
                }
            }

            if ("Male".equalsIgnoreCase(genderString)) {
                gender = Gender.Male;
            } else if ("Female".equalsIgnoreCase(genderString)) {
                gender = Gender.Female;
            }

            if (email != null && password != null && firstName != null && lastName != null && gender != null && birthDate != null) {
                //EphemeralStore store = EphemeralStore.getInstance();

                // Adiciona o utilizador à store
                //BasicUser user = new BasicUser(firstName, lastName, email, birthDate, gender);
                ///store.putUser(user, password);

                // Recupera o utilizador com o email e senha
                //Optional<User> getResult = store.getUser(email, password);
                BasicUser user = bdManager.checkLogin(email, password);

                if (user != null) {
                    System.out.println(user.getFirstName() + " " + user.getLastName() + " logged in.");
                    System.out.println("Logging in automatically.");

                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/MainMenu.fxml"));
                    Parent root = loader.load();

                    // Envia as informações do utilizador para o controlador da próxima screen
                    MainMenuController controller = loader.getController();
                    controller.initializeUser(user, bdManager);

                    Scene scene = new Scene(root);
                    stage.setTitle("MenuFit");
                    Image icon = new Image("images/logo.png");
                    stage.getIcons().add(icon);
                    stage.setScene(scene);
                    stage.show();
                    return true;
                } else {
                    System.out.println("Invalid credentials in rememberMe.txt.");
                }
            }

        } catch (IOException e) {
            System.out.println("No Remember Me file found or invalid file format.");
        }

        return false;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
