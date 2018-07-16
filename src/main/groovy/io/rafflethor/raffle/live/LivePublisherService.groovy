package io.rafflethor.raffle.live

import ratpack.handling.Context
import org.reactivestreams.Publisher

interface LivePublisherService {
    Publisher<Map> registerPublisher(Context context, String email, String raffleId)
}
