package org.example.lesson23_wait_and_notify;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        // почему у любого объекта есть методы notify() и wait() ->
        // потому что мы можем синхронизироваться на любом объекте

        WaitAndNotify wn = new WaitAndNotify();

        Thread producer = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    wn.produce();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread consumer = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    wn.consume();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();
    }
}

class WaitAndNotify {
    public void produce() throws InterruptedException {
        synchronized (this) {
            System.out.println("Producer thread started...");
            /*
             Данный метод может вызываться только в пределах синхронизованного блока.
             1. отдаем intrinsic lock
             2. ждем пока будет вызван notify() на этом же объекте (в нашем случае это this)
            */
            wait();
            System.out.println("Producer thread resumed...");
        }
    }

    public void consume() throws InterruptedException {
        Thread.sleep(2000);
        Scanner scanner = new Scanner(System.in);

        synchronized (this) {
            System.out.println("Waiting for return key pressed");
            scanner.nextLine();
            notify();
        }
    }
}
