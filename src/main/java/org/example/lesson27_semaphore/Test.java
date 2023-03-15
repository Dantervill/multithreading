package org.example.lesson27_semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        /*
        Используем класс семафор в том случае если мы хотим ограничить доступ к ресурсу,
        которым пользуются потоки.
        int permits -> количество потоков которые одновременно могут писать данные в семафор
         */
//        Semaphore semaphore = new Semaphore(3); // макс 3 потока могут использовать ресурс

        // Вызываем когда в потоке начинаем взаимодействовать с ресурсом
//        semaphore.acquire();

        /*
            Используем этот метод когда поток закончил использовать ресурс
            Пример: поток_1 взял себе 1 разрешение (кол-во пермитов стало 2) сделал опред.-ую работу,
            мы вызвали данный метод, чтобы указать что работа с ресурсом окончена (кол-во пермитов теперь 3)
         */
//        semaphore.release();

        // количество оставшихся/свободных разрешений
//        int availablePermits = semaphore.availablePermits();

        int numOfThreads = 200;
        ExecutorService executorService = Executors.newFixedThreadPool(numOfThreads);
        Connection connection = Connection.getConnection();

        for (int i = 0; i < numOfThreads; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        connection.work();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.DAYS);




    }
}

// это ресурс -> соединение к серверу
class Connection {
    private int connectionsCount;
    private static final Connection connection = new Connection();
    private final Semaphore semaphore = new Semaphore(10);

    private Connection() {

    }

    public static Connection getConnection() {
        return connection;
    }

    public void work() throws InterruptedException {
        semaphore.acquire();
        try {
            doWork();
        } finally {
            semaphore.release();
        }
    }

    private void doWork() throws InterruptedException {
        synchronized (this) {
            connectionsCount++;
            System.out.println(connectionsCount);
        }

        Thread.sleep(5000); // полезная работа с соединением

        synchronized (this) {
            connectionsCount--;
        }
    }
}
