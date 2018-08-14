package io.rafflethor.participant

import javax.inject.Inject
import groovy.sql.Sql
import java.security.MessageDigest

class RepositoryImpl implements Repository {

    @Inject
    Sql sql

    @Override
    Map<String,?> registerUser(UUID raffleId, String email) {
        return Optional
            .ofNullable(email)
            .map(this.&findParticipantByEmail) // It should be byEmailAndRaffle
            .orElse(saveNewParticipant(raffleId, email))
    }

    @Override
    Map saveNewParticipant(UUID raffleId, String email) {
        String query = '''
          INSERT INTO participants
            (id, email, hash, raffleId)
          VALUES
            (?, ?, ?, ?)
        '''

        UUID uuid = UUID.randomUUID()
        String hasheable = email ?: new Date().time.toString()
        String hash = generateMD5(hasheable)

        sql.executeInsert(query,
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

    @Override
    Map findParticipantByEmail(String email) {
        return sql.firstRow('SELECT * FROM participants WHERE email = ?', email)
    }

    String generateMD5(String s){
        MessageDigest
            .getInstance("MD5")
            .digest(s.bytes)
            .encodeHex()
            .toString()
    }
}
