package io.rafflethor.event

import javax.inject.Inject
import java.util.concurrent.CompletableFuture

import graphql.schema.DataFetchingEnvironment
import gql.ratpack.exec.Futures

class EventService {

    @Inject
    EventRepository eventRepository

    CompletableFuture<List<Event>> listAll(DataFetchingEnvironment env) {
        Integer max = env.arguments.max as Integer
        Integer offset = env.arguments.offset as Integer

        return Futures.blocking {
            return eventRepository.listAll(max, offset)
        }
    }

    CompletableFuture<Event> save(DataFetchingEnvironment env) {
        Event event = new Event(env.arguments.event.subMap(EventRepository.FIELDS))

        return Futures.blocking {
            return eventRepository.save(event)
        }
    }
}
