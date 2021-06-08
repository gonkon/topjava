package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealDao;
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
    static final int CALORIES_PER_DAY = 2000;
    private static final String INSERT_OR_EDIT = "mealEdit.jsp";
    private static final String LIST = "meals.jsp";
    MealDao mealDao;

    @Override
    public void init() {
        mealDao = new MealDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to meals");

        String forward = "";
        String action = request.getParameter("action");
        boolean isRedirected = false;

        if (action != null) {
            int mealId;
            switch (action) {
                case "edit":
                    mealId = Integer.parseInt(request.getParameter("mealId"));
                    Meal meal = mealDao.getMeal(mealId);
                    request.setAttribute("meal", meal);
                    request.setAttribute("mealId", mealId);
                    forward = INSERT_OR_EDIT;
                    break;
                case "add":
                    request.setAttribute("mealId", null);
                    forward = INSERT_OR_EDIT;
                    break;
                case "delete":
                    mealId = Integer.parseInt(request.getParameter("mealId"));
                    mealDao.delete(mealId);
                    log.debug("delete meal id = " + mealId);
                    isRedirected = true;
                    response.sendRedirect(request.getContextPath() + "/meals");
                    break;
            }
        } else {
            request.setAttribute("mealsWithExcess", MealsUtil.filteredByStreams(mealDao.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
            forward = LIST;
        }
        
        if (!isRedirected) {
            request.setCharacterEncoding("utf-8");
            RequestDispatcher view = request.getRequestDispatcher(forward);
            view.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to meals");

        request.setCharacterEncoding("utf-8");
        String mealId;
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));

        if ((mealId = request.getParameter("mealId")).equals("")) {
            Meal meal = mealDao.create(dateTime, request.getParameter("description"), Integer.parseInt(request.getParameter("calories")));
            mealDao.add(meal);
            log.debug("add meal id = " + meal.getId());
        } else {
            Meal meal = new Meal(Integer.parseInt(mealId), dateTime, request.getParameter("description"), Integer.parseInt(request.getParameter("calories")));
            mealDao.edit(Integer.parseInt(mealId), meal);
            log.debug("edit meal id = " + mealId);
        }

        response.sendRedirect(request.getContextPath() + "/meals");
    }
}
