package io.rafflethor.raffle.organization

import javax.inject.Inject
import groovy.sql.GroovyRowResult
import groovy.sql.Sql

import io.rafflethor.db.Utils

class OrganizationRepositoryImpl implements OrganizationRepository {

    @Inject
    Sql sql

    @Override
    List<Organization> listAll(Integer max, Integer offset) {
        return sql
            .rows('SELECT * FROM organizations ORDER BY createdAt DESC', offset, max)
            .collect(this.&toOrganization)
    }

    private Organization toOrganization(GroovyRowResult result) {
        return new Organization(result.subMap(FIELDS))
    }

    Organization save(Organization event) {
        UUID uuid = Utils.generateUUID()

        sql.executeInsert("INSERT INTO organizations (id, name, description) VALUES (?, ?, ?)",
                          [uuid, event.name, event.description])

        event.id = uuid
        return event
    }
}
