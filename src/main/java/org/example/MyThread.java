package org.example;

public class MyThread extends Thread {
    // Volatile - переменная не будет кэшироваться для памяти ядра
    // переменная всегда будет читаться/находиться в главной/общей памяти
    // нет volatile => проблема когерентности памяти
    // есть volatile - каждый поток будет обращаться к главной памяти,
    // чтобы взять свежее значение переменной
    /*
        У каждого ядра процессора есть свой кэш, хранящий свежие данные,
        которые нужны для этого ядра.
        Проблема когерентности кэшей -> когда кэши разный ядер процессора не совпадают.
        Пример, в кэше_1 значение переменной running = false, а в кэше_2 значение этой же
        переменной running = true
     */
    private volatile boolean running = true;
    @Override
    public void run() {
        while (running) {
            System.out.println("Hello");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void shutdown() {
        this.running = false;
    }
}
