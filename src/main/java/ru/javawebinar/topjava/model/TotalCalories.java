
package ru.javawebinar.topjava.model;


import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

interface TotalCalories {
    Map<String, Map<LocalDate, Integer>> ALL_USERS_CALORIES_MAP = new HashMap<>();
//    Map<LocalDate, Integer> CALORIES_MAP = new HashMap<>();


    void setCaloriesPerDate(String userLogin, LocalDate dt, Integer calories);

    int getCaloriesPerDate(String userLogin, LocalDate dt);
}