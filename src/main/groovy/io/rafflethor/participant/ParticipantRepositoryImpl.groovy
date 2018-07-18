package io.rafflethor.participant

import javax.inject.Inject
import groovy.sql.Sql
import java.security.MessageDigest

class ParticipantRepositoryImpl implements ParticipantRepository {

    @Inject
    Sql sql

    @Override
    Map<String,?> registerUser(UUID raffleId, String email) {
        Optional<Map> participant = Optional
            .ofNullable(email)
            .map({ String mail -> sql.firstRow('SELECT * FROM participants WHERE email = ?', mail) })

        if (participant.isPresent()) {
            return participant.get()
        }

        UUID uuid = UUID.randomUUID()
        String hash = generateMD5(email ?: new Date().time.toString())

        sql.executeInsert('INSERT INTO participants (id, email, hash, raffleId) VALUES (?, ?, ?, ?)',
                          uuid,
                          email,
                          hash,
                          raffleId)

        return [
            id: uuid,
            email: email,
            hash: hash,
            raffleId: raffleId
        ]
    }



    String generateMD5(String s){
        MessageDigest
            .getInstance("MD5")
            .digest(s.bytes)
            .encodeHex()
            .toString()
    }
}
