package io.rafflethor.raffle

import javax.inject.Inject
import org.reactivestreams.Publisher

import ratpack.handling.Handler
import ratpack.handling.Context

import io.rafflethor.security.User
import io.rafflethor.util.Handlers

/**
 * Handler which sends events of a given raffle to the attached SSE
 * client
 *
 * @since 0.1.0
 */
class ManagementHandler implements Handler {

    /**
     * Service responsible to manage raffles lifecycle
     *
     * @since 0.1.0
     */
    @Inject
    ManagementService service

    @Override
    void handle(Context ctx) {
        UUID id = UUID.fromString(ctx.pathTokens.id)
        User user = ctx.get(User)

        UUID updatedId = service.startRaffle(id, user)
        Publisher<Map> publisher = service.listenRaffleEvents(updatedId)

        Handlers
            .sse(publisher)
            .render(ctx)
    }
}
