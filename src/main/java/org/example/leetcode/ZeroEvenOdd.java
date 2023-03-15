package org.example.leetcode;

import java.util.function.IntConsumer;

public class ZeroEvenOdd {
    private final int n;
    private boolean isEven = true;
    private boolean isOdd = true;

    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
//    public void zero(IntConsumer printNumber) throws InterruptedException {
//        synchronized(this) {
////             for (int i = 0; i < n; i++) {
////                 while (i != 0) {
////                     this.wait();
////                 }
////             }
//            System.out.println("Print num from zero");
//             printNumber.accept(0);
////             this.notify();
//        }
//    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        synchronized(this) {
            for (int i = 1; i <= n; i++) {
                while (isOdd) {
                    this.wait();
                }
                System.out.println("Print num from even");
                printNumber.accept(i);
                isOdd = true;
                this.notify();
            }
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        synchronized(this) {
            for (int i = 1; i <= n; i++) {
                while (isEven) {
                    this.wait();
                }
                System.out.println("Print num from odd");
                printNumber.accept(i);
                isEven = true;
                this.notify();
            }
        }
    }
}
