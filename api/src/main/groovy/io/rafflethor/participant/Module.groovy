package io.rafflethor.participant

import com.google.inject.AbstractModule
import com.google.inject.Scopes
import io.rafflethor.participant.Repository
import io.rafflethor.participant.RepositoryImpl

class Module extends AbstractModule {
    @Override
    protected void configure() {
        bind(Repository).to(RepositoryImpl).in(Scopes.SINGLETON)
    }
}
