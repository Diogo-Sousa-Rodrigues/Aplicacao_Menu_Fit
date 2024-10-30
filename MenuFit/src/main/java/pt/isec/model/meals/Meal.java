package pt.isec.model.meals;

import java.time.Duration;

public class Meal {
    public Meal(Recipe recipe) {
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

    private MealType type;
    private Duration prep;
    private Recipe recipe;
}
