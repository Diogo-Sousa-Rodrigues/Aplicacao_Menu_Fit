package pt.isec.persistence;

import pt.isec.model.meals.Meal;
import pt.isec.model.meals.MealPlan;
import pt.isec.model.meals.Recipe;
import pt.isec.model.users.HealthData;
import pt.isec.model.users.TimeBudget;
import pt.isec.model.users.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class EphemeralStore {
    private static EphemeralStore instance = null;

    /**
     * Gets the EphemeralStore instance.
     *
     * @return The EphemeralStore instance.
     */
    public static EphemeralStore getInstance() {
        if (instance == null) {
            instance = new EphemeralStore();
        }
        return instance;
    }

    /**
     * Gets the User instance registered with the given credentials.
     *
     * @param email The User's email.
     * @param password The User's password.
     *
     * @return An Optional containing the User instance if
     * a User is registered under the given credentials, or
     * empty otherwise.
     */
    public Optional<User> getUser(String email, String password) {
        if (this.credentials.containsKey(email)) {
            String key = this.credentials.get(email);

            if (key.equals(password)) {
                if (users.containsKey(email)) {
                    User user = this.users.get(email);

                    return Optional.of(user);
                }
            }
        }

        return Optional.empty();
    }

    /**
     * Puts a new User instance in the store under the given credentials.
     *
     * @param user The User's instance.
     * @param password The User's password.
     *
     * @return An Optional containing the User instance if
     * no User is registered under the given credentials, or
     * empty otherwise.
     */
    public Optional<User> putUser(User user, String password) {
        if (isRegistered(user)) {
            return Optional.empty();
        }

        String email = user.getEmail();

        // TODO: email and password validation

        if (!this.credentials.containsKey(email)) {
            if (!this.users.containsKey(email)) {
                this.credentials.put(email, password);
                this.users.put(email, user);

                return Optional.of(user);
            }
        }

        return Optional.empty();
    }

    /**
     * Gets the MealPlan assigned to the given User.
     *
     * @param user The User instance.
     *
     * @return An Optional containing the MealPlan assigned
     * to the given User, or empty otherwise.
     */
    public Optional<MealPlan> getMealPlan(User user) {
        if (!isRegistered(user)) {
            return Optional.empty();
        }

        if (this.mealPlans.containsKey(user)) {
            MealPlan mealPlan = this.mealPlans.get(user);

            return Optional.of(mealPlan);
        }

        return Optional.empty();
    }

    /**
     * Puts a MealPlan associated to a given User.
     * Overrides an existing MealPlan associated to a User.
     *
     * @param user The User's instance.
     * @param mealPlan The MealPlan to assign to the given User.
     *
     * @return An Optional containing the MealPlan instance
     * if the User is registered within the Store, or
     * empty otherwise.
     */
    public Optional<MealPlan> putMealPlan(User user, MealPlan mealPlan) {
        if (!isRegistered(user)) {
            return Optional.empty();
        }

        this.mealPlans.put(user, mealPlan);

        return Optional.of(mealPlan);
    }

    /**
     * Gets the HealthData assigned to the given User.
     *
     * @param user The User instance.
     *
     * @return An Optional containing the HealthData assigned
     * to the given User, or empty otherwise.
     */
    public Optional<HealthData> getHealthData(User user) {
        if (!isRegistered(user)) {
            return Optional.empty();
        }

        if (this.healthData.containsKey(user)) {
            HealthData healthData = this.healthData.get(user);

            return Optional.of(healthData);
        }

        return Optional.empty();
    }

    /**
     * Puts the given HealthData in the Store and associates them
     * to a given User.
     * Overrides an existing HealData for that User.
     *
     * @param user A registered User instance.
     * @param healthData The HealthData to assign to the given User.
     *
     * @return An Optional containing the HealthData instance
     * if the User is registered within the Store, or
     * empty otherwise.
     */
    public Optional<HealthData> putHealthData(User user, HealthData healthData) {
        if (!isRegistered(user)) {
            return Optional.empty();
        }

        this.healthData.put(user, healthData);

        return Optional.of(healthData);
    }

    /**
     * Gets the TimeBudget assigned to the given User.
     *
     * @param user The User instance.
     *
     * @return An Optional containing the TimeBudget assigned
     * to the given User, or empty otherwise.
     */
    public Optional<TimeBudget> getTimeBudget(User user) {
        if (!isRegistered(user)) {
            return Optional.empty();
        }

        if (this.timeBudget.containsKey(user)) {
            TimeBudget timeBudget = this.timeBudget.get(user);

            return Optional.of(timeBudget);
        }

        return Optional.empty();
    }

    /**
     * Puts the given TimeBudget in the Store and associates them
     * to a given User.
     * Overrides an existing TimeBudget for that User.
     *
     * @param user A registered User instance.
     * @param timeBudget The TimeBudget to assign to the given User.
     *
     * @return An Optional containing the TimeBudget instance
     * if the User is registered within the Store, or
     * empty otherwise.
     */
    public Optional<TimeBudget> putTimeBudget(User user, TimeBudget timeBudget) {
        if (!isRegistered(user)) {
            return Optional.empty();
        }

        this.timeBudget.put(user, timeBudget);

        return Optional.of(timeBudget);
    }

    private boolean isRegistered(User user) {
        for (var u : this.users.values()) {
            if (u == user) {
                return true;
            }
        }

        return false;
    }

    private boolean isRegistered(MealPlan mealPlan) {
        for (var m : this.mealPlans.values()) {
            if (m == mealPlan) {
                return true;
            }
        }

        return false;
    }

    private EphemeralStore() {
        this.users = new HashMap<>();

        this.credentials = new HashMap<>();

        this.mealPlans = new HashMap<>();

        //TODO: remove
        this.meals = new HashMap<>();

        this.healthData = new HashMap<>();

        this.timeBudget = new HashMap<>();
    }

    // Maps User e-mails to User instances
    private final Map<String, User> users;

    // Maps User passwords to User e-mails
    private final Map<String, String> credentials;

    // Maps User instances to MealPlans
    private final Map<User, MealPlan> mealPlans;

    // TODO: remove
    private final Map<MealPlan, List<Meal>> meals;

    // Maps User instances to HealthData information
    private final Map<User, HealthData> healthData;

    // Maps User instances to TimeBudget information
    private final Map<User, TimeBudget> timeBudget;
}
