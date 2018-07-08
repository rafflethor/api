package io.rafflethor.event

import com.google.inject.AbstractModule
import com.google.inject.Scopes

class EventModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(EventRepository).to(EventRepositoryImpl).in(Scopes.SINGLETON)
    }
}
