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
    User login(String username, String password) {
        String query = '''
      SELECT
        id, username, roles
      FROM
        users
      WHERE
        username = :username AND
        password = :password
    '''

        Map user = sql.firstRow(query,
                                username: username,
                                password: password,)

        return Optional
            .ofNullable(user)
            .map(this.&mapToUser)
            .orElse(null) as User
    }

    User findByUsername(String username) {
        String query = '''
          SELECT
            id, username, roles
          FROM
            users
          WHERE
            username = :username
      '''

        Map user = sql.firstRow(query, username: username)

        return Optional
            .ofNullable(user)
            .map(this.&mapToUser)
            .orElse(null) as User
    }

    User mapToUser(Map user) {
        Set roles = user.roles?.array as Set

        return new User(id: user.id, username: user.username, roles: roles)
    }
}
