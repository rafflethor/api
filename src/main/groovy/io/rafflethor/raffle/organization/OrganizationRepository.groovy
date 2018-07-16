package io.rafflethor.raffle.organization

/**
 * @since 0.1.0
 */
interface OrganizationRepository {

    static final List<String> FIELDS = ['id', 'name', 'description']

    /**
     * @param max maximum number of results
     * @param offset the offset of the result
     * @return a list of {@link Organization} instances
     * @since 0.1.0
     */
    List<Organization> listAll(Integer max, Integer offset)


    Organization save(Organization event)
}
