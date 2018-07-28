package io.rafflethor.organization

import ratpack.handling.Context
import graphql.schema.DataFetchingEnvironment
import io.rafflethor.security.User

class Selectors {

    static class ListAll {
        User user
        Integer max
        Integer offset
    }

    static ListAll getListAll(DataFetchingEnvironment env) {
        return new ListAll(
            max: env.arguments.max as Integer,
            offset: env.arguments.offset as Integer,
            user: Selectors.getUser(env),
        )
    }

    static User getUser(DataFetchingEnvironment env) {
        Context context = env.context as Context

        return context.get(User)
    }

    static Organization getOrganization(DataFetchingEnvironment env) {
        return new Organization(env.arguments.organization.subMap(Repository.FIELDS))
    }

    static UUID getID(DataFetchingEnvironment env) {
        return UUID.fromString(env.arguments.id.toString())
    }
}
