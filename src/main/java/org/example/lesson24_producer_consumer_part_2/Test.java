package org.example.lesson24_producer_consumer_part_2;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Test {

    public static void main(String[] args) throws InterruptedException {
        ProducerConsumer pc = new ProducerConsumer();

        Thread producer = new Thread(() -> {
            try {
                pc.produce();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                pc.consume();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();
    }
}

class ProducerConsumer {
    private final Queue<Integer> queue = new LinkedList<>();
    private static final int LIMIT = 10;
    private final Object lock = new Object();

    // добавляем элементы в очередь
    public void produce() throws InterruptedException {
        Random random = new Random();
        while (true) {
            synchronized (lock) {
                /*
                    Поток producer заходит в тело этого цикла только в том случае,
                     если размер очереди равен 10
                 */
                while(queue.size() == LIMIT) {
                    /*
                     В потоке producer вы вызываем wait, чтобы освободить монитор объекта lock.
                      -> Смотри далее описание работы потока consumer в методе consume()
                     */
                    System.out.println("Очередь заполнена. Освобождаем монитор объекта lock с помощью lock.wait()");
                    lock.wait();
                }
                System.out.println("Producer добавил новый элемент в очередь");
                queue.offer(random.nextInt(10));
                // разбудили поток consumer, чтобы в месте где был вызван lock.wait() в методе consume()
                // поток consumer мог продолжить свою работу
                System.out.println("Пробуждаю поток consumer в lock.wait() метода consume()");
                lock.notify();
            }
        }
    }

    // читаем элементы из очереди
    public void consume() throws InterruptedException {
        while (true) {
            /*
            В этот же момент поток consumer захватывает монитор объекта lock
            и здесь читает один элемент из очереди и освобождает место в этой очереди.
            И когда у нас появилось одно свободное место, мы хотим оповестить (lock.notify()) другой поток
            (в нашем случае producer), о том что можно положить новый элемент в очередь
             */
            synchronized (lock) {
                /*
                Поток consumer заходит в этот блок кода, когда размер очереди равен нулю.
                Другими словами когда потоку consumer нечего читать. Далее мы освобождаем
                монитор объекта lock, чтобы поток producer мог добавить новые элементы в
                очередь
                 */
                while (queue.size() == 0) {
                    System.out.println("Очередь пуста. Освобождаем монитор объекта lock с помощью lock.wait() метода consume()");
                    lock.wait();
                }
                System.out.println("Consumer читает один элемент очереди");
                Integer value = queue.poll();
                System.out.println(value);

                System.out.println("Пробуждаю поток producer в lock.wait() метода produce()");
                lock.notify();
            }

            Thread.sleep(1000);
        }
    }

}
