package org.example.lesson20_syncronized_part_2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        new Worker().main();
    }
}

class Worker {
    Random random = new Random();
    private final List<Integer> list1 = new ArrayList<>(1000);
    private final List<Integer> list2 = new ArrayList<>(1000);

    private final Object listOneLock = new Object();
    private final Object listTwoLock = new Object();

    /*
        Синхронизация потока на мониторе объекта this (Worker)
        Монитор один и тот же, а значит оба метода будут заблокированы одним из потоков
     */
    public void addToList1() throws InterruptedException {
        // Синхронизируемся на мониторе объекта listOneLock
        // Если один поток зашел в монитор, то другой поток не имеет к нему доступа.
        // Зато у него есть доступ к монитору другого объекта
        synchronized (listOneLock) {
            Thread.sleep(1);
            list1.add(random.nextInt(100));
        }
    }

    /*
        Синхронизация потока на мониторе объекта this (Worker)
        Монитор один и тот же, а значит оба метода будут заблокированы одним из потоков
    */
    public void addToList2() throws InterruptedException {
        // Синхронизируемся на мониторе объекта listTwoLock
        // Если один поток зашел в монитор, то другой поток не имеет к нему доступа.
        // Зато у него есть доступ к монитору другого объекта
        synchronized (listTwoLock) {
            Thread.sleep(1);
            list2.add(random.nextInt(100));
        }
    }

    public void work() throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            addToList1();
            addToList2();
        }
    }

    public void main() throws InterruptedException {
        long before = System.currentTimeMillis();

        Thread thread1 = new Thread(() -> {
            try {
                // поток_1 начал выполнение метода work()
                work();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                // поток_2 начал выполнение метода work()
                work();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        // потоки начали свое выполнение
        thread1.start();
        thread2.start();

        // говорим главному потоку чтобы он подождал пока другие потоки не завершат свое выполнение
        thread1.join();
        thread2.join();

        long after = System.currentTimeMillis();
        System.out.println("Program took " + (after - before) + " ms to run");
        System.out.println("List1 size: " + list1.size());
        System.out.println("List2 size: " + list2.size());
    }
}
