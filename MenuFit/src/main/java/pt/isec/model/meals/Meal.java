package pt.isec.model.meals;

import java.time.LocalDateTime;

public class Meal {
    public Meal(Recipe recipe) {
        // TODO: To be removed
        this.recipe = recipe;
    }

    public Meal(MealType type, LocalDateTime date, Recipe recipe) {
        this.type = type;
        this.date = date;
        this.recipe = recipe;
    }

    /**
     * Gets the Meal's recipe.
     *
     * @return The Meal's Recipe.
     */
    public Recipe getRecipe() {
        return this.recipe;
    }

    /**
     * Set's a new Recipe for the Meal.
     *
     * @param recipe The new Recipe.
     */
    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    /**
     * Gets the Meal's type.
     *
     * @return The Meal's MealType.
     */
    public MealType getType() {
        return this.type;
    }

    /**
     * Sets the Meal's type
     *
     * @param type The Meal's type
     */
    public void setType(MealType type) {
        this.type = type;
    }

    /**
     * Gets the Meal's date.
     *
     * @return The Meal's date.
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Gets the Meal's index
     *
     * @return the Meal's index
     */
    // TODO: To be removed
    public int getMealIndex() {
        return mealIndex;
    }

    /**
     * Sets the Meal's index
     *
     * @param mealIndex the meal's index
     */
    // TODO: To be removed
    public void setMealIndex(int mealIndex) {
        this.mealIndex = mealIndex;
    }

    @Override
    public String toString() {
        return String.join("\n",
                "Type: " + type,
                "Date: " + date,
                "Recipe: " + recipe.toString());
    }

    private MealType type;

    private LocalDateTime date;

    private Recipe recipe;

    // TODO: To be removed
    private int mealIndex;
}
