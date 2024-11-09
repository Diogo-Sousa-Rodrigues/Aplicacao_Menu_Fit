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

    /**
     * Sets the Meal's type
     *
     * @param type The Meal's type
     */
    public void setType(MealType type) {
        this.type = type;
    }

    /**
     * Gets the Meal's index
     *
     * @return the Meal's index
     */
    public int getMealIndex() {
        return mealIndex;
    }

    /**
     * Sets the Meal's index
     *
     * @param mealIndex the meal's index
     */
    public void setMealIndex(int mealIndex) {
        this.mealIndex = mealIndex;
    }

    private MealType type;
    private Recipe recipe;
    private int mealIndex;
}
