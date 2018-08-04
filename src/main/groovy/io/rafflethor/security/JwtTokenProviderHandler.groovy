package io.rafflethor.security

import static ratpack.jackson.Jackson.json

import javax.inject.Inject

import ratpack.form.Form
import ratpack.handling.Handler
import ratpack.handling.Context

class JwtTokenProviderHandler implements Handler {

    @Inject
    Repository securityRepository

    @Inject
    CryptoService cryptoService

    @Override
    void handle(Context ctx) {
        ctx.parse(Map)
            .map(this.&extractUserCredentials)
            .map(this.&generateToken)
            .then({ Map token -> ctx.render(json(token))})
    }

    private User extractUserCredentials(Map form) {
        String username = form.get('username')
        String password = form.get('password')

        return securityRepository.login(username, cryptoService.hash(password))
    }

    private Map generateToken(User user) {
        return Optional
            .ofNullable(user)
            .map(this.&getPayload)
            .orElse(Collections.emptyMap())
    }

    private Map<String, ?> getPayload(User u) {
        return [
            username: u.username,
            token: cryptoService.createToken(u)
        ]
    }
}
