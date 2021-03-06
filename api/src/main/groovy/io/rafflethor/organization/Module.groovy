package io.rafflethor.organization

import com.google.inject.AbstractModule
import com.google.inject.Scopes

class Module extends AbstractModule {
    @Override
    protected void configure() {
        bind(Repository).to(RepositoryImpl).in(Scopes.SINGLETON)
        bind(Service).to(ServiceImpl).in(Scopes.SINGLETON)
    }
}
