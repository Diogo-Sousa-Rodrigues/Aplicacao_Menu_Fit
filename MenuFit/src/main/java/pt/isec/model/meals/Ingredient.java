package pt.isec.model.meals;


import java.util.List;

// {
//      "name" : "sample ingredient",
//      "description" : "sample description",
//      "quantity" : 12,
//      "units" : "millimeters",
//      "calories" : 44,
//      "macros" : {
//          "proteins": 1,
//          "carbs": 2,
//          "fats": 3
//      },
//      "allergens" : ["allergen 1", "allergen 2"]
// }

/**
 * Represents an Ingredient
 */
public record Ingredient(String name, String description, float quantity,
                         String units, int calories, Macros macros, List<String> allergens) {

    @Override
    public String toString() {
        return String.join("\n",
                "Name: " + name,
                "Description: " + description,
                "Quantity: " + quantity,
                "Units: " + units,
                "Macros: " + macros,
                "Calories: " + calories,
                "Allergens: " + String.join(",", allergens));
    }
}
