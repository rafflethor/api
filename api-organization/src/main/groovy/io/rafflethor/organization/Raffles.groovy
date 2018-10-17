package io.rafflethor.organization

import io.rafflethor.raffle.Raffle

import groovy.sql.GroovyRowResult
import groovy.json.JsonSlurper

class Raffles {

    /**
     * Relevant fields of type {@link Raffle}
     *
     * @since 0.1.0
     */
    static final List<String> FIELDS = [
        'id',
        'name',
        'type',
        'noWinners',
        'preventPreviousWinners',
        'organizationId',
        'since',
        'until',
        'payload',
        'status'
    ]

    static Raffle toRaffle(List row) {
        return Optional
            .ofNullable(row)
            .map({ r ->[FIELDS, r].transpose().collectEntries()})
            .map({ m -> new Raffle(m) })
            .orElse(null)
    }

    static Raffle toRaffle(GroovyRowResult row) {
        return Optional
            .ofNullable(row)
            .map({ GroovyRowResult r -> row.subMap(FIELDS).plus(payload: row.payload) })
            .map({ Map m -> new Raffle(m) })
            .orElse(null)
    }
}
