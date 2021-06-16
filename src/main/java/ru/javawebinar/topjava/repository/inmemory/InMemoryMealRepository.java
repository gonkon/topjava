package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, meal.getUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        } else {
            if (!containsUserId(repository.get(meal.getId()), userId)) return null;
        }
        // handle case: update, but not present in storage
        meal.setUserId(userId);
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return containsUserId(repository.get(id), userId) && repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = repository.getOrDefault(id, null);
        return containsUserId(meal, userId) ? meal : null;
    }

    @Override
    public List<Meal> getAllBetweenDates(int userId, LocalDate dateMin, LocalDate dateMax) {
        return filterByPredicate(userId, meal -> checkInDates(meal, dateMin, dateMax));
    }

    @Override
    public List<Meal> getAll(int userId) {
        return filterByPredicate(userId, meal -> true);
    }

    public List<Meal> filterByPredicate(int userId, Predicate<Meal> filter) {
        return repository.values().stream()
                .filter(meal -> containsUserId(meal, userId))
                .filter(filter)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    public boolean containsUserId(Meal meal, int userId) {
        if (meal == null) {
            return false;
        }
        return meal.getUserId() == userId;
    }

    public boolean checkInDates(Meal meal, LocalDate dateMin, LocalDate dateMax) {
        return meal.getDate().compareTo(dateMin) >= 0 && dateMax.compareTo(meal.getDate()) >= 0;
    }
}

