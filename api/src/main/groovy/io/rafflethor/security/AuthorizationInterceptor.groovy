package io.rafflethor.security

import javax.inject.Inject
import groovy.util.logging.Slf4j

import gql.DSL
import graphql.GraphQLError
import graphql.execution.ExecutionTypeInfo
import graphql.execution.instrumentation.NoOpInstrumentation
import graphql.execution.instrumentation.parameters.InstrumentationFieldFetchParameters
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import ratpack.handling.Context

/**
 * Checks the permissions of the current user, and allows or rejects the current execution
 *
 * @since 1.0.6
 */
@Slf4j
class AuthorizationInterceptor extends NoOpInstrumentation {

    /**
     * In case some fetcher has not been authorised but don't want
     * to return an error but nothing instead.
     *
     * @since 1.0.6
     */
    static final DataFetcher<?> EMPTY_FETCHER = { DataFetchingEnvironment env ->
        return null
    }

    static final User ANONYMOUS_USER = new User(username: 'anonymous', roles: ['ANONYMOUS'])

    /**
     * Service responsible to check whether a {@link User} is allowed to
     * visit a given path
     *
     * @since 1.0.6
     */
    @Inject
    AuthorizationService authorizationService

    @Override
    DataFetcher<?> instrumentDataFetcher(DataFetcher<?> dataFetcher, InstrumentationFieldFetchParameters params) {
        log.debug('instrumenting DataFetcher to check authorization')

        Context context = params.environment.context as Context
        ExecutionTypeInfo typeInfo = params.typeInfo
        String path = typeInfo.path
        DataFetcher notAllowed = getNotAllowedFetcher(authorizationService.allowPartials(), params)

        /**
         * Schema instrospection is controlled by configuration flag ALL/NOTHING
         */
        if (path.startsWith('/__schema')) {
            if (authorizationService.isSchemaVisible()) {
                return dataFetcher
            } else {
                return getAuthorizationFailureFetcher(params)
            }
        }

        /**
         * For any other GraphQL path
         */
        User user = context
            .maybeGet(User)
            .orElse(ANONYMOUS_USER)

        return Optional
            .of(user)
            .filter(authorizationService.&isAllowed.rcurry(path))
            .map { dataFetcher }
            .orElseGet { notAllowed } as DataFetcher
    }

    /**
     * When somebody is not allowed to execute a given fetcher you can choose whether to return
     * an error and stop executing nested fetchers or, just to return an empty value.
     *
     * @param partialsAllowed true if you want to return empty values when not authorized
     * @param params current instance of {@link InstrumentationFieldFetchParameters} from execution flow
     * @return an instance of {@link DataFetcher} as the result of authorization not granted
     * @since 1.0.6
     */
    static DataFetcher<?> getNotAllowedFetcher(Boolean partialsAllowed, InstrumentationFieldFetchParameters params) {
        return partialsAllowed ?
                EMPTY_FETCHER :
                getAuthorizationFailureFetcher(params)
    }

    /**
     * Returns a {@link DataFetcher} which raises a security exception
     *
     * @params parameters of the current execution
     * @return a {@link DataFetcher} raising an {@link GraphQLError}
     * @since 1.0.6
     */
    static DataFetcher getAuthorizationFailureFetcher(InstrumentationFieldFetchParameters params) {
        return DSL.errorFetcher(params) {
            message 'Forbidden Resource'
            extensions(i18n: 'ERROR.SECURITY.FORBIDDEN')
        }
    }
}
