package io.rafflethor.organization

import groovy.transform.ToString
import io.rafflethor.raffle.Raffle

@ToString
class Organization {
    UUID id
    String name
    String status
    String description
    List<Raffle> raffles = []
}
