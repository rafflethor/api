package io.rafflethor.raffle

import static groovy.json.JsonOutput.toJson

import groovy.transform.ToString
import java.sql.Timestamp
import java.time.LocalDateTime

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
     * Whether to prevent or not previous winners to participate in
     * this raffle
     *
     * @since 0.1.0
     */
    Boolean preventPreviousWinners

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
     * {@link Raffle} related information in JSON
     *
     * @since 0.1.0
     */
    String payload

    /**
     *@ since 0.1.0
     */
    LocalDateTime since

    /**
     *@ since 0.1.0
     */
    LocalDateTime until

    void setPayload(Map payload) {
        this.payload = toJson(payload)
    }

    void setPayload(org.postgresql.util.PGobject object) {
        this.payload = object?.value
    }

    void setSince(Timestamp since) {
        this.since = since.toLocalDateTime()
    }

    void setSince(LocalDateTime since) {
        this.since = since
    }

    void setUntil(Timestamp until) {
        this.until = until.toLocalDateTime()
    }

    void setUntil(LocalDateTime until) {
        this.until = until
    }

    void setId(String id) {
        this.id = UUID.fromString(id)
    }

    void setId(UUID id) {
        this.id = id
    }

    void setOrganizationId(String organizationId) {
        this.organizationId = UUID.fromString(organizationId)
    }

    void setOrganizationId(UUID organizationId) {
        this.organizationId = organizationId
    }
}
