import static ratpack.groovy.Groovy.ratpack

import ratpack.server.ServerConfigBuilder
import io.rafflethor.config.AppConfig
import io.rafflethor.db.DatabaseConfig
import io.rafflethor.migration.FlywayConfig
import io.rafflethor.security.SecurityConfig

ratpack {
    serverConfig { ServerConfigBuilder config ->
        config
            .yaml("rafflethor.yml")
            .require("/app", AppConfig)
            .require("/database", DatabaseConfig)
            .require("/security", SecurityConfig)
            .require("/flyway", FlywayConfig)
    }
}
