package io.rafflethor.init

import groovy.util.logging.Slf4j
import org.flywaydb.core.Flyway
import ratpack.service.Service
import ratpack.service.StartEvent

import javax.inject.Inject
import javax.sql.DataSource
import io.rafflethor.config.Config

/**
 * Executes Flyway migration on application's startup
 *
 * @since 0.1.0
 */
@Slf4j
class FlywayService implements Service {

  @Inject
  DataSource dataSource

  @Inject
  Config config

  @SuppressWarnings('UnusedMethodParameter')
  void onStart(StartEvent startEvent) {
    log.info('Flyway: starts')

    Flyway flyway = new Flyway()

    flyway.dataSource = dataSource
    flyway.locations = config.flyway.migrations
    flyway.schemas = config.flyway.schemas

    flyway.repair()
    flyway.migrate()

    log.info('Flyway: ends')
  }
}
