import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.junit.jupiter.api.Test;
import pt.isec.model.meals.Ingredient;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SerializeTest {
    @Test
    void testIngredient() {
        String jsonIngredient = "{\"name\":\"sample ingredient\",\"description\":\"sample description\",\"quantity\":12.0,\"units\":\"millimeters\",\"calories\":44,\"macros\":{\"proteins\":1.0,\"carbs\":2.0,\"fats\":3.0},\"allergens\":[\"allergen 1\",\"allergen 2\"]}";
        try {
            Gson gson = new Gson();
            Ingredient ingredient = gson.fromJson(jsonIngredient, Ingredient.class);

            assertEquals(jsonIngredient, gson.toJson(ingredient), "Expected JSON object to represent Ingredient.");
        } catch (JsonSyntaxException syntaxException) {
            System.out.println("Invalid JSON syntax.");
        }
    }
}
