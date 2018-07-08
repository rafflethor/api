package io.rafflethor.raffle

import java.time.LocalDate
import groovy.transform.Immutable

/**
 * Represents a potential winner for a given raffle
 *
 * @since 0.1.0
 */
@Immutable(knownImmutableClasses = [Raffle, LocalDate])
class RaffleWinner {
    /**
     * Unique identifier of the winner
     *
     * @since 0.1.0
     */
    String id

    /**
     * Human readable name
     *
     * @since 0.1.0
     */
    String name

    /**
     * When the winner has been chosen
     *
     * @since 0.1.0
     */
    LocalDate when

    /**
     * The raffle the winner has been taken from
     *
     * @since 0.1.0
     */
    Raffle raffle
}
