package io.rafflethor.raffle.bus

import rx.Observable

interface EventBusService {
    void publish(Map event)
    Observable<Map> create()
}
