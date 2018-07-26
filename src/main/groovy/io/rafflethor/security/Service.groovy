package io.rafflethor.security

import java.util.concurrent.CompletableFuture
import graphql.schema.DataFetchingEnvironment

/**
 * @since 0.1.0
 */
interface Service {

    /**
     * @since 0.1.0
     */
    final List<String> FIELDS = ['username', 'password']

    /**
     * @param env
     * @return
     * @since 0.1.0
     */
    CompletableFuture<User> login(DataFetchingEnvironment env)
}
