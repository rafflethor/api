package io.rafflethor.raffle.twitter

import io.rafflethor.raffle.Raffle

/**
 * Repository to get raffles of twitter nature
 *
 * @since 0.1.0
 */
interface TwitterRepository {

    static final List<String> FIELDS = ['id', 'name', 'noWinners', 'type', 'until', 'since']

    /**
     * Lists all twitter raffles
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
     * Saves all raffles passed as parameters in one batch
     *
     * @param raffles the list of raffles to be saved
     * @since 0.1.0
     */
    void saveBatch(List<Raffle> raffles)
}
