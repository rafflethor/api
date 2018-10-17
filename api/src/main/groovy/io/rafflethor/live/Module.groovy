package io.rafflethor.live

import com.google.inject.AbstractModule
import com.google.inject.Scopes
import ratpack.service.Service

class Module extends AbstractModule {
    @Override
    protected void configure() {
        bind(LiveHandler).in(Scopes.SINGLETON)
        bind(PublisherService).to(PublisherServiceImpl).in(Scopes.SINGLETON)
    }
}
