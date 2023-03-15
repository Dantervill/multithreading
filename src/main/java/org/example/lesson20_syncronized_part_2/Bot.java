package org.example.lesson20_syncronized_part_2;

public class Bot {
    public void show() {
        SomeSubClass someSubClass = new SomeSubClass();
        // данный метод доступен так как он лежит в том же пакете, что и данный класс
        someSubClass.showMsgFromSuperClass();
    }
}
