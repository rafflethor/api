import static ratpack.groovy.Groovy.ratpack

import gql.ratpack.GraphQLHandler
import gql.ratpack.GraphQLModule
import gql.ratpack.GraphiQLHandler

import ratpack.groovy.sql.SqlModule
import ratpack.server.ServerConfigBuilder

import io.rafflethor.cors.CorsHandler
import io.rafflethor.config.Config
import io.rafflethor.db.DataSourceModule
import io.rafflethor.graphql.GraphQLExtraModule
import io.rafflethor.init.InitModule
import io.rafflethor.eb.EventBusModule

import io.rafflethor.judge.twitter.Module as TwitterModule
import io.rafflethor.raffle.Module as RaffleModule
import io.rafflethor.organization.Module as OrganizationModule
import io.rafflethor.participant.Module as ParticipantModule
import io.rafflethor.live.Module as LiveModule
import io.rafflethor.live.LiveHandler

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
        module EventBusModule
        module RaffleModule
        module OrganizationModule
        module ParticipantModule
        module LiveModule
        module TwitterModule
    }

    handlers {
        all(new CorsHandler())
        //all(authenticator(new IndirectBasicAuthClient(registry.get(Authenticator))))

        prefix('graphql') {
            //all(requireAuth(IndirectBasicAuthClient))

            post("", GraphQLHandler)
            get('browser', GraphiQLHandler)
        }

        prefix('raffle/live') {
            get('', LiveHandler)
        }
    }
}
