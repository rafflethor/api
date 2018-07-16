package io.rafflethor.raffle.participant

import javax.inject.Inject
import groovy.sql.Sql
import java.security.MessageDigest

class ParticipantRepositoryImpl implements ParticipantRepository {

    @Inject
    Sql sql

    @Override
    Map<String,?> registerUser(UUID raffleId, String email) {
        Map participant = sql.firstRow('SELECT * FROM participant WHERE email = ?', email)

        if (participant) {
            return participant
        }

        UUID uuid = UUID.randomUUID()
        String hash = generateMD5(email)

        sql.executeInsert('INSERT INTO participant (id, email, hash, raffleId) VALUES (?, ?, ?, ?)',
                          uuid,
                          email,
                          hash,
                          raffleId)

        return [id: uuid, email: email, hash: hash, raffleId: raffleId]
    }



    String generateMD5(String s){
        MessageDigest
            .getInstance("MD5")
            .digest(s.bytes)
            .encodeHex()
            .toString()
    }
}
