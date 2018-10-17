package io.rafflethor.raffle

import ratpack.exec.Promise
import org.reactivestreams.Publisher
import io.rafflethor.security.User

/**
 * Related functions to manage a raffle lifecycle
 *
 * @since 0.1.0
 */
interface ManagementService {

    /**
     * Marks a given raffle as WAITING
     *
     * @param raffleId the raffle to start
     * @param user the user who created the raffle
     * @return an instance of {@link Publisher} with the raffle's id
     * @since 0.1.0
     */
    UUID startRaffle(UUID raffleId, User user)

    /**
     * Listens to all events related to a given raffle
     *
     * @param raffleId the raffle to start
     * @return an instance of {@link Promise} with the raffle's id
     * @since 0.1.0
     */
    Publisher<Map> listenRaffleEvents(UUID raffleId)
}
