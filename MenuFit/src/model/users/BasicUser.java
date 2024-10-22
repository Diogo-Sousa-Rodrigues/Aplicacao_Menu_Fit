package model.users;

import model.meals.MealPlan;

import java.util.Date;
import java.util.Optional;

public class BasicUser extends GenericUser {
    public BasicUser(String firstName, String lastName, String email, Date birthdate, Gender gender) {
        super(firstName, lastName, email, birthdate, gender);
    }
}
