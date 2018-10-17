package io.rafflethor.util

import org.reactivestreams.Publisher
import groovy.json.JsonOutput
import ratpack.func.Action
import ratpack.sse.Event
import ratpack.sse.ServerSentEvents

class Handlers {

    static <T> ServerSentEvents sse(Publisher<T> publisher, Action<? super Event<T>> action) {
        return ServerSentEvents.serverSentEvents(publisher, action)
    }

    static <T> ServerSentEvents sse(Publisher<T> publisher) {
        return ServerSentEvents.serverSentEvents(publisher, mapSSE())
    }

    static Action<? super Event<Map>>  mapSSE() {
        return { Event event ->
            return event
                .data({ Map payload ->
                   return JsonOutput.toJson(payload.data)
            })
                .id({ Map payload -> payload.id.toString() })
        } as Action<? super Event<Map>>
    }
}
