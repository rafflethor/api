package io.rafflethor.raffle.test

import graphql.schema.DataFetchingEnvironment
import org.pac4j.core.profile.UserProfile
import ratpack.handling.Context
import twitter4j.QueryResult
import io.rafflethor.raffle.Raffle

class Fixtures {

    /**
     * Creates an instance of {@link DataFetchingEnvironment}
     *
     * @param context an instance of {@link Context}
     * @param arguments the arguments coming from client
     * @return an instance of {@link DataFetchingEnvironment}
     * @since 0.1.0
     */
    static DataFetchingEnvironment dataFetchingEnvironment(Context context, Map<String, Object> arguments) {
        return new DefaultDataFetchingEnvironment(context: context, arguments: arguments)
    }

    /**
     * Creates a {@link UserProfile} having a specific username
     *
     * @param username the {@link UserProfile} username attribute
     * @return an instance of {@link UserProfile}
     * @since 0.1.0
     */
    static UserProfile userProfileWithUsername(String username) {
        UserProfile userProfile = new UserProfile()

        userProfile.addAttribute('username', username)

        return userProfile
    }

    static Raffle twitterRaffle() {
        return new Raffle(
            id: UUID.randomUUID(),
            name: 'anyconference t-shirt',
            noWinners: 2,
            payload: [
                since: new Date(),
                until: new Date(),
                hashTag: "#hashTag"
            ]
        )
    }

    static List<Raffle> twitterRaffleList() {
        return [new Raffle(
                id: UUID.randomUUID(),
                name: 'anyconference t-shirt',
                noWinners: 2,
                payload: [
                    since: new Date(),
                    until: new Date(),
                    hashTag: "#hashTag"
                ]
        )]
    }

    static QueryResult twitterQueryResult() {
        return new DefaultQueryResult()
    }
}
