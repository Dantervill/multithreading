package org.example.lesson29_thread_interraption;

import java.util.Random;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        // механизм прерывания - прервать один поток (thread) из другого потока (main)
        // InterruptedException - это исключение, к-е выбрасывается когда поток внутри другого потока был прерван
        Thread thread = new Thread( new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                for (int i = 0; i < 1_000_000_000; i++) {
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println(Thread.currentThread().getName() + " was interrupted");
                        break;
                    }
                    System.out.println(Math.sin(random.nextDouble()));
                }
            }
        }, "my-thread");

        System.out.println("Starting thread");

        thread.start();
        /*
        interrupt() должен вызываться до join(),
        иначе мы до него не дойдем
         */
        Thread.sleep(1000);
        thread.interrupt();

        // говорит потоку main, чтобы он дождался пока thread закончит свою работу
        thread.join();

        System.out.println("Thread has finished");
    }
}
