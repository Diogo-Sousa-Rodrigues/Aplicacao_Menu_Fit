package pt.isec.model.users;

import pt.isec.model.meals.Meal;

import java.util.Date;
import java.util.List;

public interface User {
    Integer getIdUser();

    void setIdUser(Integer id);
    /**
     * Gets the User's first name.
     *
     * @return The User's first name as a String.
     */
    String getFirstName();

    /**
     * Sets the User's first name.
     *
     * @param firstName The User's new first name.
     */
    void setFirstName(String firstName);

    /**
     * Gets the User's first name.
     *
     * @return The User's first name as a String.
     */
    String getLastName();

    /**
     * Sets the User's last name.
     *
     * @param lastName The User's new last name.
     */
    void setLastName(String lastName);

    /**
     * Gets the User's full name.
     * A full name is the concatenation of the User's first and last name.
     *
     * @return The User's full name as a String.
     */
    String getFullName();

    /**
     * Sets the User's full name.
     *
     * @param firstName The User's new first name.
     * @param lastName The User's new last name.
     */
    void setFullName(String firstName, String lastName);

    /**
     * Gets the User's account email.
     *
     * @return The User's account email as a String.
     */
    String getEmail();

    /**
     * Sets the User's account email.
     *
     * @param email The User's new email.
     */
    void setEmail(String email);

    /**
     * Gets the User's birthdate.
     *
     * @return The User's birthdate as a Date.
     */
    Date getBirthdate();

    /**
     * Sets the User's birthdate.
     *
     * @param birthdate The User's new birthdate.
     */
    void setBirthdate(Date birthdate);

    /**
     * Gets the User's gender.
     *
     * @return The User's gender as a Gender.
     */
    Gender getGender();

    /**
     * Sets the User's gender.
     *
     * @param gender The User's new gender.
     */
    void setGender(Gender gender);

    /**
     * Sets the User's health data
     *
     * @param healthData The User's new health data
     */
    void setHealthData(HealthData healthData);

    /**
     * Gets the User's health data
     *
     * @return The User's health data as a HealthData
     */
    HealthData getHealthData();

    /**
     * Sets the name of the recipe the user wants to view
     *
     * @param currentRecipe The name of the recipe
     */
    void setCurrentRecipe(String currentRecipe);

    /**
     * Gets the name of the recipe the user wants to view
     *
     * @return The name of the recipe
     */
    String getCurrentRecipe();

    /**
     * Sets the index of the current meal of the user
     *
     * @param currentMeal index of the current meal
     */
    void setCurrentMealIndex(int currentMeal);

    /**
     * Gets the index of the current meal of the user
     * @return The index of the current meal
     */
    int getCurrentMealIndex();

    void setCurrentMeal(Meal meal);

    Meal getCurrentMeal();

    List<Meal> getExtraMeals();

    void addExtraMeal(Meal meal);

    int getAge();
}
