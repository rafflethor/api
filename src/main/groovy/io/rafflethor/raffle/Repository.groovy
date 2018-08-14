package io.rafflethor.raffle

import io.rafflethor.util.Pagination
import io.rafflethor.security.User

/**
 * Repository to get raffles
 *
 * @since 0.1.0
 */
interface Repository {

    /**
     * Lists all raffles
     *
     * @param pagination
     * @param user
     * @return
     * @since 0.1.0
     */
    List<Raffle> listAll(Pagination pagination, User user)

    /**
     * Finds a given {@link Raffle} by its id
     *
     * @param id the identifier of the raffle
     * @param user who created the raffle
     * @since 0.1.0
     */
    Raffle findById(UUID id, User user)

    /**
     * Finds a given {@link Raffle} by the spot assigned to it
     *
     * @param spotId the spot used by the {@link Raffle} we would like to get
     * @return the {@link Raffle} using the spot id passed as argument
     * @since 0.1.0
     */
    Raffle findRaffleFromSpot(String spotId)

    /**
     * Saves or updates a given {@link Raffle}
     *
     * @param raffle the raffle we would like to save
     * @param user user saving the raffle
     * @return the saved {@link Raffle}
     * @since 0.1.0
     */
    Raffle upsert(Raffle raffle, User user)

    /**
     * Marks a raffle as ready to start (WAITING). Only the user who
     * created the raffle can mark the raffle as WAITING.
     *
     * @param id the id of the {@link Raffle}
     * @param user the user who created the raffle
     * @return the updated {@link Raffle}
     * @since 0.1.0
     */
    Raffle markRaffleWaiting(UUID id, User user)

    /**
     * Marks a raffle as LIVE
     *
     * @param id the id of the {@link Raffle}
     * @return the updated {@link Raffle}
     * @since 0.1.0
     */
    Raffle markRaffleLive(UUID id)

    /**
     * Marks a raffle as FINISHED
     *
     * @param id the id of the {@link Raffle}
     * @return the updated {@link Raffle}
     * @since 0.1.0
     */
    Raffle markRaffleFinished(UUID id)

    /**
     * Retrieves the first {@link Raffle} with the WAITING status
     *
     * @return an instance of {@link Raffle}
     * @since 0.1.0
     */
    Raffle findWaitingRaffle()

    /**
     * Finds a random list of participants from a given {@link Raffle}
     *
     * @param raffle the {@link Raffle} we want the winners from
     * @return a map containing the information of the winners of a
       given {@link Raffle}
     * @since 0.1.0
     */
    List<Map> findAllRandomWinners(Raffle raffle)

    /**
     * Retrieves a map containing information about the winners of a
     * given raffle including whether the user with the hash passed as
     * argument has won or not
     *
     * @param id the id of the raffle
     * @param userHash hash of a user
     * @return information about the raffle winners
     * @since 0.1.0
     */
    Map checkRaffleResult(UUID id, String userHash)

    /**
     * Deletes the {@link Raffle} with the id passed as argument
     *
     * @param id the id of the {@link Raffle} we would like to delete
     * @param user the user who created the raffle
     * @return true if the {@link Raffle} has been removed, false otherwise
     * @since 0.1.0
     */
    Boolean delete(UUID id, User user)

    /**
     * Finds all chosen winners for a given raffle
     *
     * @param raffle the raffle we want to get the winners from
     * @return a list of {@link Winner}
     * @since 0.1.0
     */
    List<Winner> findAllWinners(Raffle raffle)

    /**
     * Marks a list of winners as non valid using their ids. And
     * return the information about all the winners once the update
     * has been done
     *
     * @param winnersIds ids of the non valid winners
     * @param raffleId id of the raffle
     * @return a list of the non valid winners
     * @since 0.1.0
     */
    List<Winner> markWinnersAsNonValid (List<UUID> winnersIds, UUID raffleId)
}
