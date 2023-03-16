package org.example.interview_practice.static_and_dynamic_polymorphism;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // статический полиморфизм -> вызов перегруженных методов при компиляции кода
        System.out.println("Статический полиморфизм");
        StaticPolyDemo staticPolyDemo = new StaticPolyDemo();
        staticPolyDemo.saySomething();
        staticPolyDemo.saySomething("Hello");
        staticPolyDemo.saySomething("Bye", 3);

        // динамический полиморфизм -> вызов переопределенных методов при выполнении программы
        System.out.println("Динамический полиморфизм");
        NotificationService emailService = new EmailNotificationService();
        NotificationService smsService = new SmsNotificationService();
        List<NotificationService> services = Arrays.asList(emailService, smsService);
        services.forEach(NotificationService::sendNotification);

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

interface NotificationService {
    void sendNotification();
}

class EmailNotificationService implements NotificationService {
    @Override
    public void sendNotification() {
        System.out.println("Email notification has been sent");
    }
}

class SmsNotificationService implements NotificationService {
    @Override
    public void sendNotification() {
        System.out.println("Sms notification has been sent");
    }
}




