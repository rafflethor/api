package io.rafflethor.security

import javax.inject.Inject
import groovy.util.logging.Slf4j

/**
 * Checks current user roles with the ones required for
 * a given path and rejects users don't having the required
 * roles
 *
 * @since 1.0.6
 */
@Slf4j
class AuthorizationService {

    /**
     * Mapping configuration has to be declared
     * in the configuration file
     *
     * @since 1.0.6
     */
    @Inject
    SecurityConfig config

    /**
     * Represents the required roles for a given path
     *
     * @since 1.0.6
     */
    static class Mapping {
        String path
        Set<String> roles
    }

    /**
     * Checks whether a user has the required roles
     * or not for a given path
     *
     * @param user user information
     * @param path the path under consideration
     * @return true if the user has the required roles, false otherwise
     * @since 1.0.6
     */
    Boolean isAllowed(User user, String path) {
        List<Mapping> mappings = config
            .authorization
            .mappings
            .collect(AuthorizationService.&convertToMapping)

        Boolean isAllowed = resolveMapping(mappings, path)
            .map(AuthorizationService.&checkConstraints.rcurry(user.roles as Set))
            .orElse(false)

        log.debug "Is the user ${user.username} allowed to use the path $path ? ==> $isAllowed"

        return isAllowed
    }

    Boolean isSchemaVisible() {
        return config.authorization.schema
    }

    Boolean allowPartials() {
        return config.authorization.allowPartials
    }

    static Optional<Mapping> resolveMapping(List<Mapping> mappings, String path) {
        return Optional.ofNullable(mappings.find { Mapping mapping ->
            path ==~ mapping.path
        })
    }

    static Boolean isMappingAnonymous(Mapping mapping) {
        return mapping.roles == ['ANONYMOUS'] as Set
    }

    static Boolean hasMappingRoles(Mapping mapping, Set<String> roles) {
        return mapping.roles == roles
    }

    static Boolean checkConstraints(Mapping mapping, Set<String> roles) {
        return isMappingAnonymous(mapping) || hasMappingRoles(mapping, roles)
    }

    /**
     * Converts a given {@link Map.Entry} to a {@link Mapping}
     * instance
     *
     * @param entryPath the mapping path
     * @param entryRoles the mapping roles
     * @return an instance of {@link Mapping}
     * @since 1.0.6
     */
    static Mapping convertToMapping(String entryPath, String entryRoles) {
        Set<String> roles = entryRoles
            .split(",")
            .grep()*.trim() as Set

        return new Mapping(path: "${entryPath}.*", roles: roles)
    }
}
