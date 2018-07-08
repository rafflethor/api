package io.rafflethor.raffle.twitter

import javax.inject.Inject
import javax.inject.Provider

import io.rafflethor.config.Config
import twitter4j.Twitter
import twitter4j.TwitterFactory
import twitter4j.conf.ConfigurationBuilder

/**
 * Provides an instance of {@link Twitter} which gives access to the Twitter API
 *
 * @since 0.1.0
 */
class TwitterProvider implements Provider<Twitter> {

    /**
     * Configuration required to access Twitter OAuth credentials
     *
     * @since 0.1.0
     */
    @Inject
    Config config

    @Override
    Twitter get() {
        ConfigurationBuilder builder = new ConfigurationBuilder()
            .setDebugEnabled(true)
            .setOAuthConsumerKey(config.twitter.consumerKey)
            .setOAuthConsumerSecret(config.twitter.consumerSecret)
            .setOAuthAccessToken(config.twitter.accessToken)
            .setOAuthAccessTokenSecret(config.twitter.accessTokenSecret)

        return new TwitterFactory(builder.build()).instance
    }
}
