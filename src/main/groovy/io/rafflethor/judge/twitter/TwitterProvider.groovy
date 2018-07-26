package io.rafflethor.judge.twitter

import javax.inject.Inject
import javax.inject.Provider

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
    TwitterConfig config

    @Override
    Twitter get() {
        ConfigurationBuilder builder = new ConfigurationBuilder()
            .setDebugEnabled(true)
            .setOAuthConsumerKey(config.consumerKey)
            .setOAuthConsumerSecret(config.consumerSecret)
            .setOAuthAccessToken(config.accessToken)
            .setOAuthAccessTokenSecret(config.accessTokenSecret)

        return new TwitterFactory(builder.build()).instance
    }
}
