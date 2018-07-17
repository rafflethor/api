package io.rafflethor.raffle

import javax.inject.Inject
import java.sql.Timestamp
import groovy.sql.BatchingStatementWrapper
import groovy.sql.GroovyRowResult
import groovy.sql.Sql

import io.rafflethor.db.Utils

/**
 * Repository to get raffles of twitter nature
 *
 * @since 0.1.0
 */
class RepositoryImpl implements Repository {

    @Inject
    Sql sql

    @Override
    List<Raffle> listAll(Integer max, Integer offset) {
        return sql
            .rows("select * from raffles ORDER BY createdAt DESC", offset, max)
            .collect(this.&toRaffle)
    }

    @Override
    Raffle findById(UUID id) {
        return sql
            .rows("select * from twitter_raffles where id = :id", id: id)
            .collect(this.&toRaffle)
            .find()
    }

    @Override
    Raffle save(Raffle raffle) {
        UUID uuid = Utils.generateUUID()
        raffle.id = uuid

        sql.executeInsert(buildRaffleQuery(raffle))

        return raffle
    }

    private static String buildRaffleQuery(Raffle raffle) {
        return """
          INSERT INTO raffles (id, name, type, noWinners)
          VALUES (
          '${raffle.id}', '${raffle.name}', '${raffle.type}', ${raffle.noWinners}
          )
        """
    }

    private static Raffle toRaffle(GroovyRowResult row) {
        String pgObject = row['payload']?.value
        Map payload = pgObject ?
            new groovy.json.JsonSlurper().parseText(pgObject) :
            [:]

        Raffle raffle =  new Raffle(row.subMap(FIELDS))

        raffle.payload = payload
        return raffle
    }
}
