package org.levelup.threads.stop;

public class ThreadStop extends Thread {
    private boolean shutdown;

    @Override
    public void run() {
        while (!shutdown) {
            System.out.println("I'm working...");

        }
    }

    public void shutdown() {
        shutdown = true;
    }
}
