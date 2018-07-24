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
        Raffle raffle = sql
            .rows("select * from raffles where id = :id", id: id)
            .collect(this.&toRaffle)
            .find()

        return raffle
    }

    @Override
    Raffle save(Raffle raffle) {
        UUID uuid = Utils.generateUUID()
        raffle.id = uuid

        sql.executeInsert("""
          INSERT INTO raffles
            (id, name, type, noWinners, organizationId)
          VALUES
            (?, ?, ?, ?, ?)
        """, raffle.id, raffle.name, raffle.type, raffle.noWinners, raffle.organizationId)

        return raffle
    }

    private static Raffle toRaffle(GroovyRowResult row) {
        if (!row) {
            return null
        }

        String pgObject = row['payload']?.value
        Map payload = pgObject ?
            new groovy.json.JsonSlurper().parseText(pgObject) :
            [:]

        Raffle raffle =  new Raffle(row.subMap(FIELDS))

        raffle.payload = payload
        return raffle
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

    Raffle markRaffleWaiting(UUID id) {
        sql.executeUpdate("UPDATE raffles SET status = 'WAITING' WHERE id = ?", id)

        return findById(id)
    }

    Raffle markRaffleLive(UUID id) {
        sql.executeUpdate("UPDATE raffles SET status = 'LIVE' WHERE id = ?", id)

        return findById(id)
    }

    Raffle markRaffleFinished(UUID id) {
        sql.executeUpdate("UPDATE raffles SET status = 'FINISHED' WHERE id = ?", id)

        return findById(id)
    }

    Raffle findWaitingRaffle() {
        return toRaffle(sql.firstRow("SELECT * FROM raffles WHERE status = 'WAITING'"))
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

    List<Map> findAllRandomWinners(Raffle raffle) {
        List<Map> participants =  sql
            .rows("SELECT * FROM participants WHERE raffleId = ?", raffle.id)

        Collections.shuffle(participants)
        List<Map> winners = participants.take(raffle.noWinners)

        saveWinners(raffle, winners)
        return winners
    }

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

    Boolean delete(UUID id) {
        Integer deletedRows = 0

        sql.withTransaction {
            deletedRows = sql.executeUpdate("DELETE FROM raffles WHERE id = ?", id)
        }

        return deletedRows == 1
    }

    Raffle update(Raffle raffle) {
        Map<String, ?> params = [
            name: raffle.name,
            noWinners: raffle.noWinners,
            id: raffle.id
        ]

        sql.withTransaction {
            sql.executeUpdate("""
              UPDATE raffles SET
                 name = :name,
                 noWinners = :noWinners
              WHERE id = :id
            """, params)
        }

        return raffle
    }
}
