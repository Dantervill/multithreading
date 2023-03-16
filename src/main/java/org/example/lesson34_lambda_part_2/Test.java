package org.example.lesson34_lambda_part_2;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) {
        int[] array = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        List<Integer> integers = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));

        System.out.println("Array before update " + Arrays.toString(array));
        System.out.println("List before update " + integers);

        integers = integers.stream().map(integer -> integer * 2).collect(Collectors.toList());
        array = Arrays.stream(array).map(integer -> integer * 2).toArray();

        System.out.println("Array after update " + Arrays.toString(array));
        System.out.println("List after update " + integers);

        integers.forEach(System.out::println);

        int sum = integers.stream().reduce(0, Integer::sum);
        BigInteger mult = Arrays.stream(array)
                .mapToObj(integer -> new BigInteger(String.valueOf(integer)))
                .reduce(BigInteger.ONE, BigInteger::multiply);

        System.out.println("Sum is " + sum);
        System.out.println("Mult is " + mult);
    }
}
