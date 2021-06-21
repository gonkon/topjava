package ru.javawebinar.topjava.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(MEAL_ID_1, USER_ID);
        assertMatch(meal, userMeal1);
    }

    @Test
    public void getNotFound() {
        Assert.assertThrows(NotFoundException.class, () -> service.get(MEAL_ID_1, ADMIN_ID));
    }

    @Test
    public void getMealNotFound() {
        Assert.assertThrows(NotFoundException.class, () -> service.get(MealTestData.NOT_FOUND, USER_ID));
    }

    @Test
    public void delete() {
        service.delete(MEAL_ID_1, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ID_1, USER_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(MEAL_ID_1, ADMIN_ID));
    }

    @Test
    public void deleteMealNotFound() {
        Assert.assertThrows(NotFoundException.class, () -> service.delete(MealTestData.NOT_FOUND, USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> meals_list = service.getBetweenInclusive(START_DATE, END_DATE, USER_ID);
        assertMatch(meals_list, userMeal4, userMeal3, userMeal2);
    }

    @Test
    public void getBetweenInclusiveFilterIsNull() {
        List<Meal> meals_list = service.getBetweenInclusive(null, null, USER_ID);
        assertMatch(meals_list, allUserMeals);
    }

    @Test
    public void getAll() {
        List<Meal> meals_list = service.getAll(USER_ID);
        assertMatch(meals_list, allUserMeals);
    }

    @Test
    public void update() {
        Meal updated = MealTestData.getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(updated.getId(), USER_ID), MealTestData.getUpdated());
    }

    @Test
    public void updateNotFound() {
        Meal updated = MealTestData.getUpdated();
        assertThrows(NotFoundException.class, () -> service.update(updated, ADMIN_ID));
    }

    @Test
    public void create() {
        Meal createdMeal = service.create(MealTestData.getNew(), USER_ID);
        int createdId = createdMeal.getId();
        Meal newMeal = MealTestData.getNew();
        newMeal.setId(createdId);
        assertMatch(createdMeal, newMeal);
        assertMatch(service.get(createdId, USER_ID), newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        Meal duplicateMeal = new Meal(userMeal1.getDateTime(), "Завтрак", 500);
        assertThrows(DataAccessException.class, () -> service.create(duplicateMeal, USER_ID));
    }
}