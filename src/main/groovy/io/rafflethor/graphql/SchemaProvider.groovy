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
            scalar(Utils.Payload)
            scalar(Utils.Moment)

            byResource('schema/Types.graphql') {
                mapType('Raffle') {
                    typeResolver(Utils.raffleTypeResolver())
                }
                mapType('TwitterRaffle') {
                    link('winners', raffleService.&findAllRaffleWinners)
                    link('hashtag', raffleService.&extractHashtag)
                    link('organization', organizationService.&byRaffle)
                }
                mapType('RandomListRaffle') {
                    link('winners', raffleService.&findAllRaffleWinners)
                    link('organization', organizationService.&byRaffle)
                }
                mapType('Organization') {
                    link('raffles', organizationService.&findAllRaffles)
                }
            }

            byResource('schema/Inputs.graphql')

            byResource('schema/Queries.graphql') {
                mapType('Queries') {
                    link('listAllRaffles', raffleService.&listAllRafflesByUser)
                    link('listAllOrganizations', organizationService.&listAll)
                    link('organization', organizationService.&get)
                    link('pickWinners', raffleService.&pickWinners)
                    link('raffle', raffleService.&findById)
                    link('checkRaffleResult', raffleService.&checkRaffleResult)
                }
            }

            byResource('schema/Mutations.graphql') {
                mapType('Mutations') {
                    link('saveOrganization', organizationService.&save)
                    link('deleteOrganization', organizationService.&delete)
                    link('eventRegistry', raffleService.&raffleRegistration)
                    link('saveRaffle', raffleService.&save)
                    link('startRaffle', raffleService.&startRaffle)
                    link('deleteRaffle', raffleService.&delete)
                    link('updateRaffle', raffleService.&update)
                }
            }

            byResource('schema/Schema.graphql')
        }

        return schema
    }
}
