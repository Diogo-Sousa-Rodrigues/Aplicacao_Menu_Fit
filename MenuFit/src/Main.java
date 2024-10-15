import model.users.BasicUser;
import model.users.Gender;
import model.users.User;
import persistence.EphemeralStore;

import java.util.Date;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        EphemeralStore store = EphemeralStore.getInstance();

        String email = "mail@mail.com";
        String password = "test";

        Date newDate = new Date();
        User newUser = new BasicUser("Thomas A.", "Anderson",
                email, newDate, Gender.Male);

        store.putUser(newUser, password);

        Optional<User> getResult = store.getUser(email, password);

        assert getResult.isPresent();

        Optional<User> putResult = store.putUser(newUser, password);

        assert putResult.isEmpty();
    }
}