package io.rafflethor.raffle

import groovy.json.JsonOutput
import javax.inject.Inject
import java.util.concurrent.CompletableFuture
import gql.ratpack.exec.Futures
import graphql.schema.DataFetchingEnvironment

import io.rafflethor.judge.twitter.TwitterJudge
import io.rafflethor.participant.ParticipantRepository
import io.rafflethor.eb.EventBusService

/**
 * Service linked to GraphQL queries and mutations regarding raffles
 *
 * @since 0.1.0
 */
class ServiceImpl implements Service {

    @Inject
    Repository raffleRepository

    @Inject
    ParticipantRepository participantRepository

    @Inject
    TwitterJudge twitterJudge

    @Inject
    EventBusService eventBusService

    @Override
    CompletableFuture<List<Raffle>> listAllRafflesByUser(DataFetchingEnvironment env) {
        final Selectors.ListAll params = Selectors.listAll(env)

        return Futures.blocking({
            raffleRepository.listAll(params.pagination, params.user)
        })
    }

    @Override
    CompletableFuture<List<Winner>> pickWinners(DataFetchingEnvironment env) {
        final Selectors.PickWinners params = Selectors.pickwinners(env)

        return Futures
            .blocking({ params.id })
            .thenApply(raffleRepository.&findById)
            .thenApply(twitterJudge.&pickWinners) as CompletableFuture<List<Winner>>
        }

    @Override
    CompletableFuture<Raffle> save(DataFetchingEnvironment env) {
        final Selectors.Save params = Selectors.save(env)

        return Futures.blocking {
            raffleRepository.save(params.raffle, params.user)
        }
    }

    @Override
    CompletableFuture<Map> raffleRegistration(DataFetchingEnvironment env) {
        final Selectors.Registration params = Selectors.registration(env)

        return Futures.blocking({ params.spotId })
            .thenApply(raffleRepository.&findRaffleFromSpot)
            .thenApply({ Raffle raffle ->
               processRegistration(raffle, params.spotId, params.email)
            })
    }

    private Map processRegistration(Raffle raffle, String spotId, String email) {
        return Optional
            .ofNullable(raffle)
            .map({ Raffle raff ->
                switch (raff.type) {
                  case 'TWITTER': return participantRepository.registerUser(raff.id, email)
                  case 'LIVE': return processLiveRegistration(raff, email)
                }
        }).orElse([:])
    }

    private Map processLiveRegistration(Raffle raffle, String email) {
        return Optional
            .ofNullable(email)
            .map({ String mail -> participantRepository.registerUser(raffle.id, mail) })
            .orElse([raffleId: raffle.id])
    }

    @Override
    CompletableFuture<Raffle> findById(DataFetchingEnvironment env) {
        final Selectors.FindById params = Selectors.findById(env)

        return Futures.blocking({
            return raffleRepository.findById(params.id, params.user)
        })
    }

    @Override
    CompletableFuture<Raffle> startRaffle(DataFetchingEnvironment env) {
        final Selectors.StartRaffle params = Selectors.startRaffle(env)

        return Futures.blocking({
            return raffleRepository.markRaffleWaiting(params.id, params.user)
        })
    }

    @Override
    CompletableFuture<Map> checkRaffleResult(DataFetchingEnvironment env) {
        final Selectors.CheckRaffleResult params = Selectors.checkRaffleResult(env)

        return Futures.blocking({
            raffleRepository.checkRaffleResult(params.id, params.hash)
        })
    }

    @Override
    CompletableFuture<Map> delete(DataFetchingEnvironment env) {
        final Selectors.Delete params = Selectors.delete(env)

        return Futures.blocking({
            return [deleted: raffleRepository.delete(params.id, params.user)]
        })
    }

    @Override
    CompletableFuture<Raffle> update(DataFetchingEnvironment env) {
        final Selectors.Update params = Selectors.update(env)

        return Futures.blocking({
            return raffleRepository.update(params.raffle, params.user)
        })
    }
}
