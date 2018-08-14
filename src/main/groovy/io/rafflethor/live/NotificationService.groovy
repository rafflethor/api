package io.rafflethor.live

import groovy.util.logging.Slf4j
import groovy.json.JsonOutput

import ratpack.service.Service
import ratpack.service.StartEvent
import ratpack.service.StopEvent
import ratpack.exec.Execution
import ratpack.exec.ExecController

import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

import javax.inject.Inject
import io.rafflethor.eb.EventBusService
import io.rafflethor.raffle.Raffle
import io.rafflethor.raffle.Repository as RaffleRepository

/**
 *
 * @since 0.1.0
 */
@Slf4j
class NotificationService implements Service {

    @Inject
    EventBusService eventBusService

    @Inject
    RaffleRepository raffleRepository

    private volatile ScheduledFuture<?> nextFuture
    private ScheduledExecutorService executorService
    private volatile boolean stopped

    void onStart(StartEvent startEvent) {
        executorService = startEvent
            .getRegistry()
            .get(ExecController.class)
            .getExecutor()

        scheduleNext()
    }

    @SuppressWarnings('UnusedMethodParameter')
    void onStop(StopEvent stopEvent) {
        stopped = true;
        Optional.ofNullable(nextFuture).ifPresent { f -> f.cancel(true) };
    }

    void scheduleNext() {
        nextFuture = executorService.schedule(this.&runIt, 5, TimeUnit.SECONDS)
    }

    void runIt() {
        if (stopped) {
            return;
        }

        Execution.fork()
            .onComplete { e -> scheduleNext() }
            .onError { e -> e.printStackTrace() }
            .start(this.&handleIt)
    }

    void handleIt(event) {
        Raffle raffle = raffleRepository.findWaitingRaffle()
        log.info("checking if there is any raffle to start...")

        if (raffle) {
            log.info("executing raffle ${raffle.id}")
            raffleRepository.markRaffleLive(raffle.id)

            log.info("countdown for ${raffle.id}")
            executeCountdown(raffle.id)

            List<Map> winners = raffleRepository.findAllRandomWinners(raffle)
            log.info("winners for ${raffle.id}")

            eventBusService.publish(
                id: raffle.id,
                data: [
                    winners: winners,
                    type: 'winners'
                ]
            )

            log.info("finishing ${raffle.id}")
            raffleRepository.markRaffleFinished(raffle.id)
        }
    }

    void executeCountdown(UUID id) {
       (10..0).each { countdown ->
            eventBusService.publish(
                id: id,
                data: [
                    countdown: countdown,
                    type: 'countdown'
                ]
            )
            Thread.sleep(1000)
        }
    }
}
