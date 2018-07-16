package io.rafflethor.raffle.live

import com.google.inject.AbstractModule
import com.google.inject.Scopes
import ratpack.service.Service

class LiveModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(LiveHandler).in(Scopes.SINGLETON)
        bind(LivePublisherService).to(LivePublisherServiceImpl).in(Scopes.SINGLETON)
    }
}
