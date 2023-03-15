package org.example.lesson22_producer_consumer_part_1;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

public class Test {
    private final ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);

    public ArrayBlockingQueue<Integer> getQueue() {
        return queue;
    }

    public static void main(String[] args) throws InterruptedException {
        Test test  = new Test();

        Thread consumer = new Thread(new Consumer(test));
        Thread producer = new Thread(new Producer(test));

        consumer.start();
        producer.start();

        consumer.join();
        producer.join();
    }
}

class Consumer implements Runnable {
    private final Test test;

    public Consumer(Test test) {
        this.test = test;
    }

    @Override
    public void run() {
        try {
            consume();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void consume() throws InterruptedException {
        ArrayBlockingQueue<Integer> queue = test.getQueue();
        while (true) {
            Thread.sleep(3000);
            System.out.println(queue.take());
            System.out.println("Queue size: " + queue.size());
        }
    }
}

class Producer implements Runnable {
    private final Test test;

    public Producer(Test test) {
        this.test = test;
    }

    @Override
    public void run() {
        try {
            produce();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void produce() throws InterruptedException {
        ArrayBlockingQueue<Integer> queue = test.getQueue();
        Random random = new Random();
        while(true) {
            queue.put(random.nextInt(100));
        }
    }
}
