import static ratpack.groovy.Groovy.ratpack

import gql.ratpack.GraphQLHandler
import gql.ratpack.GraphQLModule
import gql.ratpack.GraphiQLHandler
import io.rafflethor.cors.CorsHandler
import io.rafflethor.config.Config
import io.rafflethor.db.DataSourceModule
import io.rafflethor.graphql.GraphQLExtraModule
import io.rafflethor.init.InitModule
import io.rafflethor.raffle.RaffleModule
import io.rafflethor.raffle.twitter.TwitterModule
import io.rafflethor.event.EventModule
import ratpack.groovy.sql.SqlModule
import ratpack.server.ServerConfigBuilder

ratpack {
    serverConfig { ServerConfigBuilder config ->
        config
            .yaml("raffle.yml")
            .require("", Config)
    }

    bindings {
        module InitModule
        module SqlModule
        module DataSourceModule
        module GraphQLModule
        module GraphQLExtraModule
        module TwitterModule
        module RaffleModule
        module EventModule
    }

    handlers {
        all(new CorsHandler())
        //all(authenticator(new IndirectBasicAuthClient(registry.get(Authenticator))))

        prefix('graphql') {
            //all(requireAuth(IndirectBasicAuthClient))

            post("", GraphQLHandler)
            get('browser', GraphiQLHandler)
        }
    }
}
