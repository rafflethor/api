package io.rafflethor.raffle

import static io.rafflethor.db.Utils.toTimestamp
import static groovy.json.JsonOutput.toJson
import static groovy.json.JsonOutput.unescaped

import javax.inject.Inject
import java.sql.Timestamp
import java.sql.Connection

import groovy.sql.Sql
import groovy.sql.BatchingStatementWrapper
import groovy.sql.GroovyRowResult
import groovy.json.JsonOutput

import io.rafflethor.db.Utils
import io.rafflethor.util.Pagination
import io.rafflethor.security.User

/**
 * Repository to get raffles of twitter nature
 *
 * @since 0.1.0
 */
class RepositoryImpl implements Repository {

    @Inject
    Sql sql

    @Override
    List<Raffle> listAll(Pagination pagination, User user) {
        String query = '''
          SELECT * FROM
            raffles
          WHERE
            createdBy = ?
          ORDER BY
            createdAt DESC
        '''

        return sql
            .rows(query, [user.id], pagination.offset, pagination.max)
            .collect(Raffles.&toRaffle)
    }

    @Override
    Raffle findById(UUID id, User user) {
        Raffle raffle = sql
            .rows("select * from raffles where id = :id and createdBy = :createdBy", id: id, createdBy: user.id)
            .collect(Raffles.&toRaffle)
            .find()

        return raffle
    }

    @Override
    Raffle upsert(Raffle raffle, User user) {
        raffle.id = raffle.id ?: Utils.generateUUID()

        List<GroovyRowResult> rows = sql.executeInsert('''
          INSERT INTO raffles
            (id,
             name,
             type,
             noWinners,
             preventPreviousWinners,
             organizationId,
             since,
             until,
             payload,
             createdBy)
          VALUES
            (:id,
             :name,
             :type,
             :noWinners,
             true,
             :organizationId,
             :since,
             :until,
             :payload::JSON,
             :createdBy)
          ON CONFLICT (id) DO UPDATE SET
             name = :name,
             type = :type,
             noWinners = :noWinners,
             since = :since,
             until = :until,
             payload = :payload::JSON
          WHERE
             raffles.createdBy = :createdBy
          RETURNING id, name, type, noWinners, preventPreviousWinners, organizationId, since, until, payload, status
        ''', [
                id: raffle.id,
                name: raffle.name,
                type: raffle.type,
                noWinners: raffle.noWinners,
                preventPreviousWinners: raffle.preventPreviousWinners,
                organizationId: raffle.organizationId,
                since: toTimestamp(raffle.since),
                until: toTimestamp(raffle.until),
                payload: raffle.payload,
                createdBy: user.id
            ])

        return rows
            .grep()
            .collect(Raffles.&toRaffle)
            .find()
    }

    @Override
    Raffle findRaffleFromSpot(String spotId) {
        UUID uuid = sql
            .firstRow("SELECT raffleId  as id FROM raffle_spot WHERE id = ?", spotId)
            .id

        return Optional
            .ofNullable(uuid)
            .map(this.&findById)
            .orElse(null)
    }

    @Override
    Raffle markRaffleWaiting(UUID id, User user) {
        sql.executeUpdate("UPDATE raffles SET status = 'WAITING' WHERE id = ? and createdBy = ?", id, user.id)

        return findById(id)
    }

    @Override
    Raffle markRaffleLive(UUID id) {
        sql.executeUpdate("UPDATE raffles SET status = 'LIVE' WHERE id = ?", id)

        return findById(id)
    }

    @Override
    Raffle markRaffleFinished(UUID id) {
        sql.executeUpdate("UPDATE raffles SET status = 'FINISHED' WHERE id = ?", id)

        return findById(id)
    }

    @Override
    Raffle findWaitingRaffle() {
        return Raffles.toRaffle(sql.firstRow("SELECT * FROM raffles WHERE status = 'WAITING'"))
    }

    List<Map> saveWinners(Raffle raffle, List<Map> participants) {
        sql.withTransaction {
            participants.each { p ->
                UUID uuid = Utils.generateUUID()

                sql.executeInsert(
                    "INSERT INTO winners (id, participantId, raffleId) VALUES (?, ?, ?)",
                    uuid,
                    p.id,
                    p.raffleId)
            }
        }

        return sql.rows("SELECT * FROM winners WHERE raffleId = ?", raffle.id)
    }

    @Override
    List<Map> findAllRandomWinners(Raffle raffle) {
        List<Map> participants =  sql
            .rows("SELECT * FROM participants WHERE raffleId = ?", raffle.id)

        Collections.shuffle(participants)
        List<Map> winners = participants.take(raffle.noWinners)

        saveWinners(raffle, winners)
        return winners
    }

    @Override
    Map checkRaffleResult(UUID id, String userHash) {
        Raffle raffle = findById(id)
        List<Map> winners = sql.rows("""
          SELECT
            p.hash,
            p.email
          FROM winners w JOIN participants p ON
            w.participantId = p.id
          WHERE w.raffleId = ?
        """, id)

        Boolean didIWin = userHash in winners*.hash

        return [
            winners: winners,
            didIWin: didIWin,
            raffle: raffle
        ]
    }

    @Override
    Boolean delete(UUID id, User user) {
        Integer deletedRows = 0

        sql.withTransaction {
            deletedRows = sql.executeUpdate("DELETE FROM raffles WHERE id = ? AND createdBy = ?", id, user.id)
        }

        return deletedRows == 1
    }

    static <T> T executeTx(Sql sql, Closure<T> expression) {
        final T result = null

        sql.withTransaction { Connection connection ->
            result = expression(new Sql(connection))
        }

        return result
    }

    private Map<String,?> params(Raffle raffle) {
        return raffle
            .properties
            .subMap(Raffles.FIELDS)
    }

    @Override
    List<Winner> findAllWinners(Raffle raffle) {
        String query = '''
          SELECT
            w.raffleId,
            w.ordering,
            w.id,
            p.social,
            p.nick,
            w.createdAt
          FROM winners w JOIN participants p ON
            w.participantId = p.id
          WHERE w.raffleId = ?
        '''

        return sql
            .rows(query, raffle.id)
            .collect(this.&toWinner)
    }

    private Winner toWinner(GroovyRowResult row) {
        List<String> fields = [
            'id',
            'ordering',
            'social',
            'nick',
            'raffleId',
            'createdAt'
        ]

        return new Winner(row.subMap(fields))
    }
}
