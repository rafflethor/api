package io.rafflethor.raffle

import javax.inject.Inject
import java.util.concurrent.CompletableFuture

import gql.ratpack.exec.Futures
import graphql.schema.DataFetchingEnvironment

import io.rafflethor.judge.twitter.TwitterJudge
import io.rafflethor.participant.ParticipantRepository

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
        return Futures.blocking {
            raffleRepository.save(new Raffle(env.arguments.input.subMap(Repository.FIELDS)))
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
            .map({ String mail -> participantRepository.registerUser(raffle, mail) })
            .orElse([:])
    }
}
