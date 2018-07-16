package io.rafflethor.live

import ratpack.handling.Context
import org.reactivestreams.Publisher

interface PublisherService {
    Publisher<Map> registerPublisher(Context context, String email, String raffleId)
}
