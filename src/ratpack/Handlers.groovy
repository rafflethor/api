import static ratpack.groovy.Groovy.ratpack

import gql.ratpack.GraphQLHandler
import gql.ratpack.GraphiQLHandler

import io.rafflethor.cors.CorsHandler
import io.rafflethor.live.LiveHandler

ratpack {
    handlers {
        all(new CorsHandler())
        //all(authenticator(new IndirectBasicAuthClient(registry.get(Authenticator))))

        prefix('graphql') {
            //all(requireAuth(IndirectBasicAuthClient))

            post("", GraphQLHandler)
            get('browser', GraphiQLHandler)
        }

        prefix('raffle') {
            get('/:id/:hash', LiveHandler)
        }
    }
}
