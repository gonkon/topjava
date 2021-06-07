package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDAO {
    private List<Meal> meals;
    public DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    static AtomicInteger idGenerator = new AtomicInteger();

    public static int getId() {
        return idGenerator.getAndIncrement();
    }

    public List<Meal> generateMeals() {
        meals = new ArrayList<>(Arrays.asList(
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        ));
        return meals;
    }

    public synchronized void addMeal(LocalDateTime dateTime, String description, int calories) {
        Meal meal = new Meal(dateTime, description, calories);
        meals.add(meal);
    }

    public synchronized void editMeal(int mealId, LocalDateTime dateTime, String description, int calories) {
        Meal meal = getMeal(mealId);
        meal.setDateTime(dateTime);
        meal.setDescription(description);
        meal.setCalories(calories);
    }

    public synchronized void deleteMeal(int mealId) {
        meals.remove(getMeal(mealId));
    }

    public Meal getMeal(int mealId) {
        return meals.stream().filter(m -> m.getId() == mealId).findFirst().get();
    }
}
