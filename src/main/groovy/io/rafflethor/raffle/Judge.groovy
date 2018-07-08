package io.rafflethor.raffle

/**
 * Represents an impartial judge who picks a set of winners
 *
 * @since 0.1.0
 */
interface Judge {

    /**
     * Picks a list of winners from the information present
     * in the raffle
     *
     * @param raffle the raffle to pick the winners from
     * @return a list of winners
     * @since 0.1.0
     */
    List<RaffleWinner> pickWinners(Raffle raffle)
}
