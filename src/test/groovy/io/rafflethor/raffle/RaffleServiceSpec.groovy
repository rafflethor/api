package io.rafflethor.raffle

import static io.rafflethor.raffle.test.Exec.promise

import graphql.schema.DataFetchingEnvironment
import io.rafflethor.raffle.test.Fixtures
import io.rafflethor.raffle.twitter.TwitterRepository
import org.pac4j.core.profile.UserProfile
import ratpack.handling.Context
import ratpack.test.exec.ExecHarness
import spock.lang.AutoCleanup
import spock.lang.Specification

/**
 * Checks all {@link RaffleService} related functions
 *
 * @since 0.1.0
 */
class RaffleServiceSpec extends Specification {

    @AutoCleanup
    ExecHarness execHarness = ExecHarness.harness()

    void 'list all raffles: successful result'() {
        given: 'a repository, judge mocks'
        List<Raffle> raffleList = Fixtures.twitterRaffleList()
        TwitterRepository repository = Stub(TwitterRepository) {
            listAll(_ as Integer, _ as Integer) >> raffleList
        }

        and: 'a service implementation'
        RaffleService service = new RaffleService(twitterRepository: repository)

        and: 'a valid fetching environment'
        Map<String, Object> arguments = [max: 5, offset: 0]
        DataFetchingEnvironment environment = Fixtures.dataFetchingEnvironment(context, arguments)

        when: 'listing all available raffles'
        List<Raffle> result = execHarness.yield({
            promise(service.listAllRafflesByUser(environment))
        }).value as List<Raffle>

        then: 'there should be one result'
        result.size() == 1

        and: 'raffle data should be the expected'
        result.find() == raffleList.find()
    }

    /**
     * Returns an instance of {@link Context} containing an instance of
     * {@link UserProfile} in the registry via method {@link Context#get}
     *
     * @return an instance of {@link Context}}
     */
    Context getContext() {
        return Stub(Context) {
            get(UserProfile) >> Fixtures.userProfileWithUsername('john')
        }
    }
}
