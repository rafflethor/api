package io.rafflethor.graphql

import javax.inject.Inject
import javax.inject.Provider

import gql.DSL
import graphql.schema.GraphQLSchema

import io.rafflethor.raffle.RaffleService
import io.rafflethor.event.EventService

/**
 * Provider responsible for creating an instance of {@link GraphQLSchema}
 *
 * @since 0.1.0
 */
class SchemaProvider implements Provider<GraphQLSchema> {

    @Inject
    RaffleService raffleService

    @Inject
    EventService eventService

    @Override
    GraphQLSchema get() {
        GraphQLSchema schema = DSL.mergeSchemas {
            byResource('schema/Raffle.graphql') {

                mapType('Raffle') {
                    typeResolver(Utils.raffleTypeResolver())
                }

                mapType('Queries') {
                    link('listAllRaffles', raffleService.&listAllRafflesByUser)
                    link('listAllEvents', eventService.&listAll)
                    link('pickWinners', raffleService.&pickWinners)
                }

                mapType('Mutations') {
                    link('saveEvent', eventService.&save)
                    link('eventRegistry', raffleService.&raffleRegistration)
                }
            }
        }

        return schema
    }
}
