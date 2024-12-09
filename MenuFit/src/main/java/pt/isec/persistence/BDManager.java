package pt.isec.persistence;

import pt.isec.model.meals.*;
import pt.isec.model.users.BasicUser;
import pt.isec.model.users.Gender;
import pt.isec.model.users.HealthData;

import java.io.Serializable;
import java.sql.*;
import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

public class BDManager implements Serializable {
    private String dbURL;

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

    public boolean registerUser(String fullName, String email, String password, String gender, String birthDateString) {
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
                return true;
            } else {
                System.out.println("Failed to register user.");
            }
        } catch (SQLException e) {
            System.out.println("Error while registering user: " + e.getMessage());
        }
        return false;
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
                LocalDate birthdate = LocalDate.parse(rs.getString("Date_of_Birth")); // Converte para java.sql.Date
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
           String sql = "INSERT INTO MEAL (MealPlanID, Type, Date, 'Check') VALUES (?, ?, ?, ?)";

            try (PreparedStatement pstmt = this.dbConn.prepareStatement(sql)) {
                // Configurar os parâmetros do PreparedStatement
                pstmt.setInt(1, mealPlanID);
                pstmt.setString(2, meal.getType().toString());
                pstmt.setString(3, meal.getDate().toString());
                pstmt.setInt(4, 0);

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
            pstmt.setString(2, recipe.getName());
            pstmt.setString(3, recipe.getDescription());
            pstmt.setInt(4, recipe.getServings());
            pstmt.setInt(5, recipe.getCalories());
            pstmt.setString(6, recipe.getPrep().toString());

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
        for(Reminder reminder : recipe.getReminders()){
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
        for(Ingredient ingredient : recipe.getIngredients()){
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


    public boolean saveDietaryRestrictions(HealthData healthData, Integer userID) {
        boolean success = true;

        String sql = "INSERT INTO DietaryRestrictions (UserID, Weight, Height, Objetivo, Level_Fitness, DesiredWeight, DailyCalorieCount, Allergies, Specific_Diet, Chronic_Isseus, Gastrointestinal_Issues, Vitamin_Deficiencies, Food_Preference, Medication) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?)";

        try (PreparedStatement pstmt = this.dbConn.prepareStatement(sql)) {

            pstmt.setInt(1, userID);
            pstmt.setString(2, healthData.getWeight());
            pstmt.setString(3, healthData.getHeight());
            pstmt.setString(4, healthData.getObjective());
            pstmt.setString(5, healthData.getLevelOfFitness());
            pstmt.setString(6, healthData.getDesiredWeight());
            pstmt.setString(7, healthData.getDailyCalorieCount());
            pstmt.setString(8, healthData.getAllergiesOrIntolerances());
            pstmt.setString(9, healthData.getMedicalReasons());
            pstmt.setString(10, healthData.getChronicHealth());
            pstmt.setString(11, healthData.getGastrointestinalIssues());
            pstmt.setString(12, healthData.getVitaminDeficiencies());
            pstmt.setString(13, healthData.getDietType());
            pstmt.setString(14, healthData.getMedications());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected <= 0) {
                System.out.println("Failed to register dietary restrictions.");
                success = false;
            }
        } catch (SQLException e) {
            System.out.println("Error while registering dietary restrictions: " + e.getMessage());
            success = false;
        }

        return success;
    }

    public HealthData loadHealthAndDietaryRestrictions(Integer userID) {
        String sql = "SELECT * FROM DietaryRestrictions WHERE UserID = ?";

        try (PreparedStatement pstmt = this.dbConn.prepareStatement(sql)) {
            pstmt.setInt(1, userID);

            System.out.println("Executing SQL: " + sql);
            System.out.println("UserID: " + userID);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {

                    System.out.println("Data found for UserID: " + userID);
                    System.out.println("Weight: " + rs.getString("Weight"));
                    System.out.println("Height: " + rs.getString("Height"));


                    String weight = rs.getString("Weight");
                    String height = rs.getString("Height");
                    String objective = rs.getString("Objetivo");
                    String levelOfFitness = rs.getString("Level_Fitness");
                    String desiredWeight = rs.getString("DesiredWeight");
                    String dailyCalorieCount = rs.getString("DailyCalorieCount");
                    String allergiesOrIntolerances = rs.getString("Allergies");
                    String medicalReasons = rs.getString("Specific_Diet");
                    String chronicHealth = rs.getString("Chronic_Isseus");
                    String gastrointestinalIssues = rs.getString("Gastrointestinal_Issues");
                    String vitaminDeficiencies = rs.getString("Vitamin_Deficiencies");
                    String dietType = rs.getString("Food_Preference");
                    String medications = rs.getString("Medication");


                    return new HealthData(
                            weight,
                            height,
                            objective,
                            levelOfFitness,
                            desiredWeight,
                            dailyCalorieCount,
                            allergiesOrIntolerances,
                            medicalReasons,
                            chronicHealth,
                            gastrointestinalIssues,
                            vitaminDeficiencies,
                            dietType,
                            medications
                    );
                } else {

                    System.out.println("No data found for UserID: " + userID);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error loading health and dietary restrictions: " + e.getMessage());
        }

        return null;
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
                    Boolean check = rs.getInt("Check") != 0;

                    Meal meal = new Meal(mealID, type, date, check);

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

                    Recipe recipe = new Recipe(name, description, servings, durationPrep, reminders, ingredients);
                    recipe.setRecipeID(recipeID);
                    recipe.setCalories(calories);
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

                    Ingredient ingredient = new Ingredient(name, description, quantity, units, calories, macros, Collections.singletonList(allergens));

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

    public boolean checkMeal(Meal meal) {
        Integer mealID = meal.getMealID();
        String query = "UPDATE MEAL SET 'Check' = ? WHERE MealID = ?";

        try (PreparedStatement pstmt = dbConn.prepareStatement(query)) {
            pstmt.setInt(1, 1);
            pstmt.setInt(2, mealID);

            int affectedRows = pstmt.executeUpdate();
            if(affectedRows > 0)
                return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return false;
    }

    public boolean checkReminder(Reminder reminder) {
        Integer remniderID = reminder.getReminderID();
        String query = "UPDATE REMINDERS SET 'Check' = ? WHERE ReminderID = ?";

        try (PreparedStatement pstmt = dbConn.prepareStatement(query)) {
            pstmt.setInt(1, 1);
            pstmt.setInt(2, remniderID);

            int affectedRows = pstmt.executeUpdate();
            if(affectedRows > 0)
                return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return false;
    }

    public boolean updateMealRecipe(Meal meal, Optional<Recipe> newRecipe) {
        Integer recipeID = meal.getRecipe().getRecipeID();
        String query = "UPDATE RECIPE SET (Name, Description, Servings, Calories, Prep_Time) = (?, ?, ?, ?, ?) WHERE RecipeID = ?";

        if(newRecipe.isPresent()) {
            try (PreparedStatement pstmt = dbConn.prepareStatement(query)) {
                pstmt.setString(1, newRecipe.get().getName());
                pstmt.setString(2, newRecipe.get().getDescription());
                pstmt.setInt(3, newRecipe.get().getServings());
                pstmt.setInt(4, newRecipe.get().getCalories());
                pstmt.setString(5, newRecipe.get().getPrep().toString());
                pstmt.setInt(6, recipeID);

                int affectedRows = pstmt.executeUpdate();
                if(affectedRows > 0){
                    return(deleteOldIngredients(recipeID) && deleteOldReminders(recipeID) && updateIngredients(newRecipe.get(), meal.getRecipe()) && updateReminders(newRecipe.get(), meal.getRecipe()));
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return false;
            }
        }
        return false;
    }

    private boolean deleteOldReminders(Integer recipeID) {
        String sql = "DELETE FROM REMINDERS WHERE RecipeID = ?";

        try (PreparedStatement pstmt = dbConn.prepareStatement(sql)) {
            // Configurar o parâmetro do IDExpense
            pstmt.setInt(1, recipeID);

            // Executar a query
            int rowsAffected = pstmt.executeUpdate();

            // Verificar se a operação foi bem-sucedida
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return false;
    }

    private boolean deleteOldIngredients(Integer recipeID) {
        String sql = "DELETE FROM INGREDIENTS WHERE RecipeID = ?";

        try (PreparedStatement pstmt = dbConn.prepareStatement(sql)) {
            // Configurar o parâmetro do IDExpense
            pstmt.setInt(1, recipeID);

            // Executar a query
            int rowsAffected = pstmt.executeUpdate();

            // Verificar se a operação foi bem-sucedida
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return false;
    }

    private boolean updateReminders(Recipe newRecipe, Recipe mealRecipe) {
        String sql = "INSERT INTO REMINDERS (RecipeID, 'Check', Text) VALUES (?, ?, ?)";

        //se for necessário chamar aqui uma função apra apagar os reminders da receita antiga
        boolean success = true;
        for(Reminder reminder : newRecipe.getReminders()){
            try (PreparedStatement pstmt = dbConn.prepareStatement(sql)) {
                pstmt.setInt(1, mealRecipe.getRecipeID());
                pstmt.setInt(2, 0);
                pstmt.setString(3, reminder.getData());

                int affectedRows = pstmt.executeUpdate();
                if(affectedRows <= 0){
                    success = false;
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                success = false;
            }
        }
        return success;
    }

    private boolean updateIngredients(Recipe newRecipe, Recipe mealRecipe) {
        String sql = "INSERT INTO INGREDIENTS (RecipeID, Name, Description, Quantity, Units, Calories, Allergens, Proteins, Carbs, Fats) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        //se for necessário chamar aqui uma função apra apagar os ingredients da receita antiga
        boolean success = true;
        for(Ingredient ingredient : newRecipe.getIngredients()){
            try (PreparedStatement pstmt = dbConn.prepareStatement(sql)) {
                pstmt.setInt(1, mealRecipe.getRecipeID());
                pstmt.setString(2, ingredient.name());
                pstmt.setString(3, ingredient.description());
                pstmt.setFloat(4, ingredient.quantity());
                pstmt.setString(5, ingredient.units());
                pstmt.setInt(6, ingredient.calories());
                pstmt.setString(7, String.valueOf(ingredient.allergens()));
                pstmt.setFloat(8, ingredient.macros().proteins());
                pstmt.setFloat(9, ingredient.macros().carbs());
                pstmt.setFloat(10, ingredient.macros().fats());

                int affectedRows = pstmt.executeUpdate();
                if(affectedRows <= 0){
                    success = false;
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                success = false;
            }
        }
        return success;
    }

    public String getUserPassword(Integer idUser) {
        String sql = "SELECT Password FROM USER WHERE UserID = ?";
        try (PreparedStatement pstmt = dbConn.prepareStatement(sql)) {
            pstmt.setInt(1, idUser);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString(1);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return null;
    }

    public boolean setNewPassword(Integer idUser, String text) {
        String sql = "UPDATE USER SET Password = ? WHERE UserID = ?";
        try (PreparedStatement pstmt = dbConn.prepareStatement(sql)){
            pstmt.setString(1, text);
            pstmt.setInt(2, idUser);
            int affectedRows = pstmt.executeUpdate();
            if(affectedRows > 0){
                return true;
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return false;
    }

    public boolean updateUserInfo(Integer idUser, String email, String birthdate, String gender, String name) {
        String sql =  "UPDATE USER SET Email = ?, Date_of_Birth = ?, Gender = ?, Name = ? WHERE UserID = ?";

        try(PreparedStatement pstmt = dbConn.prepareStatement(sql)){
            pstmt.setString(1, email);
            pstmt.setString(2, birthdate);
            pstmt.setString(3, gender);
            pstmt.setString(4, name);
            pstmt.setInt(5, idUser);
            int affectedRows = pstmt.executeUpdate();
            if(affectedRows > 0){
                return true;
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return false;
    }

    public boolean updateUserHeightAndWeight(Integer idUser, String height, String weight) {
        String sql = "UPDATE DietaryRestrictions SET Weight = ?, Height = ? WHERE UserID = ?";
        try(PreparedStatement pstmt = dbConn.prepareStatement(sql)){
            pstmt.setString(1, height);
            pstmt.setString(2, weight);
            pstmt.setInt(3, idUser);
            int affectedRows = pstmt.executeUpdate();
            if(affectedRows > 0){
                return true;
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return false;
    }

    public boolean updateObjective(Integer idUser, String objetivo, String desiredWeight, String cailyCalorieCount) {
        String sql = "UPDATE DietaryRestrictions SET Objetivo = ?, DesiredWeight = ?, DailyCalorieCount = ? WHERE UserID = ?";
        try(PreparedStatement pstmt = dbConn.prepareStatement(sql)){
            pstmt.setString(1, objetivo);
            pstmt.setString(2, desiredWeight);
            pstmt.setString(3, cailyCalorieCount);
            pstmt.setInt(4, idUser);
            int affectedRows = pstmt.executeUpdate();
            if(affectedRows > 0){
                return true;
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return false;
    }

    public boolean updateDietaryRestrictions(Integer idUser, String diet, String allergies, String vitamins, String foodPrefRestric, String chronicIssues, String gastroIssues, String medication) {
        String sql = "UPDATE DietaryRestrictions SET Allergies = ?, Specific_Diet = ?, Chronic_Isseus = ?, Gastrointestinal_Issues = ?, Vitamin_Deficiencies = ?, Food_Preference = ?, Medication = ? WHERE UserID = ?";
        try(PreparedStatement pstmt = dbConn.prepareStatement(sql)){
            pstmt.setString(1, allergies);
            pstmt.setString(2, diet);
            pstmt.setString(3, chronicIssues);
            pstmt.setString(4, gastroIssues);
            pstmt.setString(5, vitamins);
            pstmt.setString(6, foodPrefRestric);
            pstmt.setString(7, medication);
            pstmt.setInt(8, idUser);
            int affectedRows = pstmt.executeUpdate();
            if(affectedRows > 0){
                return true;
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return false;
    }

    public boolean addExtraMeal(Integer idUser, ExtraMeal extraMeal) {
        String sql = "INSERT INTO ExtraMeal (UserID, Name, Calories, Date) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = this.dbConn.prepareStatement(sql)) {
            // Configurar os parâmetros do PreparedStatement
            pstmt.setInt(1, idUser);
            pstmt.setString(2, extraMeal.getName());
            pstmt.setInt(3, extraMeal.getCalories());
            pstmt.setString(4, extraMeal.getDate().toString());

            // Executar a inserção
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            } else {
                System.out.println("Failed to register extra meal.");
            }
        } catch (SQLException e) {
            System.out.println("Error while registering extra meal: " + e.getMessage());
        }
        return false;

    }

//    public boolean insertUserHeightAndWeight(Integer idUser, String height, String weight) {
//        String sql = "INSERT INTO DietaryRestrictions (UserID, Weight, Height, Objetivo, Level_Fitness, DesiredWeight, DailyCalorieCount, Allergies, Specific_Diet, Chronic_Isseus, Gastrointestinal_Issues, Vitamin_Deficiencies, Food_Preference, Medication) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//        try (PreparedStatement pstmt = this.dbConn.prepareStatement(sql)) {
//
//            pstmt.setInt(1, idUser);
//            pstmt.setString(2, weight);
//            pstmt.setString(3, height);
//            pstmt.setString(4, null);
//            pstmt.setString(5, null);
//            pstmt.setString(6, null);
//            pstmt.setString(7, null);
//            pstmt.setString(8, null);
//            pstmt.setString(9, null);
//            pstmt.setString(10, null);
//            pstmt.setString(11, null);
//            pstmt.setString(12, null);
//            pstmt.setString(13, null);
//            pstmt.setString(14, null);
//
//            int rowsAffected = pstmt.executeUpdate();
//            if (rowsAffected > 0) {
//                return true;
//            }
//        } catch (SQLException e) {
//            System.out.println("Error while registering dietary restrictions: " + e.getMessage());
//            return false;
//        }
//
//        return false;
//
//    }
}