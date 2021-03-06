package io.rafflethor.organization

import ratpack.handling.Context
import graphql.schema.DataFetchingEnvironment
import io.rafflethor.raffle.Raffle
import io.rafflethor.security.User
import io.rafflethor.util.Pagination

class Selectors {

    static class ListAll {
        User user
        Pagination pagination
    }

    static class Save {
        Organization organization
        User user
    }

    static class Get {
        UUID id
        User user
    }

    static class ByRaffle {
        UUID id
        User user
    }

    static class Delete {
        UUID id
        User user
    }

    static class FindAllRaffles {
        UUID id
        User user
    }

    static ListAll listAll(DataFetchingEnvironment env) {
        return new ListAll(
            user: Selectors.getUser(env),
            pagination: new Pagination(
                max: env.arguments.max as Integer,
                offset: env.arguments.offset as Integer
            )
        )
    }

    static Save save(DataFetchingEnvironment env) {
        return new Save(
            user: getUser(env),
            organization: getOrganization(env)
        )
    }

    static Get get(DataFetchingEnvironment env) {
        return new Get(
            id: id(env),
            user: getUser(env)
        )
    }

    static ByRaffle byRaffle(DataFetchingEnvironment env) {
        Raffle raffle = env.getSource() as Raffle

        return new ByRaffle(
            id: raffle?.id,
            user: getUser(env)
        )
    }

    static Delete delete(DataFetchingEnvironment env) {
        return new Delete(
            id: id(env),
            user: getUser(env)
        )
    }

    static User getUser(DataFetchingEnvironment env) {
        Context context = env.context as Context

        return context.get(User)
    }

    static Organization getOrganization(DataFetchingEnvironment env) {
        return new Organization(env.arguments.organization.subMap(Repository.FIELDS))
    }

    static FindAllRaffles findAllRaffles(DataFetchingEnvironment env) {
        Organization organization = env.source as Organization
        User user = getUser(env)

        return new FindAllRaffles(
            id: organization?.id,
            user: user
        )
    }

    static UUID id(DataFetchingEnvironment env) {
        return UUID.fromString(env.arguments.id.toString())
    }
}
