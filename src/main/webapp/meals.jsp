<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://webapp/WEB-INF/functions" %>
<html lang="ru">
<head>
    <link href="CSS/styles.css" rel="stylesheet" type="text/css">
    <title>Meals</title>
</head>
<body>
<h3><a href="http://localhost:8080/topjava">Home</a></h3>
<hr>
<h3>Meals</h3>

<a href="meals?action=add">Add Meal</a>

<table>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    <jsp:useBean id="mealsWithExcess" scope="request" type="java.util.List"/>
    <c:forEach items="${mealsWithExcess}" var="meal">
        <tr class="${meal.excess ? "tr_excess" : "tr_ordinary"}">
            <td>${f:formatLocalDateTime(meal.getDateTime())}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td>
                <a href="meals?action=edit&mealId=${meal.id}">Update</a>
            </td>
            <td><a href="meals?action=delete&mealId=${meal.id}">Delete</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
