package pt.isec.model.meals;

import pt.isec.model.users.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//  {
//      "meals": [
//          {
//              // ...
//          },
//          {
//              // ...
//          }
//      ],
//      "goal": "",
//      "begin": ""
//      "end": ""
//  }
//
public class MealPlan {
    public MealPlan(User user) {
        this.meals = new ArrayList<>();
    }

    public boolean putMeal(Meal meal) {
        if (!this.meals.contains(meal)) {
            return this.meals.add(meal);
        }

        return false;
    }

    public void putMeals(List<Meal> meals) {
        for (var meal : meals) {
            this.putMeal(meal);
        }
    }

    public boolean removeMeal(Meal meal) {
        return this.meals.remove(meal);
    }

    public List<Meal> getMeals() {
        return this.meals;
    }

    public String getGoal() {
        return this.goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public LocalDateTime getBeginDate() {
        return this.begin;
    }

    public void setBeginDate(LocalDateTime begin) {
        this.begin = begin;
    }

    public LocalDateTime getEndDate() {
        return this.end;
    }

    public void setEndDate(LocalDateTime end) {
        this.end = end;
    }

    private List<Meal> meals;

    private String goal;

    private LocalDateTime begin;

    private LocalDateTime end;
}
