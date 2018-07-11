package io.rafflethor.raffle.twitter

import io.rafflethor.raffle.Raffle
import io.rafflethor.raffle.RaffleWinner
import io.rafflethor.raffle.test.Fixtures
import spock.lang.Specification
import twitter4j.Query
import twitter4j.Twitter

import spock.lang.Ignore

/**
 * Checks the process of picking a list of winners from a twitter
 * raffle
 *
 * @since 0.1.0
 */
class TwitterJudgeSpec extends Specification {

    @Ignore
    void 'get list of winners'() {
        given: 'an instance of twitter api'
        Twitter twitter = Stub(Twitter) {
            search(_ as Query) >> Fixtures.twitterQueryResult()
        }

        and: 'instance of a twitter judge'
        TwitterJudge judge = new TwitterJudge(twitter: twitter)

        and: 'a given raffle'
        Raffle raffle = Fixtures.twitterRaffle()

        when: 'asking for a list of winners'
        List<RaffleWinner> winnerList = judge.pickWinners(raffle)

        then: 'the list of winners should be the same size of the raffle noWinners value'
        winnerList.size() == raffle.noWinners
    }
}
