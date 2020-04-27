package org.levelup.threads.counter;

public class Counter {

    protected int counter;

    public void incrementCounter() {

        synchronized (this) {
            counter++;
        }

    }

    public synchronized int getCounter () {
        return counter;
    }
}
