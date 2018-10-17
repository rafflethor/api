package io.rafflethor.security

import javax.inject.Inject
import javax.inject.Provider

import graphql.execution.instrumentation.ChainedInstrumentation
import graphql.execution.instrumentation.Instrumentation

/**
 * Loads all instrumentations
 *
 * @since 1.0.6
 */
class InstrumentationProvider implements Provider<Instrumentation> {

    /**
     * Authorization instrumentation. Checks whether the current
     * user profile is allowed to access a given path
     *
     * @since 1.0.6
     */
    @Inject
    AuthorizationInterceptor authorization

    @Override
    Instrumentation get() {
        List<Instrumentation> workflow = [
            authorization
        ]

        return new ChainedInstrumentation(workflow)
    }
}
