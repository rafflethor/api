package io.rafflethor.init

interface FlywayAwareConfig {

    static class FlywayConfig {
        String[] schemas
        String[] migrations
    }

    FlywayConfig getFlyway()
}
