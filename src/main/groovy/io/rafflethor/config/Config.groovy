package io.rafflethor.config

import io.rafflethor.init.FlywayAwareConfig

class Config implements FlywayAwareConfig {

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
}
