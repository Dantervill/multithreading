package org.example.lesson28_deadlock;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        Runner runner = new Runner();

        Thread thread1 = new Thread(runner::firstThread);
        Thread thread2 = new Thread(runner::secondThread);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        runner.finished();
    }
}

class Runner {
    private final Account acc1 = new Account();
    private final Account acc2 = new Account();

    private final Lock lock1 = new ReentrantLock();
    private final Lock lock2 = new ReentrantLock();

    private void takeLocks(Lock lock1, Lock lock2) {
        boolean firstLockTaken = false;
        boolean secondLockTaken = false;

        while (true) {
            try {
                firstLockTaken = lock1.tryLock();
                secondLockTaken = lock2.tryLock();
            } finally {
                if (firstLockTaken && secondLockTaken) {
                    return;
                }

                if (firstLockTaken) {
                    lock1.unlock();
                }

                if (secondLockTaken) {
                    lock2.unlock();
                }
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void firstThread() {
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            takeLocks(lock1, lock2);
            try {
                Account.transfer(acc1, acc2, random.nextInt(100));
            } finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
    }

    public void secondThread() {
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            /*
            -> поток_1 захватил lock1, поток_2 захватил lock2.
            -> потоку_1 нужно захватить lock2, но lock2 уже захвачен потоком_2
            -> потоку_2 нужно захватить lock1, но lock1 уже захвачен потоком_1
            -> поток_1 и поток_2 не могут освободить свою локи, потому что по логике кода им следует захватить еще по одному локу
            -> отсюда возникает блокировка
            */
            takeLocks(lock2, lock1);
            try {
                Account.transfer(acc2, acc1, random.nextInt(100));
            } finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
    }

    public void finished() {
        System.out.println("Account 1 balance: " + acc1.getBalance());
        System.out.println("Account 2 balance: " + acc2.getBalance());
        System.out.println("Total balance " + (acc1.getBalance() + acc2.getBalance()));
    }
}

class Account {
    private int balance = 10000;

    public void deposit(int amount) {
        balance += amount;
    }

    public void withdraw(int amount) {
        balance -= amount;
    }

    public int getBalance() {
        return balance;
    }

    public static void transfer(Account acc1, Account acc2, int amount) {
        acc1.withdraw(amount);
        acc2.deposit(amount);
    }
}

//            synchronized (acc1) {
//                synchronized (acc2) {
//                /*
//                 код здесь будет выполняться только в том случае,
//                 когда поток захватит монитор acc1 и acc2
//                */
//                    Account.transfer(acc1, acc2, random.nextInt(100));
//                }
//            }


//            lock1.lock();
//            lock2.lock();
