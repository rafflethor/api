import static ratpack.groovy.Groovy.ratpack
import static io.rafflethor.util.SystemResources.classpath

/**
 * Ratpack configuration is splitted in different files:
 *
 * - Handlers
 * - DI Module bindings
 * - Configuration files
 *
 */
ratpack {
    include classpath('Handlers.groovy')
    include classpath('Bindings.groovy')
    include classpath('Configs.groovy')
}
