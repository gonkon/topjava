<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .excess {
            color: red;
        }
    </style>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"
            integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.min.js"
            integrity="sha384-Atwg2Pkwv9vp0ygtn1JAojH0nYbwNJLPhwyoVbhoPwBhjQPR5VtM2+xf0Uwh9KtT"
            crossorigin="anonymous"></script>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr/>

    <div class="container">
        <h3 class="text-center">Meals</h3>
        <div class="card border-dark">
            <div class="card-body pb-0">
                <form id="filter" method="get">
                    <input type="hidden" name="action" value="filter">
                    <div class="row">
                        <div class="col-2">
                            <label for="startDate">From date (inclusive)</label>
                            <input type="date" class="form-control" name="startDate" id="startDate" autocomplete="off">
                        </div>
                        <div class="col-2">
                            <label for="endDate">To date (inclusive)</label>
                            <input type="date" class="form-control" name="endDate" id="endDate" autocomplete="off">
                        </div>
                        <div class="offset-2 col-3">
                            <label for="startTime">From time (inclusive)</label>
                            <input type="time" class="form-control" name="startTime" id="startTime" autocomplete="off">
                        </div>
                        <div class="col-3">
                            <label for="endTime">To time (exclusive)</label>
                            <input type="time" class="form-control" name="endTime" id="endTime" autocomplete="off">
                        </div>
                    </div>
                    <div class="card-footer text-right">
                        <button class="btn btn-danger" onclick="clearFilter()">
                            <span class="fa fa-remove"></span>
                            Cancel
                        </button>
                        <button class="btn btn-primary" type="submit">
                            <span class="fa fa-filter"></span>
                            Filter
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <hr>
    <div class="container">
        <a href="meals?action=create">Add Meal</a>
        <table border="1" cellpadding="8" cellspacing="0">
            <thead>
            <tr>
                <th>Date</th>
                <th>Description</th>
                <th>Calories</th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <c:forEach items="${meals}" var="meal">
                <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
                <tr class="${meal.excess ? 'excess' : 'normal'}">
                    <td>${fn:formatDateTime(meal.dateTime)}</td>
                    <td>${meal.description}</td>
                    <td>${meal.calories}</td>
                    <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                    <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
                </tr>
            </c:forEach>
        </table>
    </div>
</section>
</body>
</html>