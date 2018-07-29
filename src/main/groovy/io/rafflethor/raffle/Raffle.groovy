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
     * Organization the raffle belongs to
     *
     * @since 0.1.0
     */
    UUID organizationId

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
     * Whether the current raffle is still going or has finished
     *
     * @since 0.1.0
     */
    String status

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

    void setId(String id) {
        this.id = UUID.fromString(id)
    }

    void setId(UUID id) {
        this.id = id
    }
}
