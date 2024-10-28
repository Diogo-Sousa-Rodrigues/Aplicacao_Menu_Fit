package pt.isec.prompt;

import pt.isec.model.users.HealthData;
import pt.isec.model.users.User;
import pt.isec.persistence.EphemeralStore;

import java.util.Optional;

public class PromptBuilder {
    private static PromptBuilder instance = null;

    public static PromptBuilder getInstance() {
        if (instance == null) {
            instance = new PromptBuilder();
        }
        return instance;
    }

    public String buildMealPlanPrompt(User user) {
        EphemeralStore store = EphemeralStore.getInstance();

        Optional<HealthData> healthData = store.getHealthData(user);

        if (healthData.isEmpty()) {
            // throw might be more appropriate
            return null;
        }

        // Build prompt...

        return "TODO";
    }

    private PromptBuilder() {
        // TODO: ...
    }
}
