import org.junit.jupiter.api.Test;
import pt.isec.model.users.BasicUser;
import pt.isec.model.users.Gender;
import pt.isec.model.users.User;
import pt.isec.persistence.EphemeralStore;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmailAndPasswordValTest {
    @Test
    void testUser(){
        EphemeralStore store = EphemeralStore.getInstance();
        BasicUser user = new BasicUser(10, "João", "Dias", "jdias@gmail.com",LocalDate.now(), Gender.Other);
        store.putUser(user,"1234");
        Optional<User> test = store.getUser(user.getEmail(), "1234");
        assertTrue(test.isPresent());
        assertEquals(user,test.get());
    }
}
