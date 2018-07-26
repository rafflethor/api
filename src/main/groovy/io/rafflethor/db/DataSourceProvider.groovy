package io.rafflethor.db

import com.google.inject.Provider
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.rafflethor.db.DatabaseConfig

import javax.inject.Inject
import javax.sql.DataSource

class DataSourceProvider implements Provider<DataSource> {

    @Inject
    DatabaseConfig config

    @Override
    DataSource get() {
        def hikariConfig = new HikariConfig()

        hikariConfig.driverClassName = config.driverClassName
        hikariConfig.jdbcUrl = config.url
        hikariConfig.username = config.username
        hikariConfig.password = config.password

        return new HikariDataSource(hikariConfig)
    }
}
