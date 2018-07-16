package io.rafflethor.participant

import com.google.inject.AbstractModule
import com.google.inject.Scopes
import io.rafflethor.participant.ParticipantRepository
import io.rafflethor.participant.ParticipantRepositoryImpl

class Module extends AbstractModule {
    @Override
    protected void configure() {
        bind(ParticipantRepository).to(ParticipantRepositoryImpl).in(Scopes.SINGLETON)
    }
}
