package io.rafflethor.event

/**
 * @since 0.1.0
 */
interface EventRepository {

    static final List<String> FIELDS = ['id', 'name', 'description']

    /**
     * @param max maximum number of results
     * @param offset the offset of the result
     * @return a list of {@link Event} instances
     * @since 0.1.0
     */
    List<Event> listAll(Integer max, Integer offset)


    Event save(Event event)
}
