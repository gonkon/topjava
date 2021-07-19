package ru.javawebinar.topjava.service.datajpa;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.JpaUtil;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.persistence.EntityManagerFactory;
import java.util.Map;

import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.UserTestData.MATCHER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles(DATAJPA)
public class DataJpaUserServiceTest extends AbstractUserServiceTest {
    @Autowired
    protected JpaUtil jpaUtil;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Test
    public void isSecondLevelCacheEnabled() {
        Map<String, Object> map = entityManagerFactory.getProperties();
        Assert.assertEquals(map.get("hibernate.cache.use_second_level_cache"), "false");
    }

    @Test
    public void getWithMeals() {
        User user = service.getWithMeals(USER_ID);
        MATCHER.assertMatch(user, UserTestData.user);
        MealTestData.MATCHER.assertMatch(user.getMeals(), MealTestData.meals);
    }

    @Test
    public void getWithMealsNotFound() {
        Assert.assertThrows(NotFoundException.class,
                () -> service.getWithMeals(1));
    }
}