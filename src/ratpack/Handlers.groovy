import static ratpack.groovy.Groovy.ratpack

import gql.ratpack.GraphiQLHandler
import gql.ratpack.GraphQLHandler

import io.rafflethor.cors.CorsHandler
import io.rafflethor.live.LiveHandler
import io.rafflethor.security.JwtTokenProviderHandler
import io.rafflethor.security.JwtTokenCheckerHandler

ratpack {
    handlers {
        all(new CorsHandler())

        prefix('auth') {
            post('token', JwtTokenProviderHandler)
        }

        prefix('graphql') {
            post(JwtTokenCheckerHandler)
            post('', GraphQLHandler)
        }

        prefix('graphiql') {
            get('', GraphiQLHandler)
        }

        prefix('raffle') {
            get('/:id/:hash', LiveHandler)
        }
    }
}
