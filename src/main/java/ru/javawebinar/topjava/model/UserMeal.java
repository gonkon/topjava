package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

public class UserMeal{
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    public UserMeal(LocalDateTime dateTime, String description, int calories) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
//        if (ALL_USERS_CALORIES_MAP.get(userLogin) == null) {
//            ALL_USERS_CALORIES_MAP.put(userLogin, new HashMap<>());
//        }
//        setCaloriesPerDate(userLogin, dateTime.toLocalDate(),
//                ALL_USERS_CALORIES_MAP.get(userLogin).getOrDefault(dateTime.toLocalDate(), 0) + calories);
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

//    @Override
//    public void setCaloriesPerDate(String userLogin, LocalDate dt, Integer calories) {
//        ALL_USERS_CALORIES_MAP.get(userLogin).put(dt,
//                ALL_USERS_CALORIES_MAP.get(userLogin).getOrDefault(dateTime.toLocalDate(), 0) + calories);
//    }
//
//    @Override
//    public int getCaloriesPerDate(String userLogin, LocalDate dt) {
//        return ALL_USERS_CALORIES_MAP.get(userLogin).get(dt);
//    }
}