package org.example.lesson30_callable_and_future;

import java.util.Random;
import java.util.concurrent.*;
import java.util.function.IntConsumer;

public class Test {
    public static void main(String[] args) {
        /*
        Проблема: мы не можем вернуть значение из нашего потока.
        Решение: использовать вместо new Runnable, new Callable.
        С помощью интерфейсов Callable и Future мы можем получать значения от потоков
         */
        ExecutorService executorService = Executors.newFixedThreadPool(1);
//        executorService.submit(() -> {
//            System.out.println("ExecutorService started thread");
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            System.out.println("Finished");
//            result++;
//            System.out.println(result);
//        });

        // когда-то в будущем в переменной future будет храниться значение
        Future<Integer> future = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("ExecutorService started thread");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Finished");
                Random random = new Random();
                int randomValue = random.nextInt(10);
                if (randomValue < 5) throw new Exception("Random value is less than 5");
                return randomValue;
            }
        });

        executorService.shutdown();
        /*
        get дожидается окончания выполнения потока
        в месте future.get() главный поток останавливается, и
        он ждет, пока другой поток выполнится/вернет рандомное значение.
        Затем результат выполнения потока thread помещается в переменную
        result и главный поток main продолжает свое выполнение
         */
        try {
            Integer result = future.get();
            System.out.println(result);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            Throwable exc = e.getCause();
            System.out.println(exc.getMessage());
        }

//        // ждать определенное время, чтобы поток успел закончить свое выполнение
//        executorService.awaitTermination(1, TimeUnit.DAYS);
    }
}
