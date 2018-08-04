package io.rafflethor.graphql

import gql.DSL
import java.time.LocalDateTime
import java.util.function.Function
import graphql.TypeResolutionEnvironment
import graphql.schema.TypeResolver
import graphql.schema.GraphQLScalarType

import io.rafflethor.raffle.Raffle

/**
 * Holds programatically created GraphQL types like type resolvers or
 * scalar implementations
 *
 * @since 0.1.0
 */
class Utils {

    /**
     * Creates a type resolver responsible to differenciate between
     * different types of raffles
     *
     * @return a {@link TypeResolver}
     * @since 0.1.0
     */
    static TypeResolver raffleTypeResolver() {
        return { TypeResolutionEnvironment env ->
            Raffle raffle = env.getObject() as Raffle

            switch (raffle.type) {
                case 'TWITTER':
                    return env.schema.getObjectType('TwitterRaffle')
                default:
                    return env.schema.getObjectType('RandomListRaffle')
            }
        } as TypeResolver
    }

    /**
     * The Payload scalar type is just a map that could hold any value
     *
     * @return a {@link GraphQLScalarType}
     * @since 0.1.0
     */
    static GraphQLScalarType Payload = DSL.scalar('Payload') {
        parseValue { Map payload -> return payload }
    }

    static GraphQLScalarType Moment = DSL.scalar('Moment') {
        serialize({ LocalDateTime moment -> moment?.toString() })
        parseValue({ String moment ->
            return moment ? LocalDateTime.parse(moment) : null
        })
        parseLiteral({ String moment ->
            return moment ? LocalDateTime.parse(moment) : null
        })
    }
}
