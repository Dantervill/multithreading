package org.example.lesson25_countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(3); // защелка обратного отсчета

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 3; i++) {
            executorService.submit(new Processor(i, countDownLatch));
        }
        executorService.shutdown();

        for (int i = 0; i < 3; i++) {
            Thread.sleep(1000);
            countDownLatch.countDown();
        }

    }
}

class Processor implements Runnable {
    private final int id;
    private final CountDownLatch countDownLatch;

    public Processor(int id, CountDownLatch count) {
        this.id = id;
        this.countDownLatch = count;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Thread with id " + id + " proceeded.");
        countDownLatch.countDown();
    }
}