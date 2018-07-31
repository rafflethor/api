package io.rafflethor.organization

import javax.inject.Inject
import groovy.sql.GroovyRowResult
import groovy.sql.Sql
import groovy.util.logging.Slf4j

import io.rafflethor.db.Utils
import io.rafflethor.raffle.Raffle
import io.rafflethor.security.User
import io.rafflethor.util.Pagination

@Slf4j
class RepositoryImpl implements Repository {

    @Inject
    Sql sql

    @Override
    List<Organization> listAll(Pagination pagination, User user) {
        String query = '''
          SELECT * FROM
            organizations
          WHERE
            createdBy = ?
          ORDER BY
            createdAt DESC
        '''

        return sql
            .rows(query, [user.id], pagination.offset, pagination.max)
            .collect(this.&toOrganization)
    }

    private Organization toOrganization(GroovyRowResult result) {
        return new Organization(result.subMap(FIELDS))
    }

    @Override
    Organization get(UUID uuid, User user) {
        GroovyRowResult row =  sql
            .firstRow('SELECT * FROM organizations WHERE id = ? AND createdBy = ?', uuid, user.id)

        if (!row) {
            return
        }

        Organization organization = this.toOrganization(row)

        List<Raffle> rows = sql
            .rows('SELECT * FROM raffles WHERE organizationId = ? AND createdBy = ?', organization.id, user.id)
            .collect(this.&toRaffle)

        organization.raffles = rows

        return organization
    }

    private static Raffle toRaffle(GroovyRowResult row) {
        List<String> RAFFLE_FIELDS = ['id', 'name', 'noWinners', 'status', 'type', 'until', 'since']
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
        String query = '''
          INSERT INTO organizations
            (id, name, description, createdBy)
          VALUES
            (?, ?, ?, ?)
        '''
        UUID uuid = Utils.generateUUID()
        List<?> params = [uuid, event.name, event.description, user.id]

        sql.executeInsert(query, params)
        event.id = uuid

        return event
    }

    @Override
    Organization byRaffleId(UUID raffleId, User user) {
        GroovyRowResult row = sql.firstRow("""
          SELECT * FROM
            organizations
          WHERE
            id = (SELECT organizationId FROM raffles WHERE id = ?) AND
            createdBy = ?
        """, raffleId, user.id)

        return toOrganization(row)
    }

    Boolean delete(UUID id, User user) {
        Integer deletedRows = 0

        sql.withTransaction { connection ->
            deletedRows = sql
                .executeUpdate("DELETE FROM organizations WHERE ID = ? AND createdBy = ?", id, user.id)
        }

        log.info "deleted ${deletedRows}"

        return deletedRows == 1
    }
}
