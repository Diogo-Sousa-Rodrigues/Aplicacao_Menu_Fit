package pt.isec.model.users;

import pt.isec.persistence.BDManager;

/**
 * Interface that defines a contract for controllers that require
 * initialization with a {@link User} object.
 */
public interface UserInitializable {
    /**
     * Initializes the controller with a specific {@link User} object.
     * This method allows the controller to set up user-specific data,
     * enabling it to display or process information related to the
     * given user.
     *
     * @param user The {@link User} object containing user-specific information
     *             to initialize the controller.
     */
    void initializeUser(BasicUser user, BDManager bdManager);
}
