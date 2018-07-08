package io.rafflethor.graphql

import graphql.TypeResolutionEnvironment
import graphql.schema.TypeResolver

import io.rafflethor.raffle.Raffle

class Utils {

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
}
