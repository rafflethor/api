package io.rafflethor.raffle.organization

import com.google.inject.AbstractModule
import com.google.inject.Scopes

class Module extends AbstractModule {
    @Override
    protected void configure() {
        bind(OrganizationRepository).to(OrganizationRepositoryImpl).in(Scopes.SINGLETON)
    }
}
