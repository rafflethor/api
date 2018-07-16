package io.rafflethor.raffle

import javax.inject.Inject
import java.sql.Timestamp
import groovy.sql.BatchingStatementWrapper
import groovy.sql.GroovyRowResult
import groovy.sql.Sql

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
    void saveBatch(List<Raffle> raffles) {
        sql.withBatch(10) { BatchingStatementWrapper stmt ->
            raffles.each { Raffle raffle ->
                stmt.addBatch(buildRaffleQuery(raffle))
            }
        }
    }

    private static String buildRaffleQuery(Raffle raffle) {
        String since = raffle.since.format('yyyy-MM-dd hh:mm:ss')
        String until = raffle.until.format('yyyy-MM-dd hh:mm:ss')

        return """
          INSERT INTO raffles (id, name, noWinners, payload, since, until)
          VALUES (
          '${raffle.id}', '${raffle.name}', ${raffle.noWinners}, '${raffle.hashTag}', '${since}', '${until}'
          )
        """
    }

    private static Raffle toRaffle(GroovyRowResult row) {
        String pgObject = row['payload'].value
        Map payload = new groovy.json.JsonSlurper().parseText(pgObject)
        Raffle raffle =  new Raffle(row.subMap(FIELDS))

        raffle.payload = payload
        return raffle
    }
}
