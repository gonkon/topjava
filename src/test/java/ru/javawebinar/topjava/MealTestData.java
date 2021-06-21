package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL_ID_1 = START_SEQ + 2;
    public static final int MEAL_ID_2 = START_SEQ + 3;
    public static final int MEAL_ID_3 = START_SEQ + 4;
    public static final int MEAL_ID_4 = START_SEQ + 5;
    public static final int MEAL_ID_5 = START_SEQ + 6;
    public static final int NOT_FOUND = 10;
    public static final LocalDate START_DATE = LocalDate.of(2021, Month.JANUARY, 30);
    public static final LocalDate END_DATE = LocalDate.of(2021, Month.JANUARY, 30);

    public static final Meal userMeal1 = new Meal(MEAL_ID_1, LocalDateTime.of(2021, Month.JANUARY, 29, 10, 0), "Завтрак", 500);
    public static final Meal userMeal2 = new Meal(MEAL_ID_2, LocalDateTime.of(2021, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal userMeal3 = new Meal(MEAL_ID_3, LocalDateTime.of(2021, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal userMeal4 = new Meal(MEAL_ID_4, LocalDateTime.of(2021, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal userMeal5 = new Meal(MEAL_ID_5, LocalDateTime.of(2021, Month.JANUARY, 31, 20, 0), "Ужин", 410);

    public static final List<Meal> allUserMeals = Arrays.asList(userMeal5, userMeal4, userMeal3, userMeal2, userMeal1);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static Meal getUpdated() {
        Meal meal = new Meal(userMeal1);
        meal.setCalories(600);
        meal.setDescription("Полдник");
        meal.setDateTime(LocalDateTime.of(1999, Month.JANUARY, 28, 20, 0));
        return meal;
    }

    public static Meal getNew() {
        return new Meal(LocalDateTime.of(2019, Month.JANUARY, 1, 1, 0), "Новый", 1000);
    }
}