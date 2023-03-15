package org.example.lesson33_lambda;

public abstract class Anonymous {
    abstract void showMsg();
    public void show() {
        System.out.println("Hello");
    }
}

interface Anon {
    void sayHi();
    default void saySomething() {
        System.out.println("I am saying something");
    }
}

class Test {
    public static void main(String[] args) {
        Anonymous anonymous1 = new Anonymous() {
            @Override
            public void showMsg() {
                System.out.println("Hello from abstract");
            }

            public void show() {
                System.out.println("Hello from non-abstract");
            }
        };
        anonymous1.showMsg();
        anonymous1.show();


        Anon anon = new Anon() {
            @Override
            public void sayHi() {
                System.out.println("Hi");
            }
        };
        anon.sayHi();
        anon.saySomething();
    }
}
