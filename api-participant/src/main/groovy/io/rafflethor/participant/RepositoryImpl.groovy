package io.rafflethor.participant

import javax.inject.Inject
import groovy.sql.Sql
import java.security.MessageDigest

class RepositoryImpl implements Repository {

    @Inject
    Sql sql

    @Override
    Map<String,?> registerUser(UUID raffleId, String social) {
        return Optional
            .ofNullable(social)
            .map(this.&findParticipantBySocial)
            .orElse(saveNewParticipant(raffleId, social))
    }

    @Override
    Map saveNewParticipant(UUID raffleId, String social) {
        String query = '''
          INSERT INTO participants
            (id, social, hash, raffleId)
          VALUES
            (?, ?, ?, ?)
        '''

        UUID uuid = UUID.randomUUID()
        String hasheable = social ?: new Date().time.toString()
        String hash = generateMD5(hasheable)

        sql.executeInsert(query,
                          uuid,
                          social,
                          hash,
                          raffleId)

        return [
            id: uuid,
            social: social,
            hash: hash,
            raffleId: raffleId
        ]
    }

    @Override
    Map findParticipantBySocial(String social) {
        return sql.firstRow('SELECT * FROM participants WHERE social = ?', social)
    }

    String generateMD5(String s){
        MessageDigest
            .getInstance("MD5")
            .digest(s.bytes)
            .encodeHex()
            .toString()
    }
}
