package io.rafflethor.init

import com.google.inject.AbstractModule
import com.google.inject.Scopes
import ratpack.service.Service
import com.google.inject.multibindings.Multibinder

import io.rafflethor.migration.FlywayService
import io.rafflethor.live.NotificationService

class Module extends AbstractModule {
    @Override
    protected void configure() {
        Multibinder<Service> servicesBinder = Multibinder.newSetBinder(binder(), Service)

        servicesBinder.with {
            addBinding().to(FlywayService).in(Scopes.SINGLETON)
            addBinding().to(NotificationService).in(Scopes.SINGLETON)
        }
    }
}
