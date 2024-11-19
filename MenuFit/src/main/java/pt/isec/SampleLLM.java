package pt.isec;

import pt.isec.ai.CommonLLM;
import pt.isec.ai.GroqLLM;
import pt.isec.prompt.PromptBuilder;

public class SampleLLM {
    public static void main(String[] args) {
        CommonLLM llm = GroqLLM.getInstance();
        llm.setApiKey("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");

        try {
            String prompt = PromptBuilder.getInstance().getRecipePrompt();
            String response = llm.request(prompt);

            System.out.println("Response: " + response);
        } catch (Exception e) {
            System.out.println("Request failure: " + e);
        }

    }
}
