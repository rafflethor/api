package io.rafflethor.judge.twitter

import javax.inject.Inject
import java.time.LocalDate
import groovy.util.logging.Slf4j

import io.rafflethor.raffle.Judge
import io.rafflethor.raffle.Raffle
import io.rafflethor.raffle.Winner
import twitter4j.Query
import twitter4j.Status
import twitter4j.Twitter
import twitter4j.User

/**
 * This judge will go to Twitter and will get a given number of
 * winners for the given
 *
 * @since 0.1.0
 */
@Slf4j
class TwitterJudge implements Judge {

    /**
     * Default date format for Twitter queries
     *
     * @since 0.10
     */
    static final String DATE_FORMAT = 'yyyy/MM/dd'

    /**
     * Access to Twitter API
     *
     * @since 0.1.0
     */
    @Inject
    Twitter twitter

    @Override
    List<Winner> pickWinners(Raffle raffle) {
        log.debug("picking ${raffle.noWinners} winners from raffle, ${raffle.id}")

        Query hashTagQuery = new Query(raffle.payload.hashTag)
            .since(raffle.payload.since.format(DATE_FORMAT))
            .until(raffle.payload.until.format(DATE_FORMAT))

        return twitter
            .search(hashTagQuery)
            .tweets
            .collect(toWinner(raffle))
            .take(raffle.noWinners)
    }

    private static Closure<Winner> toWinner(Raffle raffle) {
        return { Status status ->
            User user = status.user

            return new Winner(id: user.screenName, name: user.name, when: LocalDate.now(), raffle: raffle)
        } as Closure<Winner>
    }
}
