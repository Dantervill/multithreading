package org.example.interview.access_modifiers;

import org.example.interview.PackagePrivateDemo;
import org.example.interview.ProtectedDemo;

public class Main {
    public static void main(String[] args) {
        PackagePrivateDemo packagePrivateDemo = new PackagePrivateDemo();
//        packagePrivateDemo.showName(); -> не будет работать
//        т.к. поля и методы класса доступны в пределах пакета

        ProtectedDemo protectedDemo = new ProtectedDemo();
//        protectedDemo.showName(); -> не будет работать т.к.
//        поля и методы класса доступны в пределах пакета и для наследников за пределами пакета

        ProtectedDemoChild protectedDemoChild = new ProtectedDemoChild();
        protectedDemoChild.showName();
    }
}
