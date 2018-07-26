package io.rafflethor.config

import io.rafflethor.init.FlywayAwareConfig
import io.rafflethor.security.SecurityAwareConfig

class Config implements FlywayAwareConfig, SecurityAwareConfig {

    static class Database {
        String url
        String username
        String password
        String driverClassName
    }

    static class Twitter {
        String consumerKey
        String consumerSecret
        String accessToken
        String accessTokenSecret
    }

    Database database

    Twitter twitter

    FlywayAwareConfig.FlywayConfig flyway

    SecurityAwareConfig.SecurityConfig security
}
