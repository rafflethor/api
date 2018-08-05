package io.rafflethor.organization

import io.rafflethor.security.User
import io.rafflethor.raffle.Raffle
import io.rafflethor.util.Pagination

/**
 * @since 0.1.0
 */
interface Repository {

    static final List<String> FIELDS = ['id', 'name', 'description']

    /**
     * @param max maximum number of results
     * @param offset the offset of the result
     * @return a list of {@link Organization} instances
     * @since 0.1.0
     */
    List<Organization> listAll(Pagination pagination, User user)
    Organization get(UUID id, User user)
    Organization save(Organization event, User user)
    Organization byRaffleId(UUID raffleId, User user)
    Boolean delete(UUID id, User user)

    List<Raffle> findAllRaffles(UUID id, User user)
}
