package pt.isec.persistence;

import pt.isec.model.meals.*;
import pt.isec.model.users.BasicUser;
import pt.isec.model.users.Gender;
import pt.isec.model.users.User;

import java.io.Serializable;
import java.sql.*;
import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class BDManager implements Serializable {
    private String dbURL;
    private String dbName;

    private Connection dbConn;


    private static final Long version = 01L;


    public BDManager() {
        // Create a File object for the directory
        this.dbURL = "jdbc:sqlite:src/Base_de_Dados_MenuFit.db";
        this.connect();
    }

    private void connect() {
        try {
            this.dbConn = DriverManager.getConnection(this.dbURL);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.closeConnection();
        }
    }

    public void closeConnection() {
        try {
            if (this.dbConn != null) {
                this.dbConn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void registerUser(String fullName, String email, String password, String gender, String birthDateString) {
        String sql = "INSERT INTO USER (Email, Password, Date_of_Birth, Gender, Name) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = this.dbConn.prepareStatement(sql)) {
            // Configurar os parâmetros do PreparedStatement
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            pstmt.setString(3, birthDateString);
            pstmt.setString(4, gender);
            pstmt.setString(5, fullName);

            // Executar a inserção
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User registered successfully!");
            } else {
                System.out.println("Failed to register user.");
            }
        } catch (SQLException e) {
            System.out.println("Error while registering user: " + e.getMessage());
        }
    }

    public BasicUser checkLogin(String email, String password) {
        String query = "SELECT * FROM USER WHERE Email = ? AND Password = ?";
        try(PreparedStatement stmt = this.dbConn.prepareStatement(query)) {

            // Define os parâmetros da consulta
            stmt.setString(1, email);
            stmt.setString(2, password);

            // Executa a consulta
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Extrai os dados do banco
                Integer idUser = rs.getInt("UserID");
                String firstName = rs.getString("Name").split(" ")[0]; // Assume o primeiro nome
                String lastName = rs.getString("Name").replaceFirst(firstName, "").trim(); // Assume o restante como sobrenome
                String userEmail = rs.getString("Email");
                Date birthdate = Date.valueOf(rs.getString("Date_of_Birth")); // Converte para java.sql.Date
                Gender gender = Gender.valueOf(rs.getString("Gender")); // Converte para enum Gender

                // Cria um BasicUser com os dados extraídos
                return new BasicUser(idUser, firstName, lastName, userEmail, birthdate, gender);
            } else {
                // Retorna null se o login falhar
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean saveMealPlan(Optional<MealPlan> mealPlan){
        String sql = "INSERT INTO MEALPLAN (UserID, Begin_Date, End_Date, Goal) VALUES (?, ?, ?, ?)";
        Integer mealPlanID;
        try (PreparedStatement pstmt = this.dbConn.prepareStatement(sql)) {
            // Configurar os parâmetros do PreparedStatement
            pstmt.setInt(1, mealPlan.get().getUserID());
            pstmt.setString(2, mealPlan.get().getBeginDate().toString());
            pstmt.setString(3, mealPlan.get().getEndDate().toString());
            pstmt.setString(4, mealPlan.get().getGoal());

            // Executar a inserção
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                try (Statement stmt = dbConn.createStatement();
                     ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ROWID()")) {
                    if (rs.next()) {
                         mealPlanID = rs.getInt(1);
                         return saveMeals(mealPlan, mealPlanID);
                    }
                }
            } else {
                System.out.println("Failed to register mealplan.");
            }
        } catch (SQLException e) {
            System.out.println("Error while registering mealplan: " + e.getMessage());
        }
        return false;
    }

    private boolean saveMeals(Optional<MealPlan> mealPlan, Integer mealPlanID) {
        boolean success = true;
        for(Meal meal : mealPlan.get().getMeals()){
           String sql = "INSERT INTO MEAL (MealPlanID, Type, Date) VALUES (?, ?, ?)";

            try (PreparedStatement pstmt = this.dbConn.prepareStatement(sql)) {
                // Configurar os parâmetros do PreparedStatement
                pstmt.setInt(1, mealPlanID);
                pstmt.setString(2, meal.getType().toString());
                pstmt.setString(3, meal.getDate().toString());

                // Executar a inserção
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    try (Statement stmt = dbConn.createStatement();
                         ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ROWID()")) {
                        if (rs.next()) {
                            Integer mealID = rs.getInt(1);
                            if(!saveRecipe(meal, mealID)) success = false;
                        }
                    }
                } else {
                    System.out.println("Failed to register meal.");
                    success = false;
                }
            } catch (SQLException e) {
                System.out.println("Error while registering meal: " + e.getMessage());
                success = false;
            }
        }
        return success;
    }

    private boolean saveRecipe(Meal meal, Integer mealID) {
        Recipe recipe = meal.getRecipe();
        String sql = "INSERT INTO RECIPE (MealID, Name, Description, Servings, Calories, Prep_Time) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = this.dbConn.prepareStatement(sql)) {
            // Configurar os parâmetros do PreparedStatement
            pstmt.setInt(1, mealID);
            pstmt.setString(2, recipe.name());
            pstmt.setString(3, recipe.description());
            pstmt.setInt(4, recipe.servings());
            pstmt.setInt(5, recipe.calories());
            pstmt.setString(6, recipe.prep().toString());

            // Executar a inserção
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                try (Statement stmt = dbConn.createStatement();
                     ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ROWID()")) {
                    if (rs.next()) {
                        Integer recipeID = rs.getInt(1);
                        return (saveIngredients(recipe, recipeID) && saveReminders(recipe, recipeID));
                    }
                }
            } else {
                System.out.println("Failed to register meal.");
            }
        } catch (SQLException e) {
            System.out.println("Error while registering meal: " + e.getMessage());
        }
        return false;
    }

    private boolean saveReminders(Recipe recipe, Integer recipeID) {
        boolean success = true;
        for(Reminder reminder : recipe.reminders()){
            String sql = "INSERT INTO REMINDERS (RecipeID, 'Check', Text) VALUES (?, ?, ?)";

            try (PreparedStatement pstmt = this.dbConn.prepareStatement(sql)) {
                // Configurar os parâmetros do PreparedStatement
                pstmt.setInt(1, recipeID);
                pstmt.setInt(2, reminder.getCheck() ? 1 : 0);
                pstmt.setString(3, reminder.getData());
                // Executar a inserção
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected <= 0) {
                    System.out.println("Failed to register reminder.");
                    success = false;
                }
            } catch (SQLException e) {
                System.out.println("Error while registering reminder: " + e.getMessage());
                success = false;
            }
        }
        return success;
    }

    private boolean saveIngredients(Recipe recipe, Integer recipeID) {
        boolean success = true;
        for(Ingredient ingredient : recipe.ingredients()){
            String sql = "INSERT INTO INGREDIENTS (RecipeID, Name, Description, Quantity, Units, Calories, Allergens, Proteins, Carbs, Fats) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement pstmt = this.dbConn.prepareStatement(sql)) {
                // Configurar os parâmetros do PreparedStatement
                pstmt.setInt(1, recipeID);
                pstmt.setString(2, ingredient.name());
                pstmt.setString(3, ingredient.description());
                pstmt.setFloat(4, ingredient.quantity());
                pstmt.setString(5, ingredient.units());
                pstmt.setInt(6, ingredient.calories());
                pstmt.setString(7, String.valueOf(ingredient.allergens()));
                pstmt.setFloat(8, ingredient.macros().proteins());
                pstmt.setFloat(9, ingredient.macros().carbs());
                pstmt.setFloat(10, ingredient.macros().fats());
                // Executar a inserção
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected <= 0) {
                    System.out.println("Failed to register ingredient.");
                    success = false;
                }
            } catch (SQLException e) {
                System.out.println("Error while registering ingredient: " + e.getMessage());
                success = false;
            }
        }
        return success;
    }

    public MealPlan getMealPlan(Integer userID) {
        MealPlan mealPlan = null;
        String sql = "SELECT * FROM MEALPLAN WHERE UserID = ? ORDER BY Begin_Date DESC LIMIT 1";

        try (PreparedStatement pstmt = this.dbConn.prepareStatement(sql)) {
            pstmt.setInt(1, userID);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Criar MealPlan a partir dos dados
                    Integer mealPlanID = rs.getInt("MealPlanID");
                    String beginDate = rs.getString("Begin_Date");
                    String endDate = rs.getString("End_Date");
                    String goal = rs.getString("Goal");

                    // Criar o MealPlan
                    mealPlan = new MealPlan(mealPlanID, userID, beginDate, endDate, goal);

                    // Carregar as refeições associadas ao MealPlan
                    loadMealsForMealPlan(mealPlan);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while fetching meal plan: " + e.getMessage());
        }

        return mealPlan;
    }

    private void loadMealsForMealPlan(MealPlan mealPlan) {
        String sql = "SELECT * FROM MEAL WHERE MealPlanID = ?";

        try (PreparedStatement pstmt = this.dbConn.prepareStatement(sql)) {
            pstmt.setInt(1, mealPlan.getMealPlanID());

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    // Criar a Meal associada
                    Integer mealID = rs.getInt("MealID");
                    String type = rs.getString("Type");
                    String date = rs.getString("Date");

                    Meal meal = new Meal(mealID, type, date);

                    // Carregar a receita associada à refeição
                    loadRecipeForMeal(meal);

                    // Adicionar a refeição ao MealPlan
                    mealPlan.putMeal(meal);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while fetching meals: " + e.getMessage());
        }
    }

    private void loadRecipeForMeal(Meal meal) {
        String sql = "SELECT * FROM RECIPE WHERE MealID = ?";

        try (PreparedStatement pstmt = this.dbConn.prepareStatement(sql)) {
            pstmt.setInt(1, meal.getMealID());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Integer recipeID = rs.getInt("RecipeID");
                    String name = rs.getString("Name");
                    String description = rs.getString("Description");
                    int servings = rs.getInt("Servings");
                    int calories = rs.getInt("Calories");
                    String prepTime = rs.getString("Prep_Time");

                    Duration durationPrep = Duration.parse(prepTime);



                    // Carregar os ingredientes da receita
                    List<Ingredient> ingredients =  loadIngredientsForRecipe(recipeID);

                    // Carregar os lembretes da receita
                    List<Reminder> reminders = loadRemindersForRecipe(recipeID);

                    Recipe recipe = new Recipe(recipeID, name, description, servings, calories, durationPrep, reminders, ingredients);

                    // Associar a receita à refeição
                    meal.setRecipe(recipe);

                }
            }
        } catch (SQLException e) {
            System.out.println("Error while fetching recipe: " + e.getMessage());
        }
    }

    private List<Ingredient> loadIngredientsForRecipe(Integer recipeID) {
        String sql = "SELECT * FROM INGREDIENTS WHERE RecipeID = ?";

        List<Ingredient> ingredients = new ArrayList<>();

        try (PreparedStatement pstmt = this.dbConn.prepareStatement(sql)) {
            pstmt.setInt(1, recipeID);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Integer ingredientID = rs.getInt("IngredientsID");
                    String name = rs.getString("Name");
                    String description = rs.getString("Description");
                    float quantity = rs.getFloat("Quantity");
                    String units = rs.getString("Units");
                    int calories = rs.getInt("Calories");
                    String allergens = rs.getString("Allergens");
                    float proteins = rs.getFloat("Proteins");
                    float carbs = rs.getFloat("Carbs");
                    float fats = rs.getFloat("Fats");

                    Macros macros = new Macros(proteins, carbs, fats);

                    Ingredient ingredient = new Ingredient(ingredientID, name, description, quantity, units, calories, macros, Collections.singletonList(allergens));

                    // Adicionar ingrediente à receita
                    ingredients.add(ingredient);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while fetching ingredients: " + e.getMessage());
        }
        return ingredients;
    }

    private List<Reminder> loadRemindersForRecipe(Integer recipeID) {
        String sql = "SELECT * FROM REMINDERS WHERE RecipeID = ?";

        List<Reminder> reminders = new ArrayList<>();

        try (PreparedStatement pstmt = this.dbConn.prepareStatement(sql)) {
            pstmt.setInt(1, recipeID);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Integer reminderID = rs.getInt("ReminderID");
                    boolean check = rs.getInt("Check") == 1;
                    String text = rs.getString("Text");

                    Reminder reminder = new Reminder(reminderID, check, text);

                    // Adicionar lembrete à receita
                    reminders.add(reminder);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while fetching reminders: " + e.getMessage());
        }
        return reminders;
    }

}