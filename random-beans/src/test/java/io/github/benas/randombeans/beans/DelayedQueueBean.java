package io.github.benas.randombeans.beans;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayedQueueBean {

    private DelayQueue delayQueue;
    private DelayQueue<DummyDelayed> typedDelayQueue;

    public DelayQueue getDelayQueue() {
        return delayQueue;
    }

    public void setDelayQueue(DelayQueue delayQueue) {
        this.delayQueue = delayQueue;
    }

    public DelayQueue<DummyDelayed> getTypedDelayQueue() {
        return typedDelayQueue;
    }

    public void setTypedDelayQueue(DelayQueue<DummyDelayed> typedDelayQueue) {
        this.typedDelayQueue = typedDelayQueue;
    }

    class DummyDelayed implements Delayed {

        @Override
        public long getDelay(TimeUnit timeUnit) {
            return 0;
        }

        @Override
        public int compareTo(Delayed delayed) {
            return 0;
        }
    }
}
