package io.rafflethor.eb

import rx.Observable

interface EventBusService {
    void publish(Map event)
    Observable<Map> create()
}
