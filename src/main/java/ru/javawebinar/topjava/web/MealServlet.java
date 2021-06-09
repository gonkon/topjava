package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoMap;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final int CALORIES_PER_DAY = 2000;
    private static final String INSERT_OR_EDIT = "mealEdit.jsp";
    private static final String LIST = "meals.jsp";
    private MealDao mealDao;

    @Override
    public void init() {
        mealDao = new MealDaoMap();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to meals");

        String forward = "";
        String action = request.getParameter("action");

        if (action != null) {
            int id;
            switch (action) {
                case "edit":
                    id = Integer.parseInt(request.getParameter("mealId"));
                    Meal meal = mealDao.get(id);
                    request.setAttribute("meal", meal);
                    forward = INSERT_OR_EDIT;
                    break;
                case "add":
                    request.setAttribute("meal", null);
                    forward = INSERT_OR_EDIT;
                    break;
                case "delete":
                    id = Integer.parseInt(request.getParameter("mealId"));
                    mealDao.delete(id);
                    log.debug("delete meal id = " + id);
                    response.sendRedirect(request.getContextPath() + "/meals");
                    return;
                default:
                    log.debug("unknown action");
                    response.sendRedirect(request.getContextPath() + "/meals");
                    return;
            }
        } else {
            request.setAttribute("mealsWithExcess", MealsUtil.filteredByStreams(mealDao.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
            forward = LIST;
        }

        request.setCharacterEncoding("utf-8");
        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        String mealId = request.getParameter("mealId");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));

        if (mealId.equals("")) {
            Meal meal = new Meal(dateTime, description, calories);
            mealDao.add(meal);
            log.debug("add meal id = " + meal.getId());
        } else {
            Meal meal = new Meal(Integer.parseInt(mealId), dateTime, description, calories);
            mealDao.edit(meal);
            log.debug("edit meal id = " + mealId);
        }

        response.sendRedirect(request.getContextPath() + "/meals");
    }
}
