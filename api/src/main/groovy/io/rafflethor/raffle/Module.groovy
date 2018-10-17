package io.rafflethor.raffle

import com.google.inject.AbstractModule
import com.google.inject.Scopes

class Module extends AbstractModule {
    @Override
    protected void configure() {
        bind(Repository).to(RepositoryImpl).in(Scopes.SINGLETON)
        bind(Service).to(ServiceImpl).in(Scopes.SINGLETON)

        bind(ManagementService).to(ManagementServiceImpl).in(Scopes.SINGLETON)
        bind(ManagementHandler).in(Scopes.SINGLETON)
    }
}
