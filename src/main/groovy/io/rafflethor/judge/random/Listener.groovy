package io.rafflethor.judge.random

import com.google.common.eventbus.EventBus

interface Listener<T> {

    void listen(T event)

    void setEventBus(EventBus eventBus)
}
