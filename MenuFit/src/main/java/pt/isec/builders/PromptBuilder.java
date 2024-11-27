package pt.isec.builders;

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
