package io.rafflethor.raffle

import com.google.inject.AbstractModule
import com.google.inject.Scopes
import io.rafflethor.raffle.twitter.TwitterRepository
import io.rafflethor.raffle.twitter.TwitterRepositoryImpl
import io.rafflethor.raffle.participant.ParticipantRepository
import io.rafflethor.raffle.participant.ParticipantRepositoryImpl
import ratpack.service.Service

class RaffleModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(TwitterRepository).to(TwitterRepositoryImpl).in(Scopes.SINGLETON)
        bind(ParticipantRepository).to(ParticipantRepositoryImpl).in(Scopes.SINGLETON)
        bind(RaffleService).in(Scopes.SINGLETON)
    }
}
