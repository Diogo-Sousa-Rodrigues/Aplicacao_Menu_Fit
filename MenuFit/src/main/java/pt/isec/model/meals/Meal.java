package pt.isec.model.meals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    public Meal(Integer mealID, String type, String date) {
        this.mealID = mealID;
        this.type = MealType.valueOf(type);
        this.date = LocalDateTime.parse(date);
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

    public void setMealID(Integer mealID) {
        this.mealID = mealID;
    }

    public Integer getMealID() {
        return mealID;
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

    private Integer mealID;

    // TODO: To be removed
    private int mealIndex;
}
