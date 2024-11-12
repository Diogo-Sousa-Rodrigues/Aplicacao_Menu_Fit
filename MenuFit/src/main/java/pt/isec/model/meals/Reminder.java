package pt.isec.model.meals;

import com.google.gson.annotations.Expose;

public class Reminder {
    public Reminder(String data) {
        this.data = data;
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

    @Expose private final String data;

    private boolean check;
}
