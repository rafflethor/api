package io.rafflethor.security

import com.google.inject.AbstractModule
import com.google.inject.Scopes

class Module extends AbstractModule {
    @Override
    protected void configure() {
        bind(CryptoService).in(Scopes.SINGLETON)
        bind(AlgorithmProvider).in(Scopes.SINGLETON)
        bind(Service).to(ServiceImpl).in(Scopes.SINGLETON)
        bind(Repository).to(RepositoryImpl).in(Scopes.SINGLETON)
        bind(JwtTokenProviderHandler).in(Scopes.SINGLETON)
        bind(JwtTokenCheckerHandler).in(Scopes.SINGLETON)
    }
}
