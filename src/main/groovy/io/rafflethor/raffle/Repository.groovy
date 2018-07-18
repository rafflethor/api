package io.rafflethor.raffle

/**
 * Repository to get raffles
 *
 * @since 0.1.0
 */
interface Repository {

    static final List<String> FIELDS = ['id', 'name', 'noWinners', 'type', 'until', 'since']

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

    Raffle findRaffleFromSpot(String spotId)

    Raffle save(Raffle raffle)
}
