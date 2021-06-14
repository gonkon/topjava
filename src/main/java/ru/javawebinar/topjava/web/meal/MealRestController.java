package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;

@Controller
public class MealRestController {
    private int userId;

    @Autowired
    private MealService service;

    public List<MealTo> getAll() {
        userId = SecurityUtil.authUserId();
        return MealsUtil.getTos(service.getAll(userId), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public List<MealTo> getAll(String timeMin, String timeMax, String dateMin, String dateMax) {
        userId = SecurityUtil.authUserId();
        LocalTime timeMinLT = timeMin == "" ? LocalTime.MIN : LocalTime.parse(timeMin);
        LocalTime timeMaxLT = timeMax == "" ? LocalTime.MAX : LocalTime.parse(timeMax);
        LocalDate dateMinLD = dateMin == "" ? LocalDate.MIN : LocalDate.parse(dateMin);
        LocalDate dateMaxLD = dateMax == "" ? LocalDate.MAX : LocalDate.parse(dateMax);
        return MealsUtil.getFilteredTos(service.getAll(userId, dateMinLD, dateMaxLD), MealsUtil.DEFAULT_CALORIES_PER_DAY, timeMinLT, timeMaxLT);
    }

    public Meal get(int id) {
        userId = SecurityUtil.authUserId();
        return service.get(id, userId);
    }

    public Meal create(Meal meal) {
        userId = SecurityUtil.authUserId();
        return service.create(meal, userId);
    }

    public void delete(int id) {
        userId = SecurityUtil.authUserId();
        service.delete(id, userId);
    }

    public void update(Meal meal) {
        userId = SecurityUtil.authUserId();
        assureIdConsistent(meal, userId);
        service.update(meal, userId);
    }

}