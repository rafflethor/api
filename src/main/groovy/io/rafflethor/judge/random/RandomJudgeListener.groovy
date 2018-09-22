package io.rafflethor.judge.random

import com.google.common.eventbus.EventBus

import javax.inject.Inject
import io.rafflethor.judge.Judge

/**
 * @since 0.1.0
 */
class RandomJudgeListener implements Listener<Map> {

    @Inject
    RandomJudge judge

    @Inject
    EventBus eventBus

    @Override
    void listen(Map map) {
        eventBus.post(result(judge.getWinners(map.id)))
    }

    private Map result(String result) {
        return [result: result]
    }
}
