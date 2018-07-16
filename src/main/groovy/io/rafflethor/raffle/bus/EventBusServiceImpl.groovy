package io.rafflethor.raffle.bus

import com.google.common.eventbus.EventBus
import com.google.common.eventbus.Subscribe

import javax.inject.Inject
import rx.Observable
import rx.Subscriber

class EventBusServiceImpl implements EventBusService {

    static class Processor {
        Subscriber<Map> subscriber

        @Subscribe
        void process(Map event) {
            if (subscriber.isUnsubscribed() == false) {
                subscriber.onNext(event);
            }
        }
    }

    @Inject
    EventBus eventBus

    void publish(Map event) {
        eventBus.post(event)
    }

    Observable<Map> create() {
        Observable.create({ Subscriber<Map> subscriber ->
            eventBus.register(new Processor(subscriber: subscriber))
        })
    }
}
