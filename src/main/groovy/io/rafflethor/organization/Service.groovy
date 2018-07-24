package io.rafflethor.organization

import javax.inject.Inject
import java.util.concurrent.CompletableFuture

import graphql.schema.DataFetchingEnvironment
import gql.ratpack.exec.Futures
import io.rafflethor.raffle.Raffle

class Service {

    @Inject
    Repository repository

    CompletableFuture<List<Organization>> listAll(DataFetchingEnvironment env) {
        Integer max = env.arguments.max as Integer
        Integer offset = env.arguments.offset as Integer

        return Futures.blocking {
            return repository.listAll(max, offset)
        }
    }

    CompletableFuture<Organization> get(DataFetchingEnvironment env) {
        UUID uuid = UUID.fromString(env.arguments.id)

        return Futures.blocking({
            return repository.get(uuid)
        })
    }

    CompletableFuture<Organization> save(DataFetchingEnvironment env) {
        Organization event = new Organization(env.arguments.organization.subMap(Repository.FIELDS))

        return Futures.blocking {
            return repository.save(event)
        }
    }

    Organization byRaffle(DataFetchingEnvironment env) {
        Raffle raffle = env.getSource()

        return repository.byRaffleId(raffle.id)
    }

    CompletableFuture<Map> delete(DataFetchingEnvironment env) {
        UUID uuid = UUID.fromString(env.arguments.id.toString())

        return Futures.blocking({
            return [
                deleted: repository.delete(uuid)
            ]
        })
    }
}
