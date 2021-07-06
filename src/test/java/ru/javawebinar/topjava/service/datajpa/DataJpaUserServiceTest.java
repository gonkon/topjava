package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.BaseUserServiceTest;

import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends BaseUserServiceTest {
    @Test
    public void getWithMeal() {
        User user = service.getWithMeal(USER_ID);
        MATCHER_MEALS.assertMatch(user, getUserWithMeals());
    }
}
