package io.rafflethor.raffle

import groovy.transform.Immutable
import graphql.schema.DataFetchingEnvironment
import io.rafflethor.util.Pagination

/**
 * Functions reponsible to gather information from the GraphQL
 * execution
 *
 * @since 0.1.0
 */
class Selectors {

    /**
     * Parameters to list all raffles
     *
     * @since 0.1.0
     */
    @Immutable
    static class ListAll {

        /**
         * Information to paginate the raffle list
         *
         * @since 0.1.0
         */
        Pagination pagination
    }

    @Immutable
    static class PickWinners {
        UUID id
    }

    /**
     * @since 0.1.0
     */
    @Immutable
    static class Save {
        Raffle raffle
    }

    /**
     * @since 0.1.0
     */
    @Immutable
    static class Registration {
        String spotId
        String email
    }

    /**
     * @since 0.1.0
     */
    @Immutable
    static class FindById {
        UUID id
    }

    /**
     * @since 0.1.0
     */
    @Immutable
    static class StartRaffle {
        UUID id
    }

    /**
     * @since 0.1.0
     */
    @Immutable
    static class CheckRaffleResult {
        UUID id
        String hash
    }

    /**
     * @since 0.1.0
     */
    @Immutable
    static class Delete {
        UUID id
    }

    static class Update {
        Raffle raffle
    }

    /**
     * Gathers max and offset from {@link DataFetchingEnvironment} parameter
     *
     * @param env the GraphQL environment
     * @return all required parameters to list raffles
     * @since 0.1.0
     */
    static ListAll listAll(DataFetchingEnvironment env) {
        Integer max = env.arguments.max as Integer
        Integer offset = env.arguments.offset as Integer

        return new ListAll(
            pagination: new Pagination(offset: offset, max: max)
        )
    }

    static PickWinners pickWinners(DataFetchingEnvironment env) {
        return new PickWinners(
            id: id(env)
        )
    }

    static Save save(DataFetchingEnvironment env) {
        Map<String, ?> input = env.arguments.input as Map<String, ?>
        Raffle raffle = new Raffle(input?.subMap(Repository.FIELDS))

        raffle.organizationId = UUID
            .fromString(env.arguments.input.organizationId as String)

        return new Save(
            raffle: raffle
        )
    }

    static Registration registration(DataFetchingEnvironment env) {
        final String spotId = env.arguments.spotId as String
        final String email = env.arguments.email as String

        return new Registration(
            spotId: spotId,
            email: email
        )
    }

    static FindById findById(DataFetchingEnvironment env) {
        final UUID uuid = UUID.fromString(env.arguments.id)

        return new FindById(id: uuid)
    }

    static StartRaffle startRaffle(DataFetchingEnvironment env) {
        final UUID uuid = UUID.fromString(env.arguments.id)

        return new StartRaffle(id: uuid)
    }

    static CheckRaffleResult checkRaffleResult(DataFetchingEnvironment env) {
        UUID raffleId = UUID.fromString(env.arguments.id)
        String userHash = env.arguments.hash

        return new CheckRaffleResult(
            id: raffleId,
            hash: userHash
        )
    }

    static Delete delete(DataFetchingEnvironment env) {
        UUID raffleId = UUID.fromString(env.arguments.id)

        return new Delete(id: raffleId)
    }

    static Update update(DataFetchingEnvironment env) {
        Map<String, ?> input = env.arguments.input as Map<String, ?>
        Raffle raffle = new Raffle(input?.subMap(Repository.FIELDS))

        return new Update(raffle: raffle)
    }

    static UUID id(DataFetchingEnvironment env) {
        return UUID.fromString(env.arguments.raffleId.toString())
    }
}
