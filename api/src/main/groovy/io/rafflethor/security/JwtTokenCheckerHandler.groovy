package io.rafflethor.security

import static ratpack.jackson.Jackson.json

import javax.inject.Inject
import java.util.function.Consumer
import ratpack.handling.Context
import ratpack.handling.Handler
import ratpack.registry.Registry
import com.auth0.jwt.interfaces.DecodedJWT

/**
 * Handler responsible for adding an authenticated user credentials in
 * {@link Context} if there're any
 *
 * @since 1.0.6
 */
public class JwtTokenCheckerHandler implements Handler {

    /**
     * @since 1.0.6
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
        String token = ctx
            .header('Authorization')
            .map(this.&extractToken)
            .orElse(ctx.request.queryParams.token)

        Optional<User> user = Optional
            .ofNullable(token)
            .map(this.&verify)
            .map(this.&buildUser)

        if (user.isPresent()) {
            ctx.request.add(user.get())
        }

        ctx.next()
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
