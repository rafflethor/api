import static ratpack.groovy.Groovy.ratpack

import gql.ratpack.GraphQLModule
import ratpack.groovy.sql.SqlModule
import ratpack.session.SessionModule
import io.rafflethor.init.InitModule
import io.rafflethor.eb.EventBusModule
import io.rafflethor.db.DataSourceModule
import io.rafflethor.graphql.GraphQLExtraModule
import io.rafflethor.judge.twitter.Module as TwitterModule
import io.rafflethor.raffle.Module as RaffleModule
import io.rafflethor.organization.Module as OrganizationModule
import io.rafflethor.participant.Module as ParticipantModule
import io.rafflethor.live.Module as LiveModule
import io.rafflethor.security.Module as SecurityModule
import org.pac4j.core.profile.UserProfile

/**
 * BINDINGS
 * --------
 *
 * - Guice modules
 *
 */
ratpack {
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
        module SessionModule
        module SecurityModule
    }
}
