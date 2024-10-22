package pt.isec.model.meals;

public class Meal {
    public Meal(Recipe recipe) {
        this.recipe = recipe;
    }

    public Recipe getRecipe() {
        return this.recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    // TODO: A Meal has a time to be eaten

    private Recipe recipe;
}
