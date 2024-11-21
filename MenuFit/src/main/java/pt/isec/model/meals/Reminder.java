package pt.isec.model.meals;

import com.google.gson.annotations.Expose;

public class Reminder {
    public Reminder(String data) {
        this.data = data;
    }

    public Reminder(Integer reminderID, boolean check, String text) {
        this.reminderID = reminderID;
        this.check = check;
        this.data = text;
    }

    /**
     * Gets the Reminder text.
     *
     * @return The Reminder text as a String.
     */
    public String getData() {
        return this.data;
    }

    /**
     * Gets the Recipe check.
     *
     * @return Returns {@code true} if the Reminder is checked,
     * {@code false} otherwise.
     */
    public boolean getCheck() {
        return this.check;
    }

    /**
     * Sets a Recipe check.
     *
     * @param check The value for check.
     */
    public void setCheck(boolean check) {
        this.check = check;
    }

    public void setReminderID(Integer reminderID) {
        this.reminderID = reminderID;
    }

    public Integer getReminderID() {
        return reminderID;
    }

    @Expose private final String data;

    private boolean check;
    private Integer reminderID;
}
