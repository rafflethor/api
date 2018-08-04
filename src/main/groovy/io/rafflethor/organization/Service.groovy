package io.rafflethor.organization

import java.util.concurrent.CompletableFuture
import graphql.schema.DataFetchingEnvironment
import io.rafflethor.security.User

/**
 * @since 0.1.0
 */
interface Service {

    /**
     * @param env
     * @return
     * @since 0.1.0
     */
    CompletableFuture<List<Organization>> listAll(DataFetchingEnvironment env)

    /**
     * @param env
     * @return
     * @since 0.1.0
     */
    CompletableFuture<Organization> get(DataFetchingEnvironment env)

    /**
     * @param env
     * @return
     * @since 0.1.0
     */
    CompletableFuture<Organization> save(DataFetchingEnvironment env)

    /**
     * @param env
     * @return
     * @since 0.1.0
     */
    Organization byRaffle(DataFetchingEnvironment env)

    /**
     * @param env
     * @return
     * @since 0.1.0
     */
    CompletableFuture<Map> delete(DataFetchingEnvironment env)
}
