package io.rafflethor.raffle.test

import java.util.concurrent.CompletionStage

import ratpack.exec.Promise

/**
 * Utility functions to deal with Ratpack's execution model when testing
 *
 * @since 0.1.0
 */
class Exec {

    /**
     * Converts a {@link CompletionStage} to a {@link Promise}
     *
     * @param stage a
     * @return a {@link Promise} that yields the same type the {@link CompletionStage} did
     */
    static <T> Promise<T> promise(CompletionStage<T> stage) {
        Promise.async({ downstream ->
            downstream.accept(stage)
        })
    }
}
