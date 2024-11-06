package pt.isec;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import pt.isec.model.meals.Ingredient;
import pt.isec.model.meals.Meal;
import pt.isec.model.meals.MealPlan;
import pt.isec.model.meals.Recipe;
import pt.isec.prompt.DurationAdapter;

import java.time.Duration;

public class Sample {
    public static void main(String[] args) {
        { // Ingredient (from and to JSON)
            String jsonIngredient = "{\"name\":\"sample ingredient\",\"description\":\"sample description\",\"quantity\":12,\"units\":\"millimeters\",\"calories\":44,\"macros\":{\"proteins\":1.0,\"carbs\":2.0,\"fats\":3.0},\"allergens\":[\"allergen 1\",\"allergen 2\"]}";
            try {
                Gson gson = new Gson();
                Ingredient ingredient = gson.fromJson(jsonIngredient, Ingredient.class);

                if (jsonIngredient.equals(gson.toJson(ingredient))) {
                    System.out.println("Ingredient - Passed.");
                }
            } catch (JsonSyntaxException syntaxException) {
                System.out.println("Invalid JSON syntax.");
            }
        }
        { // Recipe (from and to JSON)
            String jsonRecipe = "{\"name\":\"Grilled Lemon Herb Chicken\",\"description\":\"A flavorful and juicy grilled chicken marinated in lemon and herbs.\",\"servings\":1,\"prep\":105,\"reminders\":[\"Preheat the grill to medium-high heat.\",\"Marinate chicken for at least 30 minutes.\"],\"ingredients\":[{\"name\":\"Chicken Breast\",\"description\":\"Lean protein source, skinless and boneless.\",\"quantity\":400,\"units\":\"grams\",\"calories\":660,\"macros\":{\"proteins\":124.0,\"carbs\":0.0,\"fats\":14.0},\"allergens\":[]},{\"name\":\"Olive Oil\",\"description\":\"Healthy fat used for marinating and cooking.\",\"quantity\":30,\"units\":\"milliliters\",\"calories\":240,\"macros\":{\"proteins\":0.0,\"carbs\":0.0,\"fats\":27.0},\"allergens\":[]},{\"name\":\"Lemon Juice\",\"description\":\"Freshly squeezed juice for flavor.\",\"quantity\":50,\"units\":\"milliliters\",\"calories\":15,\"macros\":{\"proteins\":0.0,\"carbs\":5.0,\"fats\":0.0},\"allergens\":[]},{\"name\":\"Garlic\",\"description\":\"Adds a strong flavor to the marinade.\",\"quantity\":10,\"units\":\"grams\",\"calories\":15,\"macros\":{\"proteins\":0.6,\"carbs\":3.5,\"fats\":0.1},\"allergens\":[]},{\"name\":\"Dried Oregano\",\"description\":\"Herb used for seasoning.\",\"quantity\":5,\"units\":\"grams\",\"calories\":15,\"macros\":{\"proteins\":0.8,\"carbs\":3.2,\"fats\":0.3},\"allergens\":[]}]}";

            try {
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(Duration.class, new DurationAdapter())
                        .create();
                Recipe recipe = gson.fromJson(jsonRecipe, Recipe.class);

                if (jsonRecipe.equals(gson.toJson(recipe))) {
                    System.out.println("Recipe - Passed.");
                }
            } catch (JsonSyntaxException syntaxException) {
                System.out.println("Invalid JSON syntax.");
            }
        }
        { // Meal (from and to JSON)
            String jsonMeal = "{\"type\":\"Breakfast\",\"recipe\":{\"name\":\"Grilled Lemon Herb Chicken\",\"description\":\"A flavorful and juicy grilled chicken marinated in lemon and herbs.\",\"servings\":1,\"prep\":105,\"reminders\":[\"Preheat the grill to medium-high heat.\",\"Marinate chicken for at least 30 minutes.\"],\"ingredients\":[{\"name\":\"Chicken Breast\",\"description\":\"Lean protein source, skinless and boneless.\",\"quantity\":400,\"units\":\"grams\",\"calories\":660,\"macros\":{\"proteins\":124.0,\"carbs\":0.0,\"fats\":14.0},\"allergens\":[]},{\"name\":\"Olive Oil\",\"description\":\"Healthy fat used for marinating and cooking.\",\"quantity\":30,\"units\":\"milliliters\",\"calories\":240,\"macros\":{\"proteins\":0.0,\"carbs\":0.0,\"fats\":27.0},\"allergens\":[]},{\"name\":\"Lemon Juice\",\"description\":\"Freshly squeezed juice for flavor.\",\"quantity\":50,\"units\":\"milliliters\",\"calories\":15,\"macros\":{\"proteins\":0.0,\"carbs\":5.0,\"fats\":0.0},\"allergens\":[]},{\"name\":\"Garlic\",\"description\":\"Adds a strong flavor to the marinade.\",\"quantity\":10,\"units\":\"grams\",\"calories\":15,\"macros\":{\"proteins\":0.6,\"carbs\":3.5,\"fats\":0.1},\"allergens\":[]},{\"name\":\"Dried Oregano\",\"description\":\"Herb used for seasoning.\",\"quantity\":5,\"units\":\"grams\",\"calories\":15,\"macros\":{\"proteins\":0.8,\"carbs\":3.2,\"fats\":0.3},\"allergens\":[]}]}}";

            try {
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(Duration.class, new DurationAdapter())
                        .create();

                Meal meal = gson.fromJson(jsonMeal, Meal.class);

                if (jsonMeal.equals(gson.toJson(meal))) {
                    System.out.println("Meal - Passed.");
                }
            } catch (JsonSyntaxException syntaxException) {
                System.out.println("Invalid JSON syntax.");
            }
        }
        { // MealPlan (from and to JSON)
            String jsonMealPlan = "{\"type\":\"Breakfast\",\"recipe\":{\"name\":\"Grilled Lemon Herb Chicken\",\"description\":\"A flavorful and juicy grilled chicken marinated in lemon and herbs.\",\"servings\":1,\"prep\":105,\"reminders\":[\"Preheat the grill to medium-high heat.\",\"Marinate chicken for at least 30 minutes.\"],\"ingredients\":[{\"name\":\"Chicken Breast\",\"description\":\"Lean protein source, skinless and boneless.\",\"quantity\":400,\"units\":\"grams\",\"calories\":660,\"macros\":{\"proteins\":124.0,\"carbs\":0.0,\"fats\":14.0},\"allergens\":[]},{\"name\":\"Olive Oil\",\"description\":\"Healthy fat used for marinating and cooking.\",\"quantity\":30,\"units\":\"milliliters\",\"calories\":240,\"macros\":{\"proteins\":0.0,\"carbs\":0.0,\"fats\":27.0},\"allergens\":[]},{\"name\":\"Lemon Juice\",\"description\":\"Freshly squeezed juice for flavor.\",\"quantity\":50,\"units\":\"milliliters\",\"calories\":15,\"macros\":{\"proteins\":0.0,\"carbs\":5.0,\"fats\":0.0},\"allergens\":[]},{\"name\":\"Garlic\",\"description\":\"Adds a strong flavor to the marinade.\",\"quantity\":10,\"units\":\"grams\",\"calories\":15,\"macros\":{\"proteins\":0.6,\"carbs\":3.5,\"fats\":0.1},\"allergens\":[]},{\"name\":\"Dried Oregano\",\"description\":\"Herb used for seasoning.\",\"quantity\":5,\"units\":\"grams\",\"calories\":15,\"macros\":{\"proteins\":0.8,\"carbs\":3.2,\"fats\":0.3},\"allergens\":[]}]}}";

            // TODO: this code is not yet ready to work
            // TODO: think about JSON representation of MealPlan
            // and relation with Store
            try {
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(Duration.class, new DurationAdapter())
                        .create();

                MealPlan mealPlan = gson.fromJson(jsonMealPlan, MealPlan.class);

                if (jsonMealPlan.equals(gson.toJson(mealPlan))) {
                    System.out.println("MealPlan - Passed.");
                }
            } catch (JsonSyntaxException syntaxException) {
                System.out.println("Invalid JSON syntax.");
            }
        }
    }
}
