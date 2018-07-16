package io.rafflethor.init

import groovy.util.logging.Slf4j
import ratpack.service.Service
import ratpack.service.StartEvent

import javax.inject.Inject
import io.rafflethor.eb.EventBusService

/**
 *
 * @since 0.1.0
 */
@Slf4j
class WelcomeNotificationService implements Service {

    @Inject
    EventBusService eventBusService

    @SuppressWarnings('UnusedMethodParameter')
    void onStart(StartEvent startEvent) {
        log.info('welcome')

        new Thread().start({
            Thread.sleep(30000)
            eventBusService.publish(id: 'id', data: [name: '@john'])
        })
    }
}
