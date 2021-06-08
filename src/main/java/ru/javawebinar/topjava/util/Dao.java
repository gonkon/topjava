package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;

public interface Dao {

    public Meal add(Meal meal);

    public Meal edit(int mealId, Meal meal);

    public void delete(int mealId);

}
