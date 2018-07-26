package io.rafflethor.security

import javax.inject.Inject
import java.util.concurrent.CompletableFuture
import gql.ratpack.exec.Futures
import graphql.schema.DataFetchingEnvironment

class ServiceImpl implements Service {

    @Inject
    CryptoService cryptoService

    @Inject
    Repository securityRepository

    @Override
    CompletableFuture<User> login(DataFetchingEnvironment env) {
        UserCredentials credentials = new UserCredentials(env.arguments.input.subMap(FIELDS))

        return Futures
            .blocking { credentials }
            .applyThen(this.&transformPassword)
            .applyThen(securityRepository.&login)
    }

    private UserCredentials transformPassword(UserCredentials source) {
        return source.copyWith(password: cryptoService.hash(source.password))
    }
}
