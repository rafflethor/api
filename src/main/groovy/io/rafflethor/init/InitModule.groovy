package io.rafflethor.init

import com.google.inject.AbstractModule
import com.google.inject.Scopes
import ratpack.service.Service
import com.google.inject.multibindings.Multibinder

class InitModule extends AbstractModule {
    @Override
    protected void configure() {
        Multibinder<Service> servicesBinder = Multibinder.newSetBinder(binder(), Service)

        servicesBinder.with {
            addBinding().to(FlywayService).in(Scopes.SINGLETON)
        }
    }
}
