package io.rafflethor.live

import static ratpack.stream.Streams.periodically

import javax.inject.Inject
import java.time.Duration
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber
import ratpack.rx.RxRatpack
import ratpack.handling.Context
import ratpack.stream.Streams
import io.rafflethor.eb.EventBusService
import groovy.util.logging.Slf4j

@Slf4j
class PublisherServiceImpl implements PublisherService {

    @Inject
    EventBusService eventBusService

    @Override
    Publisher<Map> registerPublisher(Context context, String raffleId, String userHash) {
        log.debug "registering publisher for raffleId: $raffleId and userHash: $userHash"

        Publisher<Map> stream = RxRatpack
            .publisher(eventBusService.create())

        return stream
    }
}
