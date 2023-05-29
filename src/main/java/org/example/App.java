package org.example;

import org.example.configuration.AppConfig;
import org.example.configuration.Communication;
import org.example.entity.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class App
{
    public static void main( String[] args ) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        Communication communication = context.getBean(Communication.class);

        List<User> allUsers = communication.getAllUsers();
        for (User user : allUsers) {
            System.out.println(user.toString());
        }

        System.out.println("В результате выполненных операций вы должны получить итоговый код, " +
                "сконкатенировав все его части. Количество символов в коде = 18: "
                + communication.addUser(new User(3L, "James", "Brown", (byte) 23))
                + communication.updateUser(3L , new User(3L,"Thomas", "Shelby", (byte) 23))
                + communication.deleteUser(3L));
    }
}
