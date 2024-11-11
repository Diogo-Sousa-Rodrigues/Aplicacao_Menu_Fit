package pt.isec.model.users;

import java.time.Duration;

public class TimeBudget {
    public TimeBudget(Duration availableTime,
                      float budgetValue, String budgetUnit) {
        this.availableTime = availableTime;
        this.budgetValue = budgetValue;
        this.budgetUnit = budgetUnit;
    }

    /**
     * Gets the User's available time for meal prep.
     *
     * @return The User's available time for meal prep, as a Duration.
     */
    public Duration getAvailableTime() {
        return this.availableTime;
    }

    /**
     * Sets the User's available time for meal prep.
     *
     * @param availableTime The User's available time, as a Duration.
     */
    public void setAvailableTime(Duration availableTime) {
        this.availableTime = availableTime;
    }

    /**
     * Gets the User's budget cap per meal.
     *
     * @return The User's budget cap, as a String.
     * The String is a concatenation of the budget value and
     * the unit, for example: 10$
     */
    public String getBudget() {
        return this.budgetValue + this.budgetUnit;
    }

    /**
     * Sets the User's budget cap per meal.
     *
     * @param budgetValue The value for the budget.
     * @param budgetUnit The unit for the budget.
     */
    public void setBudget(float budgetValue, String budgetUnit) {
        this.budgetValue = budgetValue;
        this.budgetUnit = budgetUnit;
    }

    @Override
    public String toString() {
        return "Available Time: " + this.availableTime +
                ", Budget: " + getBudget();
    }

    private Duration availableTime;

    private float budgetValue;

    private String budgetUnit;
}
