package io.rafflethor.security

class SecurityConfig {
    String secret
    String algorithm
    AuthorizationConfig authorization

 /**
     * Authorization properties
     *
     * @since 0.1.0
     */
    static class AuthorizationConfig {
        /**
         * Authorization mappings
         * <code>
         *     mappings:
         *       - Queries/randomCookie: USER,COOKIES
         * <code>
         *
         * @since 0.1.0
         */
        Map<String, String> mappings

        Boolean schema

        Boolean allowPartials
    }
}
