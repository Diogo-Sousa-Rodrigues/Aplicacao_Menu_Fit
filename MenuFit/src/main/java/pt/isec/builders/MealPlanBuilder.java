package pt.isec.builders;

import pt.isec.ai.CommonLLM;
import pt.isec.model.meals.Meal;
import pt.isec.model.meals.MealPlan;
import pt.isec.model.users.TimeBudget;
import pt.isec.model.users.User;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

public class MealPlanBuilder {
    private static MealPlanBuilder instance = null;
    public static MealPlanBuilder getInstance() {
        if (instance == null) {
            instance = new MealPlanBuilder();
        }
        return instance;
    }

    private MealPlanBuilder() {};

    public Optional<MealPlan> getMealPlan(User user,
                                          LocalDate begin, LocalDate end, CommonLLM llm)
        throws RuntimeException {

        PromptBuilder promptBuilder = PromptBuilder.getInstance();
        InstanceBuilder instanceBuilder = new InstanceBuilder();

        long days = ChronoUnit.DAYS.between(begin, end);

        MealPlan mealPlan = new MealPlan(user);
        TimeBudget timeBudget = user.getTimeBudget();

        mealPlan.setGoal(user.getHealthData().getObjective());
        mealPlan.setBeginDate(begin);
        mealPlan.setEndDate(end);

        try {
            // Step 1: Ask AI how many meals per day should the Meal Plan have
            int mealsPerDay = 5; // TODO: get numbers of meals per day from llm

            for (int day = 0; day <= days; day++) {
                // Calculate new day (begin + 1)
                LocalDate date = begin.plusDays(day); // = ...

                String prompt = promptBuilder.getMealsPerDayPrompt(user, date, timeBudget, mealsPerDay);

                Type typeToken = new TypeToken<List<Meal>>() {}.getType();

                Optional<List<Meal>> mealsOpt = instanceBuilder.getInstance(prompt, llm, typeToken, 5);
                if (mealsOpt.isEmpty()) {
                    throw new RuntimeException();
                }

                var meals = mealsOpt.get();

                mealPlan.putMeals(List.copyOf(meals));
            }

            return Optional.of(mealPlan);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
