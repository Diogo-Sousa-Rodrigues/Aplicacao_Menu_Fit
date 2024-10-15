package persistence;

import model.users.User;

import java.util.*;

public class EphemeralStore {
    private static EphemeralStore instance = null;

    public static EphemeralStore getInstance() {
        if (instance == null) {
            instance = new EphemeralStore();
        }
        return instance;
    }

    public Optional<User> getUser(String email, String password) {
        if (this.credentials.containsKey(email)) {
            String key = this.credentials.get(email);

            if (key.equals(password)) {
                if (users.containsKey(email)) {
                    return Optional.of(users.get(email));
                }
            }
        }

        return Optional.empty();
    }

    public Optional<User> putUser(User user, String password) {
        String email = user.getEmail();

        // TODO: email and password validation

        if (!this.credentials.containsKey(email)) {
            if (!this.users.containsKey(email)) {
                this.credentials.put(email, password);
                this.users.put(email, user);

                return Optional.of(user);
            }
        }

        return Optional.empty();
    }

    private EphemeralStore() {
        this.users = new HashMap<>();
        this.credentials = new HashMap<>();
    }

    private final Map<String, User> users;

    private final Map<String, String> credentials;
}
