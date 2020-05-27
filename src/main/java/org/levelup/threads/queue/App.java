package org.levelup.threads.queue;

public class App {
    public static void main(String[] args) {
        ReentrantThreadSafeQueue<String> queue = new ReentrantThreadSafeQueue<>(10);
        Runnable producer = () -> {
            for (int i = 0; i < 50; i++) {
                queue.put("String " + i + ", Thread: " + Thread.currentThread().getName());
                System.out.println(("String " + i + ", Thread: " + Thread.currentThread().getName()));
            }
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };


        Runnable consumer = () -> {
            while (true) {
                String result = queue.take();
                System.out.println("Consumer: " + result);

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }};

        new Thread(producer).start();
        new Thread(producer).start();
        new Thread(producer).start();

        new Thread(consumer).start();
        new Thread(consumer).start();
    }
}
