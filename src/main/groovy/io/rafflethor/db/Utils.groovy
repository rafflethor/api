package io.rafflethor.db

import java.sql.Timestamp
import java.time.LocalDateTime

class Utils {

    static final UUID generateUUID() {
        return UUID.randomUUID()
    }

    static Timestamp toTimestamp(LocalDateTime dateTime) {
        return Optional
            .ofNullable(dateTime)
            .map(Timestamp.&valueOf)
            .orElse(null)
    }
}
