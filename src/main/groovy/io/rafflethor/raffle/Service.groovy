package io.rafflethor.raffle

import java.util.concurrent.CompletableFuture
import graphql.schema.DataFetchingEnvironment

/**
 * Service linked to GraphQL queries and mutations regarding raffles
 *
 * @since 0.1.0
 */
interface Service {

    /**
     * Lists all available raffles
     *
     * @param env data execution environment
     * @return a {@link CompletableFuture} carrying a list of {@link Raffle}
     * @since 0.1.0
     */
    CompletableFuture<List<Raffle>> listAllRafflesByUser(DataFetchingEnvironment env)

    /**
     * Lists all winners from a given raffle
     *
     * @param env data execution environment
     * @return a {@link CompletableFuture} carrying a list of {@link Winner}
     * @since 0.1.0
     */
    CompletableFuture<List<Winner>> pickWinners(DataFetchingEnvironment env)

    /**
     * Saves a new {@link Raffle} with the content found in the
     * incoming {@link DataFetchingEnvironment}
     *
     * @param env data execution environment
     * @return the saved {@link Raffle} wrapped in a {@link CompletableFuture}
     * @since 0.1.0
     */
    CompletableFuture<Raffle> save(DataFetchingEnvironment env)

    /**
     * Registers a new participant in a given {@link Raffle}
     *
     * @param env data execution environment
     * @return the information about the new participant
     * @since 0.1.0
     */
    CompletableFuture<Map> raffleRegistration(DataFetchingEnvironment env)

    /**
     * Finds a {@link Raffle} by its id
     *
     * @param env data execution environment
     * @return
     * @since 0.1.0
     */
    CompletableFuture<Raffle> findById(DataFetchingEnvironment env)

    /**
     * Marks a {@link Raffle} as it's been started
     *
     * @param env data execution environment
     * @return the {@link Raffle} initiated
     * @since 0.1.0
     */
    CompletableFuture<Raffle> startRaffle(DataFetchingEnvironment env)

    /**
     * Checks the result of a given {@link Raffle}
     *
     * @param env data execution environment
     * @return a map containing information about the result of a given {@link Raffle}
     * @since 0.1.0
     */
    CompletableFuture<Map> checkRaffleResult(DataFetchingEnvironment env)

    /**
     * Deletes a specific {@link Raffle} by its id
     *
     * @param env data execution environment
     * @return a map containing information about the deletion process
     * @since 0.1.0
     */
    CompletableFuture<Map> delete(DataFetchingEnvironment env)

    /**
     * Updates a specific {@link Raffle}
     *
     * @param env data execution environment
     * @return the updated {@link Raffle}
     * @since 0.1.0
     */
    CompletableFuture<Raffle> update(DataFetchingEnvironment env)

    /**
     * Finds all chosen winners for a given raffle
     *
     * @param env data execution environment
     * @return a list of {@link Winner}
     * @since 0.1.0
     */
    List<Winner> findAllRaffleWinners(DataFetchingEnvironment env)

    /**
     * Extracts the hashtag from the raffle payload
     *
     * @param env data execution environment
     * @return the hashtag found in the payload map
     * @since 0.1.0
     */
    String extractHashtag(DataFetchingEnvironment env)

    /**
     * Marks some selected winners as non valid, whether they were not
     * present at the raffle or because any other issue.
     *
     * @param env data execution environment
     * @return an updated list of the raffle winners
     * @since 0.1.0
     */
    CompletableFuture<List<Winner>> markWinnersAsNonValid(DataFetchingEnvironment env)
}
