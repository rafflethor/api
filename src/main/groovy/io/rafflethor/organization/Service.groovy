package io.rafflethor.organization

import javax.inject.Inject
import java.util.concurrent.CompletableFuture

import graphql.schema.DataFetchingEnvironment
import gql.ratpack.exec.Futures
import io.rafflethor.raffle.Raffle
import io.rafflethor.security.User

class Service {

    @Inject
    Repository repository

    CompletableFuture<List<Organization>> listAll(DataFetchingEnvironment env) {
        Selectors.ListAll params = Selectors.listAll(env)

        return Futures.blocking {
            return repository.listAllByUser(params.user, params.max, params.offset)
        }
    }

    CompletableFuture<Organization> get(DataFetchingEnvironment env) {
        UUID uuid = getID(env)

        return Futures.blocking({
            return repository.get(uuid)
        })
    }

    CompletableFuture<Organization> save(DataFetchingEnvironment env) {
        User user = Selectors.getUser(env)
        Organization event = Selectors.getOrganization(env)

        return Futures.blocking {
            return repository.save(event, user)
        }
    }

    Organization byRaffle(DataFetchingEnvironment env) {
        Raffle raffle = env.getSource()

        return repository.byRaffleId(raffle.id)
    }

    CompletableFuture<Map> delete(DataFetchingEnvironment env) {
        UUID uuid = Selectors.getID(env)

        return Futures.blocking({
            return [
                deleted: repository.delete(uuid)
            ]
        })
    }
}
