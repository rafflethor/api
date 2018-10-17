package io.rafflethor.raffle

import rx.Observable
import javax.inject.Inject
import org.reactivestreams.Publisher
import ratpack.rx.RxRatpack
import ratpack.exec.Promise
import ratpack.stream.Streams
import io.rafflethor.eb.EventBusService
import io.rafflethor.security.User

/**
 * Manages a given raffle lifecycle
 *
 * @since 0.1.0
 */
class ManagementServiceImpl implements ManagementService {

    /**
     * Raffle related database access
     *
     * @since 0.1.0
     */
    @Inject
    Repository raffleRepository

    /**
     * App event service bus
     *
     * @since 0.1.0
     */
    @Inject
    EventBusService eventBusService

    @Override
    UUID startRaffle(UUID raffleId, User user) {
        return raffleRepository
            .markRaffleWaiting(raffleId, user)
            .id
    }

    @Override
    Publisher<Map> listenRaffleEvents(UUID raffleId) {
        Observable<Map> raffleEvents = eventBusService
            .create()
//            .filter { Map event -> event.id == raffleId }

        return RxRatpack.publisher(raffleEvents)
    }
}
