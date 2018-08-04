package io.rafflethor.security

/**
 * Represents access to the underlying database
 *
 * @since 0.1.0
 */
interface Repository {

    /**
     * Retrieves the user information from the database given an
     * instance of a valid {@link UserCredentials}
     *
     * @param username
     * @param password
     * @return an instance of {@link User} or null if no user matches
     * the provided credentials
     * @since 0.1.0
     */
    User login(String username, String password)

    User findByUsername(String username)
}
