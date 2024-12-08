package pt.isec;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import pt.isec.ai.CommonLLM;
import pt.isec.ai.GroqLLM;
import pt.isec.builders.InstanceBuilder;
import pt.isec.builders.MealPlanBuilder;
import pt.isec.builders.PromptBuilder;
import pt.isec.model.meals.Ingredient;
import pt.isec.model.meals.Meal;
import pt.isec.model.meals.MealPlan;
import pt.isec.model.meals.Recipe;
import pt.isec.adapters.DurationAdapter;
import pt.isec.adapters.LocalDateTimeAdapter;
import pt.isec.model.users.*;

import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class Sample {
    public static void main(String[] args) {
        PromptBuilder promptBuilder = PromptBuilder.getInstance();
        InstanceBuilder instanceBuilder = new InstanceBuilder();

        CommonLLM llm = GroqLLM.getInstance();
        llm.setApiKey("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");

        {

            LocalDate birthdate = LocalDate.now();

            User user = new BasicUser(1, "Júlio", "Pacheco",
                    "julio@email.com", birthdate, Gender.Male);

            HealthData healthData = new HealthData("68", "1.85", "Lose weight",
                    "Active", "75", "0", "None",
                    "None", "None", "None", "Vitamin C",
                    "None", "None");

            user.setHealthData(healthData);

            TimeBudget timeBudget = new TimeBudget(Duration.ofMinutes(30), 5.0f, "€");
            user.setTimeBudget(timeBudget);

            LocalDate date = LocalDateTime.now().toLocalDate();
            LocalDate begin = date.plusDays(0);
            LocalDate end = date.plusDays(7);

            MealPlanBuilder mealPlanBuilder = MealPlanBuilder.getInstance();
            Optional<MealPlan> mealPlanOpt = mealPlanBuilder.getMealPlan(user, begin, end, llm);

            if (mealPlanOpt.isPresent()) {
                System.out.println("Meal Plan generated successfully.");
            } else {
                System.out.println("Unable to generate Meal Plan.");
            }
        }
        //{ // Ingredient (from and to JSON)
        //    String jsonIngredient = "{\"name\":\"sample ingredient\",\"description\":\"sample description\",\"quantity\":12,\"units\":\"millimeters\",\"calories\":44,\"macros\":{\"proteins\":1.0,\"carbs\":2.0,\"fats\":3.0},\"allergens\":[\"allergen 1\",\"allergen 2\"]}";
        //    try {
        //        Gson gson = new Gson();
        //        Ingredient ingredient = gson.fromJson(jsonIngredient, Ingredient.class);

        //        if (jsonIngredient.equals(gson.toJson(ingredient))) {
        //            System.out.println("Ingredient - Passed.");
        //        }
        //    } catch (JsonSyntaxException syntaxException) {
        //        System.out.println("Invalid JSON syntax.");
        //    }
        //}
        //{ // Recipe (from and to JSON)
        //    String jsonRecipe = "{\"name\":\"Grilled Lemon Herb Chicken\",\"description\":\"A flavorful and juicy grilled chicken marinated in lemon and herbs.\",\"servings\":1,\"prep\":105,\"reminders\":[{\"data\":\"Preheat the grill to medium-high heat.\"},{\"data\":\"Marinate chicken for at least 30 minutes.\"}],\"ingredients\":[{\"name\":\"Chicken Breast\",\"description\":\"Lean protein source, skinless and boneless.\",\"quantity\":400,\"units\":\"grams\",\"calories\":660,\"macros\":{\"proteins\":124.0,\"carbs\":0.0,\"fats\":14.0},\"allergens\":[]},{\"name\":\"Olive Oil\",\"description\":\"Healthy fat used for marinating and cooking.\",\"quantity\":30,\"units\":\"milliliters\",\"calories\":240,\"macros\":{\"proteins\":0.0,\"carbs\":0.0,\"fats\":27.0},\"allergens\":[]},{\"name\":\"Lemon Juice\",\"description\":\"Freshly squeezed juice for flavor.\",\"quantity\":50,\"units\":\"milliliters\",\"calories\":15,\"macros\":{\"proteins\":0.0,\"carbs\":5.0,\"fats\":0.0},\"allergens\":[]},{\"name\":\"Garlic\",\"description\":\"Adds a strong flavor to the marinade.\",\"quantity\":10,\"units\":\"grams\",\"calories\":15,\"macros\":{\"proteins\":0.6,\"carbs\":3.5,\"fats\":0.1},\"allergens\":[]},{\"name\":\"Dried Oregano\",\"description\":\"Herb used for seasoning.\",\"quantity\":5,\"units\":\"grams\",\"calories\":15,\"macros\":{\"proteins\":0.8,\"carbs\":3.2,\"fats\":0.3},\"allergens\":[]}]}";

        //    try {
        //        Gson gson = new GsonBuilder()
        //                .registerTypeAdapter(Duration.class, new DurationAdapter())
        //                .create();
        //        Recipe recipe = gson.fromJson(jsonRecipe, Recipe.class);

        //        if (jsonRecipe.equals(gson.toJson(recipe))) {
        //            System.out.println("Recipe - Passed.");
        //        }
        //    } catch (JsonSyntaxException syntaxException) {
        //        System.out.println("Invalid JSON syntax.");
        //    }
        //}
        //{ // Meal (from and to JSON)
        //    String jsonMeal = "{\"type\":\"Breakfast\",\"date\":\"2024-11-07T12:30\",\"recipe\":{\"name\":\"Grilled Lemon Herb Chicken\",\"description\":\"A flavorful and juicy grilled chicken marinated in lemon and herbs.\",\"servings\":1,\"prep\":105,\"reminders\":[{\"data\":\"Preheat the grill to medium-high heat.\"},{\"data\":\"Marinate chicken for at least 30 minutes.\"}],\"ingredients\":[{\"name\":\"Chicken Breast\",\"description\":\"Lean protein source, skinless and boneless.\",\"quantity\":400,\"units\":\"grams\",\"calories\":660,\"macros\":{\"proteins\":124.0,\"carbs\":0.0,\"fats\":14.0},\"allergens\":[]},{\"name\":\"Olive Oil\",\"description\":\"Healthy fat used for marinating and cooking.\",\"quantity\":30,\"units\":\"milliliters\",\"calories\":240,\"macros\":{\"proteins\":0.0,\"carbs\":0.0,\"fats\":27.0},\"allergens\":[]},{\"name\":\"Lemon Juice\",\"description\":\"Freshly squeezed juice for flavor.\",\"quantity\":50,\"units\":\"milliliters\",\"calories\":15,\"macros\":{\"proteins\":0.0,\"carbs\":5.0,\"fats\":0.0},\"allergens\":[]},{\"name\":\"Garlic\",\"description\":\"Adds a strong flavor to the marinade.\",\"quantity\":10,\"units\":\"grams\",\"calories\":15,\"macros\":{\"proteins\":0.6,\"carbs\":3.5,\"fats\":0.1},\"allergens\":[]},{\"name\":\"Dried Oregano\",\"description\":\"Herb used for seasoning.\",\"quantity\":5,\"units\":\"grams\",\"calories\":15,\"macros\":{\"proteins\":0.8,\"carbs\":3.2,\"fats\":0.3},\"allergens\":[]}]}}";

        //    try {
        //        Gson gson = new GsonBuilder()
        //                .registerTypeAdapter(Duration.class, new DurationAdapter())
        //                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
        //                .create();

        //        Meal meal = gson.fromJson(jsonMeal, Meal.class);

        //        if (jsonMeal.equals(gson.toJson(meal))) {
        //            System.out.println("Meal - Passed.");
        //        }
        //    } catch (JsonSyntaxException syntaxException) {
        //        System.out.println("Invalid JSON syntax.");
        //    }
        //}
    }
}
