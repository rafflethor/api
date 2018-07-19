package io.rafflethor.graphql

import javax.inject.Inject
import javax.inject.Provider

import gql.DSL
import graphql.schema.GraphQLSchema

import io.rafflethor.raffle.Service as RaffleService
import io.rafflethor.organization.Service as OrganizationService

/**
 * Provider responsible for creating an instance of {@link GraphQLSchema}
 *
 * @since 0.1.0
 */
class SchemaProvider implements Provider<GraphQLSchema> {

    @Inject
    RaffleService raffleService

    @Inject
    OrganizationService organizationService

    @Override
    GraphQLSchema get() {
        GraphQLSchema schema = DSL.mergeSchemas {
            byResource('schema/Raffle.graphql') {

                mapType('Raffle') {
                    typeResolver(Utils.raffleTypeResolver())
                }

                mapType('TwitterRaffle') {
                    link('organization', organizationService.&byRaffle)
                }

                mapType('RandomListRaffle') {
                    link('organization', organizationService.&byRaffle)
                }

                mapType('Queries') {
                    link('listAllRaffles', raffleService.&listAllRafflesByUser)
                    link('listAllOrganizations', organizationService.&listAll)
                    link('organization', organizationService.&get)
                    link('pickWinners', raffleService.&pickWinners)
                    link('raffle', raffleService.&findById)
                    link('checkRaffleResult', raffleService.&checkRaffleResult)
                }

                mapType('Mutations') {
                    link('saveOrganization', organizationService.&save)
                    link('saveRaffle', raffleService.&save)
                    link('eventRegistry', raffleService.&raffleRegistration)
                    link('startRaffle', raffleService.&startRaffle)
                }
            }
        }

        return schema
    }
}
