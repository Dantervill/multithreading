package org.example.leetcode;

import java.util.function.IntConsumer;

public class MyConsumer implements IntConsumer {
    @Override
    public void accept(int value) {
        System.out.println(value);
    }
}
