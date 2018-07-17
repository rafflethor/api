package io.rafflethor.organization

import io.rafflethor.raffle.Raffle

class Organization {
    UUID id
    String name
    String description
    List<Raffle> raffles = []
}
