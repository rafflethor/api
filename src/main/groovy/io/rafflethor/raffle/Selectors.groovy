package io.rafflethor.raffle

import ratpack.handling.Context

import groovy.transform.Immutable
import graphql.schema.DataFetchingEnvironment
import io.rafflethor.util.Pagination
import io.rafflethor.security.User

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
        User user
        Pagination pagination
    }

    @Immutable
    static class PickWinners {
        UUID id
        User user
    }

    /**
     * @since 0.1.0
     */
    // TODO make it immutable. At the moment is not possible
    // because Raffle is not immutable
    static class Save {
        Raffle raffle
        User user
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
        User user
    }

    /**
     * @since 0.1.0
     */
    @Immutable
    static class StartRaffle {
        UUID id
        User user
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
        User user
    }

    /**
     * @since 0.1.0
     */
    // TODO make it @Immutable (raffle is not immutable)
    static class Update {
        Raffle raffle
        User user
    }

    static class FindAllWinners {
        Raffle raffle
    }

    static class MarkWinnersAsNonValid {
        List<UUID> winnersIds
        UUID raffleId
    }

    static class AssignSpot {
        String id
        UUID raffleId
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
        User user = getUser(env)

        return new ListAll(
            pagination: new Pagination(offset: offset, max: max),
            user: user
        )
    }

    static PickWinners pickWinners(DataFetchingEnvironment env) {
        UUID id = id(env)
        User user = getUser(env)

        return new PickWinners(id: id, user: user)
    }

    static Save save(DataFetchingEnvironment env) {
        Map<String, ?> input = env.arguments.input as Map<String, ?>
        Raffle raffle = new Raffle(input?.subMap(Raffles.FIELDS))
        User user = getUser(env)

        raffle.organizationId = UUID
            .fromString(env.arguments.input.organizationId as String)

        return new Save(
            raffle: raffle,
            user: user
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
        final User user = getUser(env)

        return new FindById(id: uuid, user: user)
    }

    static StartRaffle startRaffle(DataFetchingEnvironment env) {
        final UUID uuid = UUID.fromString(env.arguments.id)
        final User user = getUser(env)

        return new StartRaffle(id: uuid, user: user)
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
        User user = getUser(env)

        return new Delete(id: raffleId, user: user)
    }

    static Update update(DataFetchingEnvironment env) {
        Map<String, ?> input = env.arguments.input as Map<String, ?>
        Raffle raffle = new Raffle(input?.subMap(Raffles.FIELDS))
        User user = getUser(env)

        return new Update(raffle: raffle, user: user)
    }

    static User getUser(DataFetchingEnvironment env) {
        Context context = env.context as Context
        User user = context.get(User)

        return user
    }

    static FindAllWinners findAllWinners(DataFetchingEnvironment env) {
        Raffle raffle = env.source as Raffle

        return new FindAllWinners(raffle: raffle)
    }

    static UUID id(DataFetchingEnvironment env) {
        return UUID.fromString(env.arguments.raffleId.toString())
    }

    static MarkWinnersAsNonValid markWinnersAsNonValid(DataFetchingEnvironment env) {
        Map<String, ?> input = env.arguments.input as Map<String, ?>
        UUID raffleId = UUID.fromString(input.raffleId)
        List<UUID> winnersIds = input
            .winnersIds
            .collect(UUID.&fromString) as List<UUID>

        return new MarkWinnersAsNonValid(winnersIds: winnersIds, raffleId: raffleId)
    }

    static AssignSpot assignSpot(DataFetchingEnvironment env) {
        UUID raffleId = UUID.fromString(env.arguments.raffleId)
        String spotId = env.arguments.spotId

        return new AssignSpot(id: spotId, raffleId: raffleId)
    }
}
