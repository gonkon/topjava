package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDaoMap implements MealDao {
    private Map<Integer, Meal> meals = new ConcurrentHashMap<>();
//    public DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private AtomicInteger idGenerator = new AtomicInteger();

    {
        add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

//    public synchronized Meal create(LocalDateTime dateTime, String description, int calories) {
//        int id = idGenerator.getAndIncrement();
//        return new Meal(id, dateTime, description, calories);
//    }

    public Meal add(Meal meal) {
        if (meal.getId() == null) {
            int id = idGenerator.getAndIncrement();
            meal.setId(id);
        }
        meals.put(meal.getId(), meal);
        return meal;
    }

    public Meal edit(Meal meal) {
        if (meals.replace(meal.getId(), meal) == null) {
            return null;
        }
        return meal;
    }

    public void delete(int id) {
        meals.remove(id);
    }

    public Meal get(int id) {
        Meal result = null;
        try {
            result = meals.get(id);
        } catch (NullPointerException ignore) {}
        return result;
    }

    public List<Meal> getAll() {
        return new ArrayList<>(meals.values());
    }
}
