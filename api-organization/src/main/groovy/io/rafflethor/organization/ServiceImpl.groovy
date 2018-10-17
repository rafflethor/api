package io.rafflethor.organization

import javax.inject.Inject
import java.util.concurrent.CompletableFuture
import graphql.schema.DataFetchingEnvironment
import gql.ratpack.exec.Futures
import io.rafflethor.security.User
import io.rafflethor.raffle.Raffle

class ServiceImpl implements Service {

    @Inject
    Repository repository

    @Override
    CompletableFuture<List<Organization>> listAll(DataFetchingEnvironment env) {
        Selectors.ListAll params = Selectors.listAll(env)

        return Futures.blocking({
            return repository.listAll(params.pagination, params.user)
        })
    }

    @Override
    CompletableFuture<Organization> get(DataFetchingEnvironment env) {
        Selectors.Get params = Selectors.get(env)

        return Futures.blocking({
            return repository.get(params.id, params.user)
        })
    }

    @Override
    CompletableFuture<Organization> save(DataFetchingEnvironment env) {
        Selectors.Save params = Selectors.save(env)

        return Futures.blocking({
            return repository.save(params.organization, params.user)
        })
    }

    @Override
    Organization byRaffle(DataFetchingEnvironment env) {
        Selectors.ByRaffle params = Selectors.byRaffle(env)

        return repository.byRaffleId(params.id, params.user)
    }

    @Override
    CompletableFuture<Map> delete(DataFetchingEnvironment env) {
        Selectors.Delete params = Selectors.delete(env)

        return Futures.blocking({
            return [
                deleted: repository.delete(params.id, params.user)
            ]
        })
    }

    @Override
    List<Raffle> findAllRaffles(DataFetchingEnvironment env) {
        Selectors.FindAllRaffles params = Selectors.findAllRaffles(env)

        return repository.findAllRaffles(params.id, params.user)
    }
}
