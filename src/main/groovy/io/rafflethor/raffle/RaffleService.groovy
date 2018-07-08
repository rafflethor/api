package io.rafflethor.raffle

import javax.inject.Inject
import java.util.concurrent.CompletableFuture

import gql.ratpack.exec.Futures
import graphql.schema.DataFetchingEnvironment
import io.rafflethor.raffle.twitter.TwitterJudge
import io.rafflethor.raffle.twitter.TwitterRepository

/**
 * Service linked to GraphQL queries
 *
 * @since 0.1.0
 */
class RaffleService {

    @Inject
    TwitterRepository twitterRepository

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
            twitterRepository.listAll(max, offset)
        })
    }

    /**
     * Lists all winners from a given raffle
     *
     * @param env data execution environment
     * @return a {@link CompletableFuture} carrying a list of {@link RaffleWinner}
     * @since 0.1.0
     */
    CompletableFuture<List<RaffleWinner>> pickWinners(DataFetchingEnvironment env) {
        final UUID uuid = UUID.fromString(env.arguments.raffleId as String)

        return Futures
            .blocking({ uuid })
            .thenApply(twitterRepository.&findById)
            .thenApply(twitterJudge.&pickWinners) as CompletableFuture<List<RaffleWinner>>
    }
}
