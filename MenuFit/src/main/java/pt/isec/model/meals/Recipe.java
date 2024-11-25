package pt.isec.model.meals;

import java.time.Duration;
import java.util.List;

// {
//      "name": "Recipe name",
//      "description": "Recipe description",
//      "servings": 1,
//      "prep": 60, // minutes
//      "reminders": ["preheat the oven at 250º"],
//      "ingredients" : [
//          {
//              "name" : "sample ingredient",
//              "description" : "sample description",
//              "quantity" : 12,
//              "units" : "millimeters",
//              "calories" : 44,
//              "macros" : {
//                  "proteins": 1,
//                  "carbs": 2,
//                  "fats": 3
//              },
//              "allergens" : ["allergen 1", "allergen 2"]
//          }
//      ]
// }
public class Recipe {
    private final String name;
    private final String description;
    private final int servings;
    private final Duration prep;
    private final List<Reminder> reminders;
    private final List<Ingredient> ingredients;
    private Integer recipeID; // Inicialmente null
    private int calories;

    public Recipe(String name, String description, int servings, Duration prep,
                  List<Reminder> reminders, List<Ingredient> ingredients) {
        this.name = name;
        this.description = description;
        this.servings = servings;
        this.prep = prep;
        this.reminders = reminders;
        this.ingredients = ingredients;
    }

    // Getters
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getServings() { return servings; }
    public Duration getPrep() { return prep; }
    public List<Reminder> getReminders() { return reminders; }
    public List<Ingredient> getIngredients() { return ingredients; }
    public Integer getRecipeID() { return recipeID; }
    public int getCalories() {
        int sum = 0;

        for (var ingredient : ingredients) {
            sum += ingredient.calories();
        }

        return sum;
    }


    // Setters para campos opcionais
    public void setRecipeID(Integer recipeID) { this.recipeID = recipeID; }
    public void setCalories(int calories) { this.calories = calories; }

    @Override
    public String toString() {
        return String.join("\n",
                "Name: " + name,
                "Description: " + description,
                "Servings: " + servings,
                "Calories: " + calories,
                "Prep: " + prep,
                "Reminders: " + reminders,
                "Ingredients: " + ingredients,
                "Recipe ID: " + recipeID
        );
    }

    public int getCalories() {
        int sum = 0;

        for (var ingredient : ingredients) {
            sum += ingredient.calories();
        }

        return sum;
    }
}
