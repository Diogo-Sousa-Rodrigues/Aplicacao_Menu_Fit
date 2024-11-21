package pt.isec.model.users;

import java.util.Date;

public class BasicUser extends GenericUser {
    public BasicUser(Integer userID, String firstName, String lastName, String email, Date birthdate, Gender gender) {
        super(userID, firstName, lastName, email, birthdate, gender);
    }
}
