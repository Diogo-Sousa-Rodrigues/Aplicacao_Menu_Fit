package persistence;

import model.users.User;

public class LoggedInUserStore {
    private static LoggedInUserStore instance = null;

    public static LoggedInUserStore getInstance() {
        if (instance == null) {
            instance = new LoggedInUserStore();
        }
        return instance;
    }
    private User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}

