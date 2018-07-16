package io.rafflethor.raffle.participant

interface ParticipantRepository {

    /**
     * At the moment a given participant must provide an email
     * to participate in a raffle. The system must provide a
     * unique id to participate in the raffle.
     *
     * @param raffleId id of the raffle
     * @param email email used to register the participant
     * @return the registration information required to participate
     * in the raffle
     * @since 0.1.0
     */
    Map<String,?> registerUser(UUID raffleId, String email)
}
