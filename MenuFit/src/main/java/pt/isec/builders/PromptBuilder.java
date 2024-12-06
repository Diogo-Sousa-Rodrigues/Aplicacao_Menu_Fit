package pt.isec.builders;

import pt.isec.model.users.HealthData;
import pt.isec.model.users.TimeBudget;
import pt.isec.model.users.User;

import java.time.LocalDate;

public class PromptBuilder {
    private static PromptBuilder instance = null;

    public static PromptBuilder getInstance() {
        if (instance == null) {
            instance = new PromptBuilder();
        }

        return instance;
    }

    private PromptBuilder() {};

    public String getIngredientPrompt() {
        String prompt = null;

        prompt = "Give me a JSON that represents an ingredient of a meal. Follow the following template: " + ingredientJSONTemplate + "\n";
        prompt += String.join("\n", replyRequirements);

        return prompt;
    }

    public String getRecipePrompt() {
        String prompt = null;

        prompt = "Give me a JSON that represents a recipe for a meal. Follow the following template: " + recipeJSONTemplate + "\n";
        prompt += String.join("\n", replyRequirements);

        return prompt;
    }

    public String getMealPrompt() {
        String prompt = null;

        prompt = "Give me a JSON that represents a meal. Follow the following template: " + mealJSONTemplate + "\n";
        prompt += String.join("\n", replyRequirements);

        return prompt;
    }

    public String getMealPlanPrompt() {
        String prompt = null;

        prompt = "Give me a JSON that represents a meal plan for the next 7 days, try to keep 3 meals minimum per day (total of 21 meals or more are expected). Follow the following template: " + mealPlanJSONTemplate + "\n";
        prompt += String.join("\n", replyRequirements);

        return prompt;
    }

    public String getMealsPerDayPrompt(String goal, User user, HealthData healthData) {
        return "TODO";
    }

    public String getMeals(User user, LocalDate date, TimeBudget timeBudget, int mealsPerDay) {

        HealthData healthData = user.getHealthData();

        StringBuilder prompt = new StringBuilder();

        prompt.append("You are tasked with generating a JSON Array with meals data.\n");
        prompt.append("The JSON Array will contain the meals for a given day.\n");
        prompt.append("The number of expected meals in the JSON Array is: ").append(mealsPerDay).append(".\n");
        prompt.append("The meals are part of a strict meal plan that you must respect, that has the following goal: ")
                .append(healthData.getObjective()).append('\n');
        prompt.append("The person you will generate the meals for identifies as a ")
                .append(user.getGender().toString()).append(", is ")
                .append(healthData.getHeight()).append(" meters tall, has")
                .append(healthData.getWeight()).append(" kilograms of weight, and is ")
                .append(user.getAge()).append(" years old.\n");
        prompt.append("The person has a fitness level of: ").append(healthData.getLevelOfFitness());
        prompt.append("Please respect the following health restrictions when generating the meals: ")
                .append("Allergies or intolerances: ").append(healthData.getAllergiesOrIntolerances())
                .append("Chronic health issues: ").append(healthData.getChronicHealth())
                .append("Gastrointestinal issues: ").append(healthData.getGastrointestinalIssues())
                .append("Vitamin deficiencies: ").append(healthData.getVitaminDeficiencies())
                .append("Medications: ").append(healthData.getMedications())
                .append("Other medical reasons: ").append(healthData.getMedicalReasons());
        prompt.append("The meals you generate are for the day: ").append(date);
        prompt.append("The meals you generate must not exceed: ").append(timeBudget.getBudget());
        prompt.append("The preparation time for a meal must try to meet the persons timing restrictions (minutes): ")
                .append(timeBudget.getAvailableTime().toMinutes());
        prompt.append("You must use the following JSON template for each of the meals you generate: ")
                .append(mealJSONTemplate);
        prompt.append("Here are your reply requirements: ").append(String.join("\n", replyRequirements));

        return prompt.toString();
    }

    private static final String ingredientJSONTemplate =
            "{" +
                "name:string," +
                "description:string," +
                "quantity:float," +
                "units:string," +
                "calories:int," +
                "macros: {" +
                    "proteins:float," +
                    "carbs:float," +
                    "fats:float" +
                "}," +
                "allergens:[string]" +
            "}";

    private static final String recipeJSONTemplate =
            "{" +
                "name:string," +
                "description:string," +
                "servings:int," +
                "calories:int," +
                "prep:int (time in minutes)," +
                "reminders:[{ data: string }]" +
                "ingredients:[" + ingredientJSONTemplate + "]" +
            "}";

    private static final String mealJSONTemplate =
            "{" +
                "type:string (Breakfast, Lunch, Dinner, Snack)," +
                "date:string (LocalTimeDate)," +
                "recipe: " + recipeJSONTemplate  +
            "}";

    private static final String mealPlanJSONTemplate =
            "{" +
                "goal: string," +
                "begin: string (LocalDateTime)," +
                "end: string (LocalDateTime)," +
                "meals: [" + mealJSONTemplate +"]" +
            "}";

    private static final String[] replyRequirements = {
            "Reply with the JSON only.",
            "Do not reply in MD format (markdown).",
            "Reply using plain text only.",
            "Do not use measurements like '1/2' or '3/4'. Always spell ou the value like '0.5' or '0.25'.",
            "Make sure the JSON is in the correct format. Keys must be wrapped in \"'s.",
            "Do not forget to match every closing bracket in the JSON. {}'s and []'s.",
            "The JSON must be in {}'s. Do not forget the ending }.",
    };
}
