package io.rafflethor.raffle

import groovy.transform.ToString

/**
 * Base class for all raffles
 *
 * @since 0.1.0
 */
@ToString
class Raffle {

    /**
     * Raffle identifier
     *
     * @since 0.1.0
     */
    UUID id

    /**
     * The name of the raffle
     *
     * @since 0.1.0
     */
    String name

    /**
     * How many winners will this raffle have
     *
     * @since 0.1.0
     */
    Integer noWinners

    /**
     * Type of the {@link Raffle}
     *
     * @since 0.1.0
     */
    String type

    /**
     * {@link Raffle} related information
     *
     * @since 0.1.0
     */
    Map payload

    /**
     *@ since 0.1.0
     */
    Date since

    /**
     *@ since 0.1.0
     */
    Date until
}
