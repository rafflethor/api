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
 * Service linked to GraphQL queries
 *
 * @since 0.1.0
 */
class Service {

    @Inject
    Repository raffleRepository

    @Inject
    ParticipantRepository participantRepository

    @Inject
    TwitterJudge twitterJudge

    @Inject
    EventBusService eventBusService

    /**
     * Lists all available raffles
     *
     * @param env data execution environment
     * @return a {@link CompletableFuture} carrying a list of {@link Raffle}
     * @since 0.1.0
     */
    CompletableFuture<List<Raffle>> listAllRafflesByUser(DataFetchingEnvironment env) {
        Integer max = env.arguments.max as Integer
        Integer offset = env.arguments.offset as Integer

        return Futures.blocking({
            raffleRepository.listAll(max, offset)
        })
    }

    /**
     * Lists all winners from a given raffle
     *
     * @param env data execution environment
     * @return a {@link CompletableFuture} carrying a list of {@link Winner}
     * @since 0.1.0
     */
    CompletableFuture<List<Winner>> pickWinners(DataFetchingEnvironment env) {
        final UUID uuid = UUID.fromString(env.arguments.raffleId as String)

        return Futures
            .blocking({ uuid })
            .thenApply(raffleRepository.&findById)
            .thenApply(twitterJudge.&pickWinners) as CompletableFuture<List<Winner>>
        }

    CompletableFuture<Raffle> save(DataFetchingEnvironment env) {
        Map<String, ?> input = env.arguments.input as Map<String, ?>
        Raffle raffle = new Raffle(input?.subMap(Repository.FIELDS))

        raffle.organizationId = UUID.fromString(env.arguments.input.organizationId as String)

        return Futures.blocking {
            raffleRepository.save(raffle)
        }
    }

    CompletableFuture<Map> raffleRegistration(DataFetchingEnvironment env) {
        final String spotId = env.arguments.spotId as String
        final String email = env.arguments.email as String

        return Futures.blocking({ spotId })
            .thenApply(raffleRepository.&findRaffleFromSpot)
            .thenApply({ Raffle raffle ->
               processRegistration(raffle, spotId, email)
            })
    }

    Map processRegistration(Raffle raffle, String spotId, String email) {
        return Optional
            .ofNullable(raffle)
            .map({ Raffle raff ->
                switch (raff.type) {
                  case 'TWITTER': return participantRepository.registerUser(raff.id, email)
                  case 'LIVE': return processLiveRegistration(raff, email)
                }
        }).orElse([:])
    }

    Map processLiveRegistration(Raffle raffle, String email) {
        return Optional
            .ofNullable(email)
            .map({ String mail -> participantRepository.registerUser(raffle.id, mail) })
            .orElse([raffleId: raffle.id])
    }

    CompletableFuture<Map> findById(DataFetchingEnvironment env) {
        final UUID uuid = UUID.fromString(env.arguments.id)

        return Futures.blocking({
            return raffleRepository.findById(uuid)
        })
    }

    CompletableFuture<Map> startRaffle(DataFetchingEnvironment env) {
        final UUID uuid = UUID.fromString(env.arguments.id)

        return Futures.blocking({
            return raffleRepository.markRaffleWaiting(uuid)
        })
    }

    CompletableFuture<Map> checkRaffleResult(DataFetchingEnvironment env) {
        UUID raffleId = UUID.fromString(env.arguments.id)
        String userHash = env.arguments.hash

        return Futures.blocking({
            raffleRepository.checkRaffleResult(raffleId, userHash)
        })
    }

    CompletableFuture<Map> delete(DataFetchingEnvironment env) {
        UUID raffleId = UUID.fromString(env.arguments.id)

        return Futures.blocking({
            return [deleted: raffleRepository.delete(raffleId)]
        })
    }

    CompletableFuture<Map> update(DataFetchingEnvironment env) {
        Map<String, ?> input = env.arguments.input as Map<String, ?>
        Raffle raffle = new Raffle(input?.subMap(Repository.FIELDS))

        return Futures.blocking({
            return raffleRepository.update(raffle)
        })
    }
}
