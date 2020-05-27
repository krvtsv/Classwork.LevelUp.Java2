package org.levelup.threads.queue;

import lombok.SneakyThrows;

import java.util.LinkedList;
import java.util.Queue;

public class ThreadSafeQueue<T> {

    private final Queue<T> queue;
    private final int maxSize;
    private final Object notEmpty = new Object();//mutex
    private final Object notFull = new Object();//mutex

    public ThreadSafeQueue(int maxSize) {
        this.maxSize = maxSize;
        this.queue = new LinkedList<>();
    }

    @SneakyThrows
    public void put(T task) {
        synchronized (notFull) {
            while (queue.size() == maxSize) {
                // Thread.sleep(1000);
                System.err.println("Producer: wait");
                notFull.wait();
                System.err.println("Producer: wake up");
            }
        }

        synchronized (notEmpty) {
            queue.add(task);
            notEmpty.notifyAll();
        }
    }

    @SneakyThrows
    public T take() {
        synchronized (notEmpty) {
            while (queue.isEmpty()) {
                System.err.println("Consumer: wait");
                notEmpty.wait(); // IllegalMonitorStateException
                System.err.println("Consumer: wake up");
            }
        }

        synchronized (notFull) {
            T task = queue.remove();
            notFull.notifyAll();
            return task;
        }
    }
}