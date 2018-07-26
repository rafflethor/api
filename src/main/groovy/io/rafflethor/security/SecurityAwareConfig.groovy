package io.rafflethor.security

interface SecurityAwareConfig {

    static class SecurityConfig {
        String secret
        String algorithm
    }

    SecurityConfig getSecurity()
}
