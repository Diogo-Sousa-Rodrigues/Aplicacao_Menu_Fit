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
public record Recipe(Integer recipeID, String name, String description, int servings, int calories, Duration prep,
                     List<Reminder> reminders, List<Ingredient> ingredients) {


    @Override
    public String toString() {
        return String.join("\n",
                "Name: " + name,
                "Description: " + description,
                "Servings: " + servings,
                "Calories: " + calories,
                "Prep: " + prep,
                "Reminders: " + reminders,
                "Ingredients: " + ingredients
            );
    }
}
