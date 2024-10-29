package pt.isec.model.meals;

import java.util.List;

// {
//      "name": "Recipe name",
//      "description": "Recipe description",
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
public record Recipe(String name, String description,
                     List<String> reminders, List<Ingredient> ingredients) {
}
