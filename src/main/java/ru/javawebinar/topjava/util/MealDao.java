package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDao implements Dao {
    public Map<Integer, Meal> meals = new ConcurrentHashMap<>();
    public DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private AtomicInteger idGenerator = new AtomicInteger();

    {
        this.add(create(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        this.add(create(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        this.add(create(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        this.add(create(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        this.add(create(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        this.add(create(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        this.add(create(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    public synchronized Meal create(LocalDateTime dateTime, String description, int calories) {
        int id = idGenerator.getAndIncrement();
        return new Meal(id, dateTime, description, calories);
    }

    public synchronized Meal add(Meal meal) {
        meals.put(meal.getId(), meal);
        return meal;
    }

    public synchronized Meal edit(int mealId, Meal meal) {
        meals.replace(mealId, meal);
        return meal;
    }

    public synchronized void delete(int mealId) {
        meals.remove(mealId);
    }

    public Meal getMeal(int mealId) {
        Meal result = null;
        try {
            result = meals.get(mealId);
        } catch (Exception ignore) {}
        return result;
    }

    public List<Meal> getAll() {
        return new ArrayList<>(meals.values());
    }
}
