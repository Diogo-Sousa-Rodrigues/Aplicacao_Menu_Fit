package pt.isec;

import java.util.Random;

public class SampleLLM {
    public static void main(String[] args) {
        Random random = new Random();

        int three = 0;
        int four = 0;
        int five = 0;
        int six = 0;

        for (int i = 0; i < 100; ++i) {
            double value = random.nextGaussian(3, 2.2);

            if (value < 3) {
                var distance = 3 - value;
                value += distance;
            }

            int v = (int) value;
            if (v == 3) {
                three++;
            } else if (v == 4) {
                four++;
            } else if (v == 5) {
                five++;
            } else if (v == 6) {
                six++;
            }

            if (value <= 6.0) {
                System.out.println(value);
            } else {
                System.out.println("\t" + value);
            }
        }

        System.out.println("Three: " + three);
        System.out.println("Four: " + four);
        System.out.println("Five: " + five);
        System.out.println("Six: " + six);

        return;

        //CommonLLM llm = GroqLLM.getInstance();
        //llm.setApiKey("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");

        //try {
        //    String prompt = PromptBuilder.getInstance().getMealPlanPrompt();

        //    InstanceBuilder instanceBuilder = new InstanceBuilder();

        //    Optional<MealPlan> mealOptional = instanceBuilder.getInstance(prompt,
        //            llm, MealPlan.class, 5);

        //    if (mealOptional.isPresent()) {
        //        MealPlan meal = mealOptional.get();


        //        System.out.println("MealPlan: " + meal.getBeginDate() + "\n");
        //    }
        //    else {
        //        System.out.println("Unable to instantiate meal.\n");
        //    }
        //} catch (Exception e) {
        //    System.out.println("Request failure: " + e);
        //}

    }
}
