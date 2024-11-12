package pt.isec.model.meals;

import pt.isec.model.users.User;

import java.time.LocalDateTime;
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
//      "duration": ""
//  }
//
public class MealPlan {
    public MealPlan(User user) {
        // test
    }

    private List<Meal> meals;

    private String goal;

    private LocalDateTime begin;

    private LocalDateTime end;
}
