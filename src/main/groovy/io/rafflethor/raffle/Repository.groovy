package io.rafflethor.raffle

/**
 * Repository to get raffles
 *
 * @since 0.1.0
 */
interface Repository {

    /**
     * Relevant fields of type {@link Raffle}
     *
     * @since 0.1.0
     */
    static final List<String> FIELDS = [
        'id',
        'name',
        'noWinners',
        'type',
        'status',
        'until',
        'since'
    ]

    /**
     * Lists all raffles
     *
     * @param max maximum number of results
     * @param offset the offset of the result
     * @return
     * @since 0.1.0
     */
    List<Raffle> listAll(Integer max, Integer offset)

    /**
     * Finds a given {@link Raffle} by its id
     *
     * @param id the identifier of the raffle
     * @since 0.1.0
     */
    Raffle findById(UUID id)

    /**
     * Finds a given {@link Raffle} by the spot assigned to it
     *
     * @param spotId the spot used by the {@link Raffle} we would like to get
     * @return the {@link Raffle} using the spot id passed as argument
     * @since 0.1.0
     */
    Raffle findRaffleFromSpot(String spotId)

    /**
     * Saves a given {@link Raffle}
     *
     * @param raffle the raffle we would like to save
     * @return the saved {@link Raffle}
     * @since 0.1.0
     */
    Raffle save(Raffle raffle)

    /**
     * Marks a raffle as ready to start (WAITING)
     *
     * @param id the id of the {@link Raffle}
     * @return the updated {@link Raffle}
     * @since 0.1.0
     */
    Raffle markRaffleWaiting(UUID id)

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
     * @return true if the {@link Raffle} has been removed, false otherwise
     * @since 0.1.0
     */
    Boolean delete(UUID id)

    /**
     * Updates a {@link Raffle} with the information contained
     * in the raffle passed as argument
     *
     * @param raffle raffle containing the information to update
     * @return the updated raffle
     * @since 0.1.0
     */
    Raffle update(Raffle raffle)
}
