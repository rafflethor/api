package io.rafflethor.raffle.live

import static ratpack.stream.Streams.periodically

import javax.inject.Inject
import java.time.Duration
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber
import ratpack.rx.RxRatpack
import ratpack.handling.Context
import ratpack.stream.Streams
import io.rafflethor.raffle.bus.EventBusService

class LivePublisherServiceImpl implements LivePublisherService {

    @Inject
    EventBusService eventBusService

    @Override
    Publisher<Map> registerPublisher(Context context, String email, String raffleId) {
        Publisher<Map> stream = RxRatpack.publisher(eventBusService.create())

        return stream
    }
}
