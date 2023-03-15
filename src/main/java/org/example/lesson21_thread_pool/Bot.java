package org.example.lesson21_thread_pool;

import org.example.lesson20_syncronized_part_2.SomeSubClass;

public class Bot {

    public void show() {
        SomeSubClass someSubClass = new SomeSubClass();
        // не работает, т.к. данный метод имеет мод доступа protected, т.е данный метод будет доступен только из пакета где он был объявлен
        // Кроме того он также недоступен потому что данный класс не наследуются от класса, у которого есть этот метод
//        someSubClass.showMsgFromSuperClass();
    }
}
