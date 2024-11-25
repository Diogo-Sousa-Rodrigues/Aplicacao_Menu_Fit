package pt.isec;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import pt.isec.ai.CommonLLM;
import pt.isec.ai.GeminiLLM;
import pt.isec.ai.GroqLLM;
import pt.isec.model.meals.Meal;
import pt.isec.model.meals.MealPlan;
import pt.isec.model.meals.Recipe;
import pt.isec.prompt.DurationAdapter;
import pt.isec.prompt.InstanceBuilder;
import pt.isec.prompt.PromptBuilder;

import java.time.Duration;
import java.util.Optional;

public class SampleLLM {
    public static void main(String[] args) {
        CommonLLM llm = GroqLLM.getInstance();
        llm.setApiKey("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");

        try {
            String prompt = PromptBuilder.getInstance().getMealPlanPrompt();

            InstanceBuilder instanceBuilder = new InstanceBuilder();

            Optional<MealPlan> mealOptional = instanceBuilder.getInstance(prompt,
                    llm, MealPlan.class, 5);

            if (mealOptional.isPresent()) {
                MealPlan meal = mealOptional.get();


                System.out.println("MealPlan: " + meal.getBeginDate() + "\n");
            }
            else {
                System.out.println("Unable to instantiate meal.\n");
            }
        } catch (Exception e) {
            System.out.println("Request failure: " + e);
        }

    }
}
