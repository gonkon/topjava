package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealDAO;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    List<Meal> meals;
    List<MealTo> mealsTo;
    int CALORIES_PER_DAY = 2000;
    private static final String INSERT_OR_EDIT = "mealEdit.jsp";
    private static final String LIST = "meals.jsp";
    String forward = "";
    MealDAO mealDAO = new MealDAO();

    @Override
    public void init() throws ServletException {
        super.init();
        meals = mealDAO.generateMeals();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to meals");


        String action = request.getParameter("action");

        if (action != null && action.equalsIgnoreCase("edit")) {
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            Meal meal = mealDAO.getMeal(mealId);
            request.setAttribute("calories", meal.getCalories());
            request.setAttribute("description", meal.getDescription());
            request.setAttribute("dateTime", meal.getDateTime());
            request.setAttribute("mealId", mealId);
            forward = INSERT_OR_EDIT;
        } else if (action != null && action.equalsIgnoreCase("add")) {
            request.setAttribute("mealId", -1);
            forward = INSERT_OR_EDIT;
        } else if (action != null && action.equalsIgnoreCase("delete")) {
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            mealDAO.deleteMeal(mealId);
            mealsTo = MealsUtil.filteredForExcess(meals, CALORIES_PER_DAY);
            request.setAttribute("mealsWithExcess", mealsTo);
            forward = LIST;
        } else {
            mealsTo = MealsUtil.filteredForExcess(meals, CALORIES_PER_DAY);
            request.setAttribute("mealsWithExcess", mealsTo);
            forward = LIST;
        }
        request.setCharacterEncoding("utf-8");
        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to meals");
        request.setCharacterEncoding("utf-8");

        int mealId = Integer.parseInt(request.getParameter("mealId"));
        if (mealId == -1) {
            mealDAO.addMeal(LocalDateTime.parse(request.getParameter("dateTime").replace('T', ' '), mealDAO.dateTimeFormatter),
                    request.getParameter("description"), Integer.parseInt(request.getParameter("calories")));
        } else {
            mealDAO.editMeal(Integer.parseInt(request.getParameter("mealId")), LocalDateTime.parse(request.getParameter("dateTime").replace('T', ' '), mealDAO.dateTimeFormatter),
                    request.getParameter("description"), Integer.parseInt(request.getParameter("calories")));
        }
        forward = LIST;
        mealsTo = MealsUtil.filteredForExcess(meals, CALORIES_PER_DAY);
        request.setAttribute("mealsWithExcess", mealsTo);
        request.setCharacterEncoding("utf-8");
        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }
}
