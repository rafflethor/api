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

}
