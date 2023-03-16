package org.example.lesson33_lambda_part_1;


import java.util.ArrayList;
import java.util.List;

interface Executable {
    int execute(int x);
}

class Runner {
    public int run(Executable e) {
        return e.execute(5);
    }
}

class Test {
    public static void main(String[] args) {
        Runner runner = new Runner();

        /*
        синтаксис_1
        int num = runner.run(new ExecutableImpl());
        System.out.println(num);
        */

        /*
        синтаксис_2
        Данная реализация не имеет область видимости за ее пределами
        */
        int a = 2;
        int num2 = runner.run(new Executable() {
            final int a = 1;
            @Override
            public int execute(int x) {
                System.out.println("Hello");
                return x + 5 + a;
            }
        });
        System.out.println(num2);

        /*
         синтаксис_3
         Лямбда выражение имеет область видимости за ее пределами
         */
        int num3 = runner.run(x -> {
            int b = 10;
            return x + a + b;
        });
        System.out.println(num3);

        // List.of() -> unmodifiable list
        List<String> list = new ArrayList<>(List.of("Hello", "Goodbye", "a", "ab"));
//        list.sort(new Comparator<String>() {
//            @Override
//            public int compare(String o1, String o2) {
//                if (o1.length() > o2.length()) {
//                    return 1;
//                } else if (o1.length() < o2.length()) {
//                    return -1;
//                } else {
//                    return 0;
//                }
//            }
//        });

        list.sort((s1, s2) -> {
            if (s1.length() > s2.length()) return 1;
            else if (s1.length() < s2.length()) return -1;
            else return 0;
        });

//        Comparator<String> comparator = Comparator.comparingInt(String::length);
//        list.sort(comparator);

//        list.sort(Comparator.reverseOrder());
        System.out.println(list);
    }
}
