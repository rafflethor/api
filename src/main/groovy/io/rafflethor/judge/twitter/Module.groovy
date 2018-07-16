package io.rafflethor.judge.twitter

import com.google.inject.AbstractModule
import com.google.inject.Scopes
import twitter4j.Twitter

class Module extends AbstractModule {
    @Override
    protected void configure() {
        bind(Twitter).toProvider(TwitterProvider).in(Scopes.SINGLETON)
    }
}
