package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface MealDao {

    Meal add(Meal meal);

    Meal edit(Meal meal);

    void delete(int id);

    Meal get(int id);

    List<Meal> getAll();
}
