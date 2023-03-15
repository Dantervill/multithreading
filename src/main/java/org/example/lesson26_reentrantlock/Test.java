package org.example.lesson26_reentrantlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        Task task = new Task();

        Thread thread1 = new Thread(task::firstThread);

        Thread thread2 = new Thread(task::secondThread);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        task.showCounter();
    }
}

class Task {
    private int counter;
    private final Lock lock = new ReentrantLock();

    private void increment() {
        for (int i = 0; i < 10000; i++) {
            counter++;
        }
    }

    public void firstThread() {
        // Поток_1 вызвал метод lock, инкрементация заблокирована для остальных потоков.
        // Поток_2 ждет в методе secondThread(), когда lock станет unlock, чтобы сделать его снова lock
        lock.lock();
        increment();
        lock.unlock();
    }

    public void secondThread() {
        lock.lock();
        increment();
        lock.unlock();
    }

    public void showCounter() {
        System.out.println(counter);
    }
}
