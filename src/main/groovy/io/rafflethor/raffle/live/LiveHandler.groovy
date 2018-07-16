package io.rafflethor.raffle.live

import static ratpack.sse.ServerSentEvents.serverSentEvents

import javax.inject.Inject
import org.reactivestreams.Publisher
import ratpack.sse.Event
import ratpack.handling.Handler
import ratpack.handling.Context

/**
 * Handler responsible for notifying registered
 * users about raffle winners
 *
 * @since 0.1.0
 */
class LiveHandler implements Handler {

    /**
     * Service listening to the raffle events
     *
     * @since 0.1.0
     */
    @Inject
    LivePublisherService service

    @Override
    void handle(Context ctx) {
        Publisher<Map> publisher = service
            .registerPublisher(ctx, 'email', 'raffleId')

        ctx.render(serverSentEvents(publisher, { Event event ->
            event.id('id').data('kk')
        }))
    }
}
