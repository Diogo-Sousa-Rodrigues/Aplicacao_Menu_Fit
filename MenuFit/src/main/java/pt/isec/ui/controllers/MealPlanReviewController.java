package pt.isec.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import pt.isec.model.meals.*;
import pt.isec.model.users.User;
import pt.isec.model.users.UserInitializable;
import pt.isec.persistence.BDManager;
import pt.isec.persistence.EphemeralStore;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MealPlanReviewController implements UserInitializable {
    private User user;
    private SceneSwitcher sceneSwitcher;

    @FXML
    private GridPane weekGridPane;

    private Map<String, VBox> dailyMealPanes = new HashMap<>();

    public MealPlanReviewController() {
        sceneSwitcher = new SceneSwitcher();
    }

    /**
     * Initializes the layout for each day of the week in a GridPane, arranging them in a 3x3 grid.
     * Each day gets a VBox with a label displaying the day name in bold, and the VBoxes are styled with a black border.
     * Sunday is placed in the center column.
     */
    @FXML
    public void initialize() {
        String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        for (int i = 0; i < daysOfWeek.length; i++) {
            String day = daysOfWeek[i];

            VBox dailyPane = new VBox();
            dailyPane.setSpacing(5);
            dailyPane.setPadding(new Insets(8));
            dailyPane.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
            dailyMealPanes.put(day, dailyPane);

            int column = i == 6 ? 1 : i % 3;
            int row = i / 3;
            weekGridPane.add(dailyPane, column, row);

            Label dayLabel = new Label(day);
            dayLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
            dailyPane.getChildren().add(dayLabel);
        }
    }


    @Override
    public void initializeUser(User user, BDManager bdManager) {
        this.user = user;

        // Exemplo de como adicionar refeições para testar
        addMeal("Monday", MealType.Breakfast, "Omelette", "10 min", "200 cal");
        addMeal("Monday", MealType.Dinner, "Pasta", "20 min", "500 cal");
        addMeal("Tuesday", MealType.Breakfast, "Omelette", "10 min", "200 cal");
        addMeal("Tuesday", MealType.Lunch, "Grilled Chicken Salad", "15 min", "350 cal");
        addMeal("Tuesday", MealType.Dinner, "Grilled Chicken Salad", "15 min", "350 cal");
        addMeal("Tuesday", MealType.Snack, "Grilled Chicken Salad", "15 min", "350 cal");
        addMeal("Tuesday", MealType.Snack, "Grilled Chicken Salad", "15 min", "350 cal");

    }

    /**
     * Adds a meal to the specified day, displaying the meal type in bold and other details in normal font.
     * The meal is added to the corresponding day's VBox in the GridPane.
     *
     * @param day The day to add the meal to.
     * @param type The type of meal.
     * @param recipeName The name of the meal.
     * @param time The preparation time for the meal.
     * @param calories The calorie count of the meal.
     */
    public void addMeal(String day, MealType type, String recipeName, String time, String calories) {
        if (!dailyMealPanes.containsKey(day)) return;

        VBox dailyPane = dailyMealPanes.get(day);

        Text typeText = new Text(type + ": ");
        typeText.setStyle("-fx-font-size: 13; -fx-font-weight: bold;");

        Text recipeText = new Text(recipeName + "\n" + time + ", " + calories);
        recipeText.setStyle("-fx-font-size: 13; -fx-padding: 2 0 2 0;");

        TextFlow mealTextFlow = new TextFlow(typeText, recipeText);

        dailyPane.getChildren().add(mealTextFlow);
    }

    public void btnBackHandler(ActionEvent event) {
        sceneSwitcher.switchScene("fxml/TimeAndBudget.fxml", event, user);
    }

    public void btnAcceptHandler(ActionEvent event) {
        EphemeralStore store = EphemeralStore.getInstance();
        createTemporaryMealPlanForTesting(store, user);
        user.setCurrentMealIndex(0);
        user.getHealthData().setDailyCalorieSum(0);
        sceneSwitcher.switchScene("fxml/MainMenu.fxml", event, user);
    }

    private void createTemporaryMealPlanForTesting(EphemeralStore store, User user) {
        // Criar as receitas
        Recipe breakfastRecipe = new Recipe(
                "Overnight Oats",
                "1. Coloque a aveia em uma tigela.\n2. Adicione o iogurte e misture bem.\n3. Deixe na geladeira durante a noite.\n4. Adicione frutas na manhã seguinte, se desejar.",
                1,
                300,
                Duration.ofMinutes(5),
                List.of(new Reminder("Prepare na noite anterior.")),
                List.of(
                        new Ingredient("Aveia", "Aveia integral", 50, "g", 200, new Macros(7, 34, 5), List.of("Glúten")),
                        new Ingredient("Iogurte", "Iogurte grego", 100, "ml", 100, new Macros(5, 4, 10), List.of("Lactose"))
                )
        );

        Recipe lunchRecipe = new Recipe(
                "Salada de Grão-de-Bico",
                "1. Corte o tomate em cubos.\n2. Misture o tomate e o grão-de-bico em uma tigela.\n3. Tempere a gosto com azeite e sal.",
                1,
                450,
                Duration.ofMinutes(15),
                List.of(new Reminder("Corte os legumes."), new Reminder("Misture com grão-de-bico.")),
                List.of(
                        new Ingredient("Grão-de-Bico", "Grão cozido", 150, "g", 150, new Macros(8, 45, 2), List.of()),
                        new Ingredient("Tomate", "Tomate fresco", 100, "g", 20, new Macros(1, 4, 0), List.of())
                )
        );

        Recipe dinnerRecipe = new Recipe(
                "Frango com Legumes",
                "1. Tempere o frango com sal e pimenta.\n2. Grelhe o frango até dourar.\n3. Cozinhe os brócolis no vapor.\n4. Sirva o frango acompanhado dos brócolis.",
                1,
                400,
                Duration.ofMinutes(20),
                List.of(new Reminder("Grelhe o frango."), new Reminder("Cozinhe os legumes no vapor.")),
                List.of(
                        new Ingredient("Frango", "Peito de frango grelhado", 150, "g", 200, new Macros(30, 0, 5), List.of()),
                        new Ingredient("Brócolis", "Brócolis no vapor", 100, "g", 40, new Macros(4, 7, 0), List.of())
                )
        );

        Recipe complexRecipe = new Recipe(
                "Lasagna de Legumes",
                "1. Pré-aqueça o forno a 180°C.\n" +
                        "2. Em uma panela, aqueça o azeite e refogue a cebola e o alho até dourarem.\n" +
                        "3. Adicione a cenoura, abobrinha e berinjela picadas e cozinhe até que os legumes estejam macios.\n" +
                        "4. Tempere com sal, pimenta e ervas de sua preferência.\n" +
                        "5. Em outra panela, prepare o molho de tomate: aqueça o azeite, adicione o alho picado e o tomate pelado.\n" +
                        "6. Cozinhe em fogo baixo até o molho engrossar. Tempere com sal, pimenta e manjericão fresco.\n" +
                        "7. Em uma forma, espalhe uma camada de molho de tomate, seguida de uma camada de massa de lasanha.\n" +
                        "8. Coloque uma camada dos legumes cozidos e adicione uma camada de queijo mussarela por cima.\n" +
                        "9. Repita as camadas até que todos os ingredientes acabem, finalizando com uma camada de molho e queijo.\n" +
                        "10. Cubra a forma com papel alumínio e leve ao forno por 30 minutos.\n" +
                        "11. Retire o papel alumínio e deixe gratinar por mais 10 minutos ou até dourar.\n" +
                        "12. Retire do forno, deixe descansar por 5 minutos, corte e sirva quente.",
                4,
                700,
                Duration.ofMinutes(60),
                List.of(new Reminder("Prepare o molho com antecedência."), new Reminder("Deixe descansar antes de cortar.")),
                List.of(
                        new Ingredient("Massa de Lasanha", "Massa de lasanha seca ou fresca", 200, "g", 300, new Macros(10, 60, 2), List.of("Glúten")),
                        new Ingredient("Queijo Mussarela", "Queijo mussarela ralado", 150, "g", 400, new Macros(20, 2, 30), List.of("Lactose")),
                        new Ingredient("Cenoura", "Cenoura picada", 100, "g", 35, new Macros(1, 8, 0), List.of()),
                        new Ingredient("Abobrinha", "Abobrinha picada", 100, "g", 20, new Macros(1, 4, 0), List.of()),
                        new Ingredient("Berinjela", "Berinjela picada", 100, "g", 25, new Macros(1, 5, 0), List.of()),
                        new Ingredient("Molho de Tomate", "Molho de tomate caseiro", 200, "g", 80, new Macros(1, 10, 4), List.of())
                )
        );

        Recipe smoothieRecipe = new Recipe(
                "Smoothie Verde",
                "1. Coloque todos os ingredientes no liquidificador.\n2. Bata até obter uma mistura homogênea.\n3. Sirva imediatamente.",
                1,
                250,
                Duration.ofMinutes(5),
                List.of(new Reminder("Certifique-se de que todos os ingredientes estão frescos.")),
                List.of(
                        new Ingredient("Espinafre", "Espinafre fresco", 50, "g", 15, new Macros(2, 1, 0), List.of("Vitamina K")),
                        new Ingredient("Banana", "Banana madura", 100, "g", 89, new Macros(1, 23, 0), List.of("Potássio")),
                        new Ingredient("Leite", "Leite sem lactose", 200, "ml", 100, new Macros(7, 10, 3), List.of("Sem lactose")),
                        new Ingredient("Mel", "Mel natural", 10, "g", 30, new Macros(0, 8, 0), List.of("Açúcares naturais"))
                )
        );

        Recipe veggieOmeletteRecipe = new Recipe(
                "Omelete de Vegetais",
                "1. Bata os ovos em uma tigela com sal e pimenta.\n2. Aqueça uma frigideira antiaderente com azeite.\n3. Adicione os vegetais picados e refogue por 2 minutos.\n4. Despeje os ovos batidos sobre os vegetais.\n5. Cozinhe em fogo baixo até firmar e vire para dourar o outro lado.\n6. Sirva quente.",
                1,
                250,
                Duration.ofMinutes(10),
                List.of(new Reminder("Prepare os vegetais antes de começar a cozinhar.")),
                List.of(
                        new Ingredient("Ovo", "Ovo grande", 2, "unidades", 140, new Macros(12, 1, 10), List.of("Proteínas")),
                        new Ingredient("Pimentão", "Pimentão vermelho picado", 50, "g", 20, new Macros(1, 5, 0), List.of("Vitamina C")),
                        new Ingredient("Cebola", "Cebola picada", 30, "g", 12, new Macros(0, 3, 0), List.of("Antioxidantes")),
                        new Ingredient("Azeite de Oliva", "Azeite extra virgem", 10, "ml", 80, new Macros(0, 0, 9), List.of("Gorduras saudáveis"))
                )
        );


        // Criar as refeições usando as receitas
        Meal breakfast = new Meal(MealType.Breakfast, LocalDateTime.now(), breakfastRecipe);
        breakfast.setMealIndex(0);
        //breakfast.setType(MealType.Breakfast);
        Meal lunch = new Meal(MealType.Lunch, LocalDateTime.now(), lunchRecipe);
        lunch.setMealIndex(1);
        //lunch.setType(MealType.Lunch);
        //Meal dinner = new Meal(dinnerRecipe);
        //dinner.setMealIndex(2);
        //dinner.setType(MealType.Dinner);
        Meal dinner2 = new Meal(MealType.Dinner, LocalDateTime.now(), complexRecipe);
        dinner2.setMealIndex(2);
        //dinner2.setType(MealType.Dinner);


        Meal breakfast2 = new Meal(MealType.Breakfast, LocalDateTime.now().plusDays(1), smoothieRecipe);
        breakfast2.setMealIndex(3);

        Meal lunch2 = new Meal(MealType.Lunch, LocalDateTime.now().plusDays(1), veggieOmeletteRecipe);
        lunch2.setMealIndex(4);

        // Criar o MealPlan e adicionar ao usuário
        MealPlan mealPlan = new MealPlan(user);
        store.putMealPlan(user, mealPlan);
        // Associar as refeições ao MealPlan
        mealPlan.putMeals(List.of(breakfast, lunch, dinner2, breakfast2, lunch2));
        //este setCurrentRecipe depois deverá ser chamado quando uma receita for escolhida na lista de receitas
        user.setCurrentRecipe("Lasagna de Legumes");
    }
}
