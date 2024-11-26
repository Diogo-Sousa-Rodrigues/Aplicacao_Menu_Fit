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
    private void saveRememberMeData(String email, String password, String firstName, String lastName, Date birthdate, Gender gender) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(REMEMBER_ME_FILE))) {
            writer.write("email:" + email + "\n");
            writer.write("password:" + password + "\n");
            writer.write("firstName:" + firstName + "\n");
            writer.write("lastName:" + lastName + "\n");
            writer.write("gender: " + gender + "\n");
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            String birthDateFormatted = formatter.format(birthdate);
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

//    private void createTemporaryMealPlanForTesting(EphemeralStore store, User user) {
//        // Criar as receitas
//        Recipe breakfastRecipe = new Recipe(
//                "Overnight Oats",
//                "1. Coloque a aveia em uma tigela.\n2. Adicione o iogurte e misture bem.\n3. Deixe na geladeira durante a noite.\n4. Adicione frutas na manhã seguinte, se desejar.",
//                1,
//                300,
//                Duration.ofMinutes(5),
//                List.of(new Reminder("Prepare na noite anterior.")),
//                List.of(
//                        new Ingredient("Aveia", "Aveia integral", 50, "g", 200, new Macros(7, 34, 5), List.of("Glúten")),
//                        new Ingredient("Iogurte", "Iogurte grego", 100, "ml", 100, new Macros(5, 4, 10), List.of("Lactose"))
//                )
//        );
//
//        Recipe lunchRecipe = new Recipe(
//                "Salada de Grão-de-Bico",
//                "1. Corte o tomate em cubos.\n2. Misture o tomate e o grão-de-bico em uma tigela.\n3. Tempere a gosto com azeite e sal.",
//                1,
//                450,
//                Duration.ofMinutes(15),
//                List.of(new Reminder("Corte os legumes."), new Reminder("Misture com grão-de-bico.")),
//                List.of(
//                        new Ingredient("Grão-de-Bico", "Grão cozido", 150, "g", 150, new Macros(8, 45, 2), List.of()),
//                        new Ingredient("Tomate", "Tomate fresco", 100, "g", 20, new Macros(1, 4, 0), List.of())
//                )
//        );
//
//        Recipe dinnerRecipe = new Recipe(
//                "Frango com Legumes",
//                "1. Tempere o frango com sal e pimenta.\n2. Grelhe o frango até dourar.\n3. Cozinhe os brócolis no vapor.\n4. Sirva o frango acompanhado dos brócolis.",
//                1,
//                400,
//                Duration.ofMinutes(20),
//                List.of(new Reminder("Grelhe o frango."), new Reminder("Cozinhe os legumes no vapor.")),
//                List.of(
//                        new Ingredient("Frango", "Peito de frango grelhado", 150, "g", 200, new Macros(30, 0, 5), List.of()),
//                        new Ingredient("Brócolis", "Brócolis no vapor", 100, "g", 40, new Macros(4, 7, 0), List.of())
//                )
//        );
//
//        Recipe complexRecipe = new Recipe(
//                "Lasagna de Legumes",
//                "1. Pré-aqueça o forno a 180°C.\n" +
//                        "2. Em uma panela, aqueça o azeite e refogue a cebola e o alho até dourarem.\n" +
//                        "3. Adicione a cenoura, abobrinha e berinjela picadas e cozinhe até que os legumes estejam macios.\n" +
//                        "4. Tempere com sal, pimenta e ervas de sua preferência.\n" +
//                        "5. Em outra panela, prepare o molho de tomate: aqueça o azeite, adicione o alho picado e o tomate pelado.\n" +
//                        "6. Cozinhe em fogo baixo até o molho engrossar. Tempere com sal, pimenta e manjericão fresco.\n" +
//                        "7. Em uma forma, espalhe uma camada de molho de tomate, seguida de uma camada de massa de lasanha.\n" +
//                        "8. Coloque uma camada dos legumes cozidos e adicione uma camada de queijo mussarela por cima.\n" +
//                        "9. Repita as camadas até que todos os ingredientes acabem, finalizando com uma camada de molho e queijo.\n" +
//                        "10. Cubra a forma com papel alumínio e leve ao forno por 30 minutos.\n" +
//                        "11. Retire o papel alumínio e deixe gratinar por mais 10 minutos ou até dourar.\n" +
//                        "12. Retire do forno, deixe descansar por 5 minutos, corte e sirva quente.",
//                4,
//                700,
//                Duration.ofMinutes(60),
//                List.of(new Reminder("Prepare o molho com antecedência."), new Reminder("Deixe descansar antes de cortar.")),
//                List.of(
//                        new Ingredient("Massa de Lasanha", "Massa de lasanha seca ou fresca", 200, "g", 300, new Macros(10, 60, 2), List.of("Glúten")),
//                        new Ingredient("Queijo Mussarela", "Queijo mussarela ralado", 150, "g", 400, new Macros(20, 2, 30), List.of("Lactose")),
//                        new Ingredient("Cenoura", "Cenoura picada", 100, "g", 35, new Macros(1, 8, 0), List.of()),
//                        new Ingredient("Abobrinha", "Abobrinha picada", 100, "g", 20, new Macros(1, 4, 0), List.of()),
//                        new Ingredient("Berinjela", "Berinjela picada", 100, "g", 25, new Macros(1, 5, 0), List.of()),
//                        new Ingredient("Molho de Tomate", "Molho de tomate caseiro", 200, "g", 80, new Macros(1, 10, 4), List.of())
//                )
//        );
//
//        // Criar as refeições usando as receitas
//        Meal breakfast = new Meal(breakfastRecipe);
//        breakfast.setMealIndex(0);
//        breakfast.setType(MealType.Breakfast);
//        Meal lunch = new Meal(lunchRecipe);
//        lunch.setMealIndex(1);
//        lunch.setType(MealType.Lunch);
//        //Meal dinner = new Meal(dinnerRecipe);
//        //dinner.setMealIndex(2);
//        //dinner.setType(MealType.Dinner);
//        Meal dinner2 = new Meal(complexRecipe);
//        dinner2.setMealIndex(2);
//        dinner2.setType(MealType.Dinner);
//
//        // Criar o MealPlan e adicionar ao usuário
//        MealPlan mealPlan = new MealPlan(user);
//        mealPlan.putMeals(List.of(breakfast, lunch, dinner2));
//
//        store.putMealPlan(user, mealPlan);
//
//        //este setCurrentRecipe depois deverá ser chamado quando uma receita for escolhida na lista de receitas
//        user.setCurrentRecipe("Lasagna de Legumes");
//    }

}
