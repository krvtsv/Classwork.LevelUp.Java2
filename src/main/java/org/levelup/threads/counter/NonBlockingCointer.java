package org.levelup.threads.counter;

import java.util.concurrent.atomic.AtomicInteger;

public class NonBlockingCointer extends Counter {
    private AtomicInteger atomicCounter = new AtomicInteger (0);

    @Override
    public void incrementCounter () {
        atomicCounter.incrementAndGet ();
    }

    @Override
    public synchronized int getCounter () {
        return atomicCounter.get ();
    }
}
