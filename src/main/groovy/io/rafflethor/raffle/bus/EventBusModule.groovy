package io.rafflethor.raffle.bus

import com.google.common.eventbus.EventBus

import com.google.inject.AbstractModule
import com.google.inject.Scopes

class EventBusModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(EventBus).in(Scopes.SINGLETON)
        bind(EventBusService).to(EventBusServiceImpl).in(Scopes.SINGLETON)
    }
}
