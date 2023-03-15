package org.example.interview_practice.static_and_dynamic_polymorphism;

public class Main {
    public static void main(String[] args) {
        // статический полиморфизм -> вызов перегруженных методов при компиляции кода
        StaticPolyDemo staticPolyDemo = new StaticPolyDemo();
        staticPolyDemo.saySomething();
        staticPolyDemo.saySomething("Hello");
        staticPolyDemo.saySomething("Bye", 3);

        // динамический полиморфизм -> вызов переопределенных методов при выполнении программы
    }
}

class StaticPolyDemo {
    public void saySomething() {
        System.out.println("Saying with no additional words");
    }

    public void saySomething(String word) {
        System.out.println("Saying " + word);
    }

    public void saySomething(String word, int num) {
        for (int i = 0; i < num; i++) {
            System.out.println("Saying " + word + " " + num + " times");
        }
    }
}


