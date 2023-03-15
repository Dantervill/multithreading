package org.example.leetcode;

import java.util.function.IntConsumer;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        ZeroEvenOdd zeroEvenOdd = new ZeroEvenOdd(5);
        IntConsumer printNumber = new MyConsumer();

//        Thread threadA = new Thread(() -> {
//            try {
//                zeroEvenOdd.zero(printNumber);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        });

        Thread threadB = new Thread(() -> {
            try {
                zeroEvenOdd.even(printNumber);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread threadC = new Thread(() -> {
            try {
                zeroEvenOdd.odd(printNumber);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

//        threadA.start();
        threadB.start();
        threadC.start();

//        threadA.join();
        threadB.join();
        threadC.join();
    }
}

