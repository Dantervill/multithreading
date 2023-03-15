package org.example.lesson21_thread_pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        /*
         Это пул потоков (Thread pool)
         Метафора - это наша фабрика с 2-мя работниками
        */
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        long before = System.currentTimeMillis();
        for (int i = 0; i < 5; i++) {
            // передали 5 заданий
            executorService.submit(new Work(i));
        }

        // все задания были переданы и они начали свое выполнение
        executorService.shutdown();
        System.out.println("All tasks submitted");

        /*
         Похож на join. Если задания не будут выполнены в течение 1-го дня,
         то поток Main проснется и завершит свое выполнение
        */
        executorService.awaitTermination(1, TimeUnit.DAYS);
        long after = System.currentTimeMillis();
        System.out.println("Program took " + (after - before) + " ms to complete all tasks");
    }
}

class Work implements Runnable {
    private final int id;

    public Work(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Work " + id + " was completed");
    }
}