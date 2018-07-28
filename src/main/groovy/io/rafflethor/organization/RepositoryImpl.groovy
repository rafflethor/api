package io.rafflethor.organization

import javax.inject.Inject
import groovy.sql.GroovyRowResult
import groovy.sql.Sql
import groovy.util.logging.Slf4j

import io.rafflethor.db.Utils
import io.rafflethor.raffle.Raffle
import io.rafflethor.security.User

@Slf4j
class RepositoryImpl implements Repository {

    @Inject
    Sql sql

    @Override
    List<Organization> listAllByUser(User user, Integer max, Integer offset) {
        String query = '''
          SELECT * FROM
            organizations
          WHERE
            createdBy = ?
          ORDER BY
            createdAt DESC
        '''

        return sql
            .rows(query, [user.id], offset, max)
            .collect(this.&toOrganization)
    }

    private Organization toOrganization(GroovyRowResult result) {
        return new Organization(result.subMap(FIELDS))
    }

    @Override
    Organization get(UUID uuid) {
        GroovyRowResult row =  sql
            .firstRow('SELECT * FROM organizations WHERE id = ?', uuid)

        if (!row) {
            return
        }

        Organization organization = this.toOrganization(row)

        List<Raffle> rows = sql
            .rows('SELECT * FROM raffles WHERE organizationId = ?', organization.id)
            .collect(this.&toRaffle)

        organization.raffles = rows

        return organization
    }

    private static Raffle toRaffle(GroovyRowResult row) {
        List<String> RAFFLE_FIELDS = ['id', 'name', 'noWinners', 'type', 'until', 'since']
        Raffle raffle =  new Raffle(row.subMap(RAFFLE_FIELDS))

        String pgObject = row['payload']?.value
        Map payload = pgObject ?
            new groovy.json.JsonSlurper().parseText(pgObject) :
            [:]

        raffle.payload = payload
        return raffle
    }

    @Override
    Organization save(Organization event, User user) {
        UUID uuid = Utils.generateUUID()

        sql.executeInsert("INSERT INTO organizations (id, name, description, createdBy) VALUES (?, ?, ?, ?)",
                          [uuid, event.name, event.description, user.id])

        event.id = uuid
        return event
    }

    Organization byRaffleId(UUID raffleId) {
        GroovyRowResult row = sql.firstRow("""
          SELECT * FROM
            organizations
          WHERE
            id = (SELECT organizationId FROM raffles WHERE id = ?)
        """, raffleId)

        return toOrganization(row)
    }

    Boolean delete(UUID id) {
        Integer deletedRows = 0

        sql.withTransaction { connection ->
            deletedRows = sql
                .executeUpdate("DELETE FROM organizations WHERE ID = ?", id)
        }

        log.info "deleted ${deletedRows}"

        return deletedRows == 1
    }
}
