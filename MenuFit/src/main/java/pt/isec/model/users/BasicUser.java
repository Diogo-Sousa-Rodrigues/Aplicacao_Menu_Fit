package pt.isec.model.users;

import pt.isec.model.meals.MealPlan;

import java.util.Date;

public class BasicUser extends GenericUser {

    public BasicUser(Integer userID, String firstName, String lastName, String email, Date birthdate, Gender gender) {
        super(userID, firstName, lastName, email, birthdate, gender);
    }

    public MealPlan getMealPlan() {
        return mealPlan;
    }

    public void setMealPlan(MealPlan mealPlan) {
        this.mealPlan = mealPlan;
    }

    private MealPlan mealPlan;
}
