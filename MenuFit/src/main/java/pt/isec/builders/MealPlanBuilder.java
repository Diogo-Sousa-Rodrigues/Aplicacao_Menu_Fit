package pt.isec.builders;

import pt.isec.ai.CommonLLM;
import pt.isec.model.meals.Meal;
import pt.isec.model.meals.MealPlan;
import pt.isec.model.users.HealthData;
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

    public Optional<MealPlan> getMealPlan(String goal, User user, HealthData healthData,
                                          TimeBudget timeBudget, LocalDate begin, LocalDate end, CommonLLM llm)
        throws RuntimeException {

        PromptBuilder promptBuilder = PromptBuilder.getInstance();
        InstanceBuilder instanceBuilder = new InstanceBuilder();

        long days = ChronoUnit.DAYS.between(begin, end);

        try {
            // Step 1: Ask AI how many meals per day should the Meal Plan have
            int mealsPerDay = 5; // TODO: get numbers of meals per day from llm

            for (int day = 0; day <= days; day++) {
                // Calculate new day (begin + 1)
                LocalDate date = begin.plusDays(day); // = ...

                String prompt = promptBuilder.getMeals(user, date, timeBudget, mealsPerDay);

                Type typeToken = new TypeToken<List<Meal>>() {}.getType();

                Optional<List<Meal>> mealsOpt = instanceBuilder.getInstance(prompt, llm, typeToken, 5);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }
}
