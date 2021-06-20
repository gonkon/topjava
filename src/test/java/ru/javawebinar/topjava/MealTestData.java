package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static int MEAL_ID = START_SEQ + 1;
    public static final int NOT_FOUND = 10;
    public static final LocalDate START_DATE = LocalDateTime.of(2020, Month.JANUARY, 30, 0, 0).toLocalDate();
    public static final LocalDate END_DATE = LocalDateTime.of(2020, Month.JANUARY, 30, 0, 0).toLocalDate();

    public static Map<Integer, Meal> meals = new ConcurrentHashMap<Integer, Meal>() {{
        put(++MEAL_ID, new Meal(MEAL_ID, LocalDateTime.of(2021, Month.JANUARY, 29, 10, 0), "Завтрак", 500));
        put(++MEAL_ID, new Meal(MEAL_ID, LocalDateTime.of(2021, Month.JANUARY, 29, 13, 0), "Обед", 1000));
        put(++MEAL_ID, new Meal(MEAL_ID, LocalDateTime.of(2021, Month.JANUARY, 29, 20, 0), "Ужин", 500));
        put(++MEAL_ID, new Meal(MEAL_ID, LocalDateTime.of(2021, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        put(++MEAL_ID, new Meal(MEAL_ID, LocalDateTime.of(2021, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        put(++MEAL_ID, new Meal(MEAL_ID, LocalDateTime.of(2021, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        put(++MEAL_ID, new Meal(MEAL_ID, LocalDateTime.of(2021, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        put(++MEAL_ID, new Meal(MEAL_ID, LocalDateTime.of(2021, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        put(++MEAL_ID, new Meal(MEAL_ID, LocalDateTime.of(2021, Month.JANUARY, 31, 13, 0), "Обед", 500));
        put(++MEAL_ID, new Meal(MEAL_ID, LocalDateTime.of(2021, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }};

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static Meal getUpdated() {
        Meal meal = new Meal(meals.get(MEAL_ID));
        meal.setCalories(600);
        meal.setDescription("Полдник");
        meal.setDateTime(LocalDateTime.of(1999, Month.JANUARY, 31, 20, 0));
        return meal;
    }

    public static Meal getNew() {
        return new Meal(LocalDateTime.of(2019, Month.JANUARY, 1, 1, 0), "Новый", 1000);
    }
}