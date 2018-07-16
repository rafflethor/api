package io.rafflethor.raffle

import com.google.inject.AbstractModule
import com.google.inject.Scopes
import ratpack.service.Service

class Module extends AbstractModule {
    @Override
    protected void configure() {
        bind(Repository).to(RepositoryImpl).in(Scopes.SINGLETON)
        bind(RaffleService).in(Scopes.SINGLETON)
    }
}
