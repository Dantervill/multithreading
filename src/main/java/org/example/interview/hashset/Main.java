package org.example.interview.hashset;

import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Set<Integer> set = new HashSet<>(Set.of(1, 2, 3, 4, 5));
        set.add(null);
        System.out.println(set.contains(null));
    }
}
