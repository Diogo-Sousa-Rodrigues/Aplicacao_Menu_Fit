package pt.isec.model.meals;

/**
 * Represents macro values
 */
public record Macros(float proteins, float carbs, float fats) {
    @Override
    public String toString() {
        return String.join("\n",
                "Proteins: " + proteins,
                "Carbs: " + carbs,
                "Fats: " + fats);
    }
}
