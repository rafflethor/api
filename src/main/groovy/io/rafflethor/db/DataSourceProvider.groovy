package io.rafflethor.db

import com.google.inject.Provider
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.rafflethor.config.Config

import javax.inject.Inject
import javax.sql.DataSource

class DataSourceProvider implements Provider<DataSource> {

    @Inject
    Config config

    @Override
    DataSource get() {
        def hikariConfig = new HikariConfig()

        hikariConfig.driverClassName = config.database.driverClassName
        hikariConfig.jdbcUrl = config.database.url
        hikariConfig.username = config.database.username
        hikariConfig.password = config.database.password

        return new HikariDataSource(hikariConfig)
    }
}
