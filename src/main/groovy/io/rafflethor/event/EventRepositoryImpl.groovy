package io.rafflethor.event

import javax.inject.Inject
import groovy.sql.GroovyRowResult
import groovy.sql.Sql

import io.rafflethor.db.Utils

class EventRepositoryImpl implements EventRepository {

    @Inject
    Sql sql

    @Override
    List<Event> listAll(Integer max, Integer offset) {
        return sql
            .rows('SELECT * FROM events ORDER BY createdAt DESC', offset, max)
            .collect(this.&toEvent)
    }

    private Event toEvent(GroovyRowResult result) {
        return new Event(result.subMap(FIELDS))
    }

    Event save(Event event) {
        UUID uuid = Utils.generateUUID()

        sql.executeInsert("INSERT INTO events (id, name, description) VALUES (?, ?, ?)",
                          [uuid, event.name, event.description])

        event.id = uuid
        return event
    }
}
