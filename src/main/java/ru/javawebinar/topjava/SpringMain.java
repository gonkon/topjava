package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName1", "email3@mail.ru", "password", Role.ADMIN));
            adminUserController.create(new User(null, "userName1", "email2@mail.ru", "password", Role.USER));
            adminUserController.create(new User(null, "userName1", "email1@mail.ru", "password", Role.USER));

            System.out.println(adminUserController.get(3));

            adminUserController.delete(3);
            adminUserController.getAll().forEach(System.out::println);

            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            mealRestController.getAll().forEach(System.out::println);

        }
    }
}
