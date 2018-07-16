package io.rafflethor.raffle.organization

import javax.inject.Inject
import java.util.concurrent.CompletableFuture

import graphql.schema.DataFetchingEnvironment
import gql.ratpack.exec.Futures

class OrganizationService {

    @Inject
    OrganizationRepository eventRepository

    CompletableFuture<List<Organization>> listAll(DataFetchingEnvironment env) {
        Integer max = env.arguments.max as Integer
        Integer offset = env.arguments.offset as Integer

        return Futures.blocking {
            return eventRepository.listAll(max, offset)
        }
    }

    CompletableFuture<Organization> save(DataFetchingEnvironment env) {
        Organization event = new Organization(env.arguments.event.subMap(OrganizationRepository.FIELDS))

        return Futures.blocking {
            return eventRepository.save(event)
        }
    }
}
