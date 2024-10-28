package pt.isec.model.users;

import java.util.Date;

public class BasicUser extends GenericUser {
    public BasicUser(String firstName, String lastName, String email, Date birthdate, Gender gender) {
        super(firstName, lastName, email, birthdate, gender);
    }
}
