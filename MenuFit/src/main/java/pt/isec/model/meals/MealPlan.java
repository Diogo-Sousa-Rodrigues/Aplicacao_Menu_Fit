package pt.isec.model.meals;

import pt.isec.model.users.BasicUser;
import pt.isec.model.users.User;

import java.time.LocalDate;
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
        this.userID = user.getIdUser();
    }

    public MealPlan(Integer mealPlanID, Integer userID, String beginDate, String endDate, String goal) {
        this.mealPlanID = mealPlanID;
        this.userID = userID;
        this.begin = LocalDate.parse(beginDate);
        this.end = LocalDate.parse(endDate);
        this.goal = goal;
    }

    public boolean putMeal(Meal meal) {
        if (!this.meals.contains(meal)) {
            return this.meals.add(meal);
        }
        return false;
    }

    public boolean putExtraMeal(ExtraMeal meal) {
        if (!this.extraMeals.contains(meal)) {
            return this.extraMeals.add(meal);
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

    public List<ExtraMeal> getExtraMeals() { return this.extraMeals; }

    public String getGoal() {
        return this.goal;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public LocalDate getBeginDate() {
        return this.begin;
    }

    public void setBeginDate(LocalDate begin) {
        this.begin = begin;
    }

    public LocalDate getEndDate() {
        return this.end;
    }

    public void setEndDate(LocalDate end) {
        this.end = end;
    }

    public void setIDUser(Integer idUser){
        this.userID = idUser;
    }

    public Integer getUserID() {
        return userID;
    }

    public int getMealPlanID() {
        return mealPlanID;
    }

    private List<Meal> meals = new ArrayList<>();
    private final List<ExtraMeal> extraMeals = new ArrayList<>();

    private String goal;

    private LocalDate begin;

    private LocalDate end;
    Integer userID;
    Integer mealPlanID;

}
