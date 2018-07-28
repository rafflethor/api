package io.rafflethor.security

import static ratpack.jackson.Jackson.json

import javax.inject.Inject
import java.util.function.Consumer
import ratpack.handling.Context
import ratpack.handling.Handler
import ratpack.registry.Registry
import com.auth0.jwt.interfaces.DecodedJWT

/**
 * @since 0.1.0
 */
public class JwtTokenCheckerHandler implements Handler {

    /**
     * @since 0.1.0
     */
    @Inject
    CryptoService cryptoService

    @Inject
    Repository repository

    /**
     * {@inheritDoc}
     */
    @Override
    public void handle(Context ctx) {
        Optional<User> user = ctx.header('Authorization')
            .map(this.&extractToken)
            .map(this.&verify)
            .map(this.&buildUser)

        if (user.isPresent()) {
            ctx.request.add(user.get())
            ctx.next()
        } else {
            ctx.render(json(buildAuthErrorMessage()))
        }
    }

    private DecodedJWT verify(String token) {
        try {
            return cryptoService.verifyToken(token)
        } catch(e) {
            return null
        }
    }

    private Map<String,?> buildAuthErrorMessage() {
        return [
            data: null,
            errors: [
                code: "error.security.auth",
                message: "Bad credentials"
            ]
        ]
    }

    private String extractToken(String header) {
        return header - "JWT "
    }

    private User buildUser(DecodedJWT jwt) {
        String username = jwt.getClaim("sub").asString()

        return repository.findByUsername(username)
    }
}
