package pt.isec.model.meals;

import java.time.LocalDateTime;

public class ExtraMeal {

    public ExtraMeal(String name, int calories, LocalDateTime date) {
        this.name = name;
        this.calories = calories;
        this.date = date;
    }

    public ExtraMeal(String name, int calories) {
        this.name = name;
        this.calories = calories;
        this.date = LocalDateTime.now();
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getCalories() { return calories; }

    public void setCalories(int calories) { this.calories = calories; }

    public LocalDateTime getDate() { return date; }

    public void setDate(LocalDateTime date) { this.date = date; }


    private LocalDateTime date;

    private String name;

    private int calories;

}
