package io.rafflethor.raffle.twitter

import com.google.inject.AbstractModule
import com.google.inject.Scopes
import twitter4j.Twitter

class TwitterModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Twitter).toProvider(TwitterProvider).in(Scopes.SINGLETON)
        bind(TwitterRepository).to(TwitterRepositoryImpl).in(Scopes.SINGLETON)
    }
}
