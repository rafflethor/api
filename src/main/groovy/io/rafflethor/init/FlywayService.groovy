package io.rafflethor.init

import groovy.util.logging.Slf4j
import org.flywaydb.core.Flyway
import ratpack.service.Service
import ratpack.service.StartEvent

import javax.inject.Inject
import javax.sql.DataSource

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
  FlywayConfig config

  @SuppressWarnings('UnusedMethodParameter')
  void onStart(StartEvent startEvent) {
    log.info('Flyway: starts')

    Flyway flyway = new Flyway()

    flyway.dataSource = dataSource
    flyway.locations = config.migrations
    flyway.schemas = config.schemas

    flyway.repair()
    flyway.migrate()

    log.info('Flyway: ends')
  }
}
