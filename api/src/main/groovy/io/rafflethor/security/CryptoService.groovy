package io.rafflethor.security

import javax.inject.Inject
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT

/**
 * Crytographic functions
 *
 * @since 0.1.0
 */
class CryptoService {

    final String ISSUER = 'rafflethor'

    /**
     * Configured algorithm to be used in cryptographic procedures
     *
     * @since 0.1.0
     */
    @Inject
    AlgorithmProvider algorithmProvider

    /**
     * Creates a new token using the provided claim and the configured
     * algorithm
     *
     * @param user
     * @return a new token
     * @since 0.1.0
     */
    String createToken(User user) {
        Algorithm algorithm = algorithmProvider.get()

        return JWT
            .create()
            .withIssuer(ISSUER)
            .withSubject(user.username)
            .withClaim("username", user.username)
            .withIssuedAt(new Date())
            .sign(algorithm)
    }

    /**
     * Checks whether a given token is valid or not
     *
     * @param token the token to be validated
     * @return an instanceof {@link DecodedJWT}
     * @since 0.1.0
     */
    DecodedJWT verifyToken(String token) {
        Algorithm algorithm = algorithmProvider.get()

        return JWT
            .require(algorithm)
            .withIssuer(ISSUER)
            .build()
            .verify(token)
    }

    /**
     * Hashes a given text using SHA-256 as hashing algorithm
     *
     * @param text the text we would like to hash
     * @since 0.1.0
     */
    String hash(String text) {
        return Optional
            .ofNullable(text)
            .map(this.&doHash)
            .orElse(text)
    }

    private doHash(String text) {
        return MessageDigest
            .getInstance("SHA-256")
            .digest(text.getBytes(StandardCharsets.UTF_8))
            .encodeHex()
            .toString()
    }
}
