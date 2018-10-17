package io.rafflethor.raffle.test

import twitter4j.Query
import twitter4j.QueryResult
import twitter4j.RateLimitStatus
import twitter4j.Status

class DefaultQueryResult implements QueryResult {
    long sinceId
    long maxId
    int count
    int accessLevel
    double completedIn
    String refreshURL
    String query

    List<Status> tweets
    RateLimitStatus rateLimitStatus

    Query nextQuery() {

    }

    @Override
    boolean hasNext() {

    }
}
