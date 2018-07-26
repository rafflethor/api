package io.rafflethor.security

import javax.inject.Inject
import groovy.sql.Sql

/**
 * Accesses security related information persisted in the database
 *
 * @since 0.1.0
 */
class RepositoryImpl implements Repository {

    /**
     * Access to db
     *
     * @since 0.1.0
     */
    @Inject
    Sql sql

    /**
     * Gets basic information about a given user
     *
     * @param credentials
     * @return an instance of {@link User} with id and username
     * @since 0.1.0
     */
    @Override
    User login(UserCredentials credentials) {
        String query = '''
      SELECT
        id, username
      FROM
        users
      WHERE
        username = :username AND
        password = :password
    '''

        Map user = sql.firstRow(query,
                                username: credentials.username,
                                password: credentials.password,)

        return Optional
            .ofNullable(user)
            .map(this.&mapToUser)
            .orElse(null) as User
    }

    User mapToUser(Map user) {
        return new User(id: user.id, username: user.username)
    }
}
