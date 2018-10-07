package io.rafflethor.live

import static ratpack.sse.ServerSentEvents.serverSentEvents

import groovy.json.JsonOutput
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
    PublisherService service

    @Override
    void handle(Context ctx) {
        Publisher<Map> publisher = service
            .registerPublisher(ctx,
                               ctx.pathTokens.id,
                               ctx.pathTokens.hash)

        ctx.render(serverSentEvents(publisher, { Event event ->
            return event
                .data({ Map payload -> JsonOutput.toJson(payload.data) })
                .id({ Map payload -> payload?.id?.toString() })
        }))
    }
}
